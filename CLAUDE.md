# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트

ChaKak(차칵)은 Hyundai Pleos Connect용 Android Automotive 앱이다. 음성 요청 시점의 위치·차량 관측값을 차량 내 Room DB에 저장하고, 주차 후 검토·편집한다. 구현 기준 문서는 `spec-en.md`이고 `spec.md`는 같은 내용의 한국어판이다. 동작을 바꿀 때는 `spec-en.md`의 해당 절을 먼저 확인한다.

`chakak-reel/`과 `shorts_prompt.md`는 앱 코드가 아니라 홍보용 50초 세로 영상 프로젝트다(showreel 스킬 산출물, 재생성 절차는 `chakak-reel/제작노트.md`). 앱 작업 시 건드리지 않는다.

## 빌드·테스트

JDK 17, Android SDK 35, Gradle 8.12(wrapper). SDK 경로는 추적하지 않는 `local.properties`에 둔다. `real` 플레이버는 Pleos Nexus(`nexus-playground.pleos.ai`)에서 `ai.pleos.playground:Vehicle`, `FusedLocation`을 받으므로 네트워크가 막히면 `mock`만 빌드된다.

```sh
# 단위 테스트
./gradlew :core:test                      # 순수 JVM (CaptureEngine, 접근 게이트)
./gradlew :app:testMockDebugUnitTest      # Robolectric (Room 저장소)
./gradlew :app:testRealDebugUnitTest      # Robolectric (Gleo 스키마·리소스 계약, app/src/testReal)

# 단일 테스트
./gradlew :core:test --tests 'ai.chakak.core.CaptureTest'
./gradlew :app:testMockDebugUnitTest --tests 'ai.chakak.data.RoomRecordStoreTest.memoIsCorrelatedSingleUseAndExpiring'

# 빌드·린트
./gradlew :app:assembleMockDebug :app:assembleRealDebug
./gradlew :app:lintMockDebug :app:lintRealDebug
```

- `mockRelease` 변형은 `androidComponents`에서 비활성화되어 있다. 존재하지 않는 태스크다.
- `GleoContractTest`는 `realDebug` 리소스 오버라이드(`gleo_emulator_in_motion_enabled=true`)를 단정하므로 `testRealDebugUnitTest`로만 통과한다.
- Room은 `exportSchema = true`로 `app/schemas/`에 스키마를 내보낸다. 엔티티를 바꾸면 DB `version`을 올리고 새 스키마 JSON이 생성되는지 확인한다.
- 테스트는 JVM·Robolectric에서만 돌았다. Pleos 에뮬레이터나 실차 검증 결과가 아니라는 점을 문서에 쓸 때 구분한다.

## 아키텍처

### 모듈 경계

- `core` — 순수 Kotlin/JVM. Android 의존 없음. 도메인 모델(`Record`, `Observation`, `Status`), `RecordStore`/`CaptureProvider` 인터페이스, `CaptureEngine`, 주행 상태 접근 게이트(`captureAccess`, `inMotionVoiceEnabled`). 정책 로직은 여기에 두고 테스트한다.
- `app` — Android. `core` 인터페이스의 구현체(`RoomRecordStore`), 플랫폼 추상화(`Platform`), Compose UI 한 화면(`LibraryScreen`), Gleo 수신기.

### 플레이버가 곧 의존성 주입

`app`은 `data` 차원의 `mock`/`real` 두 플레이버로 나뉘고, 진입점은 각 소스셋에 하나씩 있는 `createPlatform(context)`다.

- `app/src/mock/.../PlatformFactory.kt` — `MockPlatform`. 항상 터치 허용, `scenario`로 성공·부분 실패·권한 거부·미지원·오류·시간 초과를 시뮬레이션. 패키지 `ai.chakak.mock`. 실제 데이터로 전환하거나 자동 대체하지 않는다.
- `app/src/real/.../PlatformFactory.kt` — `RealPlatform`. `android.car`(기어·UX 제한)와 Pleos SDK(주행거리·경고등·타이어·위치)를 읽는다. `FEATURE_AUTOMOTIVE`가 없으면 `UnavailablePlatform`(모든 접근 차단).

`Platform`은 `touchAllowed`/`voiceAllowed` StateFlow와 `providers` 목록을 노출한다. `ChakakApp`은 같은 providers로 `CaptureEngine`을 두 개 만든다: 터치 게이트용 `engine`, 음성 게이트용 `voiceEngine`.

### 캡처 파이프라인 (`CaptureEngine.capture`)

1. 게이트(`allowed`)가 false면 즉시 false.
2. `store.begin()` — `requests` 테이블(ID만 있는 묘비)로 중복 요청을 거른다. 삭제된 기록의 ID로 재요청이 와도 되살아나지 않는다. 기본 레코드와 PENDING 관측 행이 먼저 영속된다.
3. 모든 provider를 병렬로 읽는다. 예산은 `receivedAt` 기준 5초. `SecurityException`→DENIED, 기타 예외→ERROR. 게이트가 도중에 false가 되면 INTERRUPTED.
4. `finally { NonCancellable }`에서 `store.finish()` — 남은 PENDING을 TIMEOUT/INTERRUPTED/ERROR로 바꾸고 `collecting=0`. 앱 시작 시 `store.recover()`가 중단된 수집을 INTERRUPTED로 마감한다.

`observe()`의 UPDATE는 `status='PENDING' AND collecting=1`일 때만 적용되므로 늦게 도착한 관측값이 완료·삭제된 기록을 덮지 않는다.

### 접근 게이트 (fail-closed)

`captureAccess(state, uxKnown, unrestricted, inMotionVoiceApproved)`:

- 터치 = 주차(P) ∧ UX 제한 정보 확인됨 ∧ 산만 최적화 불필요. 하나라도 모르면 차단.
- 음성 = 터치 ∨ (주행(D) ∧ UX 확인됨 ∧ 주행 중 음성 승인).
- UNKNOWN·R·기타 기어는 승인 여부와 무관하게 전부 차단.
- 주행 중 음성 승인은 `gleo_in_motion_approved`(배포 승인) 또는 `gleo_emulator_in_motion_enabled ∧ DEBUG ∧ HARDWARE=="ranchu"`(에뮬레이터 opt-in). 어느 쪽도 터치 UI를 열지 않는다.

이 규칙은 `CaptureAccessTest`에 고정되어 있다. 완화하려면 스펙과 테스트를 같이 바꾼다.

### Gleo 음성 연동 (`real`만)

`GleoReceiver`는 `CAPTURE_RECORD`, `ADD_RECORD_MEMO` 브로드캐스트를 받는다.

- Intent action: 실제 Gleo는 `<패키지명>.<함수명>`(`ai.chakak.CAPTURE_RECORD`)으로 보낸다. 가이드는 함수명만 쓴다고 설명하므로 매니페스트와 `gleoFunction()`은 두 형식을 모두 받는다. 이외의 action은 `ChakakApp`·DB에 닿기 전에 반환한다.
- 파라미터: `ACTION_PARAM` 문자열 extra 하나에 JSON 객체. 값이 없는 필드는 `""`로 온다(`requestId: ""`).
- 발신자 검증 없음: Gleo는 신원 공유 없이 일반 `sendBroadcast`로 보내고, 가이드에도 검증 방법이 없다. 대신 응답은 `gleo_package`로만 보내 기록 ID·토큰이 다른 앱에 퍼지지 않게 하고, 메모 쓰기는 1회용 토큰으로 묶는다.
- 전체 예산 6초. 플랫폼을 `start()`하고 기어·UX 제한 상태가 확정(`accessResolved`)될 때까지 최대 1.5초 기다린 뒤 `voiceAllowed`로 판정한다. 대기 시간만큼 수집 예산 5초가 줄어든다.
- 응답은 `ai.pleos.playground.intent.action.APP_ACTION_RESPONSE` 브로드캐스트로 돌려준다.
- 음성 메모: 새 기록을 저장하면 매번 `status: "asking"`으로 응답해 Gleo가 메모를 묻게 한다. 이때 60초짜리 1회용 토큰을 발급하고 기존 대기 토큰은 지운다. 그래서 메모를 기다리는 기록은 항상 하나뿐이다.
- Gleo는 응답 `data`의 `recordId`·`memoToken`을 다음 액션에 넘기지 않고 빈 값으로 `ADD_RECORD_MEMO`를 호출한다. 두 값이 비어 있으면 `addPendingMemo()`가 대기 중인 기록에 붙인다. 스키마에서 `memo`만 필수다. 주차 중 UI 편집(`edit`)은 대기 토큰을 지운다.
- 메모 답에 "메모"라는 말이 있어야 Gleo가 `ADD_RECORD_MEMO`로 보낸다. 내용만 말하면 잡담 영역으로 가서 저장되지 않는다. 그래서 질문 메시지가 "메모 다음에 내용을 말하라"고 안내한다. Gleo는 이 영어 메시지를 자기 말로 바꿔 말하고, 그 표현이 다음 답의 라우팅에 영향을 준다. "키워드 메모를 포함해 말하라"는 문구로 바꾸면 Gleo가 "메모라고 말하면서…"로 안내하고, 사용자의 "메모 …" 답이 Gleo 기본 리마인더로 빠졌다. 문구를 바꾸면 에뮬레이터에서 메모 라우팅을 다시 확인한다.
- 앱을 설치만 하고 한 번도 실행하지 않으면 Android의 stopped 상태라 브로드캐스트가 전달되지 않는다. 테스트 전 한 번 실행한다.
- 기능 플래그는 전부 `gleo.xml`의 bool 리소스다. `realDebug/res/values/gleo.xml`이 에뮬레이터 opt-in만 덮어쓴다.
- `app/src/real/assets/schema.json`(Gleo 액션 스키마)은 수신기·`Category` enum과 일치해야 하며 `GleoContractTest`가 검사한다. 액션이나 카테고리를 바꾸면 세 곳을 같이 고친다.

### 에뮬레이터 음성 테스트

Pleos Connect v2.1.0 AVD에 Gleo(`ai.umos.ambientai`, 사용자 10)가 들어 있다. 마이크 없이 문장을 주입하면 Gleo가 말한 것처럼 처리하며 스키마 승인 전에도 동작한다. Gleo는 앱 설치 시 스키마를 다시 읽는다.

```sh
adb shell am broadcast --user 10 -a ai.umos.intent.action.ASR -p ai.umos.ambientai --es ASR "'차칵으로 지금 위치 저장해줘'"
adb logcat | grep -E 'ACTIONPLANNER|sendActionCall|APP_ACTION_RESPONSE|timeout has occurred'
adb shell pm grant --user 10 ai.chakak pleos.car.permission.FUSED_LOCATION   # 위치 권한 요청 화면이 아직 없음
```

Gleo는 먼저 서버 라우팅 예시로 담당 영역을 고르고, 그 영역이 서드파티 앱을 다룰 때만 `schema.json`을 읽는다. 첫 단계는 스키마를 보지 않으므로 스키마 설명을 고쳐도 라우팅이 바뀌지 않는다. 스펙의 예시 명령 "캡처해줘"는 "차칵"을 붙여도 Gleo 기본 화면 캡처로 가는 경우가 있다. "지금 위치 저장해줘", "타이어 상태 기록해줘", "저 카페 나중에 가보게 기억해줘"는 차칵으로 연결된다. 카테고리는 라벨일 뿐이고 캡처는 항상 네 항목을 모두 수집한다.

기어는 adb로 바꿀 수 없다(user 빌드). Virtual Console은 기기 안 8088 포트의 웹 페이지이고, 같은 HTTP API로 바꿀 수 있다. 세션 신호가 1초마다 와야 에뮬레이션이 활성화된다.

```sh
adb forward tcp:18088 tcp:8088
while true; do curl -s -X POST "http://127.0.0.1:18088/browser/session?client=cli" >/dev/null; sleep 1; done &
curl -s -X PUT "http://127.0.0.1:18088/vehicle/gear?gear=Drive"   # Park | Reverse | Neutral | Drive
```

Gleo가 메모를 물은 뒤 `isFromAsking:true` 로그가 찍히는 동안만 답을 받는다. 두 번째 문장은 `adb logcat -e 'isFromAsking:true' -m 1`로 기다렸다가 주입한다. 빌드는 JDK 17로 한다. Android Studio 내장 JBR은 25라 Gradle 8.12에서 실패한다.

음성·좌표·토큰은 로그에 남기지 않는다(코드 주석의 명시적 정책). 메모 실패가 기본 레코드를 지우지 않는다.

### UI

`MainActivity` → `LibraryModel`(ViewModel) → `LibraryScreen` 하나. `mutate()`가 모든 쓰기를 직렬화하고 `touchAllowed`를 재확인한다. UI 문자열은 Compose 안에 한국어로 직접 쓴다(strings.xml 없음). 840dp 이상이면 목록·상세 2단, 아니면 단일 패널.

## 작업 관례

- 파일 수가 적고 한 줄에 많이 담는 밀도 높은 Kotlin이다. 새 파일을 늘리기보다 기존 파일에 맞춰 넣는다.
- `docs/`는 Git 제외이고 `docs/design`, `docs/pleos`, `docs/technical`만 추적 대상이다. README가 링크하는 `docs/technical/implementation-status.md`, `gleo-integration.md`는 이 체크아웃에 없다. 링크를 따라가기 전에 존재 여부를 확인한다.
- 커밋 메시지는 `feat:`, `fix:` 접두사를 쓴다.
