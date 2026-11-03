# 차칵(ChaKak) MVP 명세서

> English version: [spec-en.md](spec-en.md)

## 1. 제품 요약

차칵은 “캡처해줘” 한마디로 위치·시각·조회 가능한 차량 상태를 저장하고 음성 메모를 더하는 차량용 기록 앱이다. MVP는 다음 세 가지에 집중한다.

1. 운전 중 다시 찾고 싶은 장소와 차량 상태를 빠르게 기록한다.
2. 기본 기록을 먼저 저장한 뒤 선택적으로 음성 메모를 추가한다.
3. 주차 후 기록을 확인·보완해 장소 재방문과 정비 상담에 활용한다.

Pleos Connect 환경의 Kotlin 기반 Android 앱으로 개발한다. 배포 형태는 사용자 설치 앱(User-installed)으로 가정하고, 카테고리는 Mobility Services와 Lifestyle을 후보로 두고 실제 목적과 심사 정책에 따라 확정한다. 주행 중 음성 기록은 카테고리·지역 정책, Gleo 연동 및 실제 SDK 사용 가능 여부를 검증한 뒤 제공한다. **음성 중심 기록**, **차량 내 로컬 저장**, **주차 후 상세 열람·편집**을 기본 방향으로 한다.

### 범위와 근거의 구분

- **확정한 제품 설계**: Kotlin·Jetpack Compose·Room, 기본 기록 선저장, 주차 후 보관함, SDK Adapter와 Mock Provider 분리.
- **검증 전 목표**: Gleo 승인과 주행 중 headless 호출, 후속 음성 메모 연결, 프로젝트별 SDK 사용 권한 및 실제 반환값.
- **이 문서의 주차 상태**: 일시적으로 속도가 0인 상황이 아니라, 주차 여부를 확인하고 플랫폼이 해당 조작을 허용한 상태다.
- Moniq 명세의 19개 섹션 순서를 유지하되 차칵의 기능·화면·검증 조건으로 구성한다. 이 명세는 설계 기준이며 구현 완료 보고서가 아니다.

---

## 2. 문제 정의

- 우연히 발견한 카페·공원·전망 좋은 길은 이름과 주소를 몰라 다시 찾기 어렵다.
- 혼자 운전하면서 휴대폰으로 사진을 찍거나 지도에 핀을 남기기 어렵다.
- 도착 후에는 위치와 주변 풍경에 대한 기억이 흐릿해진다.
- 일시적인 경고나 이상 증상은 정비소에서 재현되지 않아 당시 상황을 설명하기 어렵다.
- 위치·시각·차량 상태와 운전자가 느낀 증상을 함께 남길 수단이 필요하다.

---

## 3. 제품 목표

### 핵심 목표

음성 요청부터 기록 저장, 선택적 메모 추가, 주차 후 확인까지 이어지는 MVP를 구현하고 Pleos Emulator에서 검증한다.

### MVP 성공 기준

- 음성 명령으로 기본 기록을 생성할 수 있다.
- 장소·차량 상태를 언급한 명령은 해당 분류로 저장된다.
- 후속 음성 메모가 최초 기록 ID에 연결된다.
- 메모 거절·무응답·인식 실패에도 기본 기록이 유지된다.
- 주차 후 전체·장소·차량 상태 목록에서 기록을 조회·수정·삭제할 수 있다.
- 차량 상태 요약에서 수집 시각, 조회 가능한 상태, 메모를 확인할 수 있다.
- 기본 기록 저장 실패 시 성공 안내를 하지 않는다.

### MVP 제외 범위

- 차량 고장 진단이나 수리 판단
- 서버 기반 계정·백업·동기화
- 휴대폰 연동
- 내비게이션 및 정비 서비스 직접 연계
- 지원 여부가 확인되지 않은 서라운드 이미지 수집

### 완료 판단

로컬 기록·보관함의 구현 완료, Mock 기반 UX 검증, 실제 Pleos SDK 연동, 공모전/Gleo 승인, 실차 검증을 각각 보고한다. Mock Preview 또는 에뮬레이터 성공만으로 주행 중 음성 기능과 실차 지원을 완료 처리하지 않는다. 아래 주행 음성 시나리오는 승인·허용 여부가 확인된 환경을 전제로 한다.

---

## 4. 대상 사용자

### 주요 사용자

혼자 운전하는 일이 잦고, 출퇴근이나 드라이브 중 발견한 장소를 나중에 다시 찾고 싶은 운전자.

### 추가 사용자

일시적인 경고나 이상 증상을 기록해 정비 상담에서 당시 상황을 설명하려는 운전자.

---

## 5. 제품 원칙

1. **먼저 저장**: 무엇을 남길지 고르기 전에 기본 기록을 저장한다.
2. **음성 중심**: 주행 중에는 음성 요청과 짧은 응답으로 조작을 줄인다.
3. **메모는 선택**: 추가 질문은 한 번만 하고, 메모가 없어도 기록을 유지한다.
4. **사실과 관측 시각 구분**: 명령 수신 시각과 항목별 수집 시각을 구분한다.
5. **누락을 숨기지 않기**: 미지원·조회 실패 항목을 정상값으로 대체하지 않는다.
6. **상세 조작은 주차 후**: 플랫폼 주행 제한과 주차 상태를 확인하고, 확인 불가 시 상세 조작을 제한한다.
7. **로컬 저장 우선**: 차량 내 저장을 기본으로 하고 외부 공유는 이용자의 선택을 전제로 한다.

8. **공식 디자인 우선**: Pleos 원문을 최우선으로 하고 Figma 자료를 대조한다. Material 3과 다른 디자인 도구는 보조 수단이다.
9. **실제 자료만 표시**: 이미지가 없으면 텍스트·아이콘으로 구성한다. 샘플 사진, 추정 센서값, Mock 결과를 실제 캡처로 보이게 하지 않는다.
10. **설계 후 구현**: 원문 확인 목록, 레이아웃, 컴포넌트·상태, 허용 매트릭스, 토큰 후보와 미결정을 정리한 뒤 앱 코드를 구현한다.

---

## 6. UX 방향과 흐름 조정

### 주요 UX 방향

1. **하나의 공통 저장 흐름**
   - “캡처해줘”, “지금 위치 캡처해줘”, “차 상태 캡처해줘”는 같은 저장 기능을 사용한다.
   - 장소·차량 상태를 언급하면 분류를 추가하고, 일반 명령은 주차 후 분류할 수 있다.
2. **기본 저장과 메모 추가 분리**
   - 저장 성공 후 “기록했어요. 추가로 남기실 메모가 있나요?”라고 묻는다.
   - 메모 추가 실패가 이미 저장된 기본 기록을 지우지 않는다.
3. **주행과 주차 상황별 화면 구분**
   - 주행 중에는 짧은 처리 결과 안내를 제공한다.
   - 상세 열람과 편집은 주차 후 제공한다.
4. **위치의 의미를 명확히 표시**
   - 핀은 기록 당시 차량 위치이며, 주변 카페나 공원의 정확한 위치와 다를 수 있음을 안내한다.
5. **차량 상태와 운전자 설명 함께 제공**
   - 조회한 차량 데이터와 음성 메모를 구분해 표시한다.
   - 과거 경고 조회와 후속 음성 대화 지원 여부는 구현 전 확인한다.

### Pleos 디자인 적용 기준

- `docs/Pleos_Connect_design_assets_215.fig`의 색상·타이포그래피·아이콘·컴포넌트를 스타일 기준으로 사용한다. Light/Dark 테마의 의미별 색상을 적용하고 선택·누락·오류를 색상만으로 전달하지 않는다.
- Asta Sans 기반의 제목·본문·레이블 위계를 사용한다. 한글·영어 및 폰트 확대 시 잘림을 확인하고, 문서에 나오는 최소 크기를 모든 화면의 본문 크기로 고정하지 않는다. [Typography](https://document.pleos.ai/docs/connect/guide/docs-design/foundations/typography)
- 핵심 상태는 1~2초 안에 파악되도록 구성한다. 시각적 입력 피드백은 0.25초 이내 제공하고, 긴 저장 작업은 별도 처리 상태로 보여준다. 이는 저장 완료 시간 기준이 아니다.
- 텍스트·아이콘 대비는 4.5:1 이상을 검증한다. 주요 정보 높이 5 mm, 보조 정보 3 mm 기준은 대상 디스플레이에서 확인한다. 주행 중에는 장문 메모·자동 스크롤·장식 애니메이션을 노출하지 않는다. [UI Design Guide](https://document.pleos.ai/docs/connect/guide/docs-design/Introduction/intro)
- 앱 아이콘은 foreground/background를 분리한 Adaptive Icon으로 제작한다. 일반 아이콘의 웹 기준 48 × 48 dp와 Figma의 px/그리드 표기는 구분하며, 이를 터치 영역 크기로 간주하지 않는다. [Iconography](https://document.pleos.ai/docs/connect/guide/docs-design/foundations/contents-creation/iconography), [App Icon](https://document.pleos.ai/docs/connect/guide/docs-design/foundations/contents-creation/app-icon)

### 화면 없는 캡처와 디자인 토큰 후보

- 승인된 Gleo 호출은 현재 앱을 강제로 전면화하지 않고 처리하는 것을 목표로 한다. 자체 마이크 상시 청취나 독자적인 호출어 기능은 MVP에 포함하지 않는다.
- 주행 중 완료 피드백은 검증된 Gleo 경로로 전달한다. 진행 표시를 위해 화면·Toast·OS 알림을 강제로 띄우지 않는다. 주차 후 목록과 상세에서 저장 범위를 확인한다.
- Compose 토큰은 다음과 같이 역할을 정의하되, 실제 HEX·크기·간격·반경은 원본에서 확인한 값만 채운다. 표의 이름은 차칵 내부 후보이며 공식 API 명칭이 아니다.

| 후보 그룹 | 역할 | 값의 근거 |
| --- | --- | --- |
| `ChaKakColors` | 배경·표면·본문·보조 텍스트·선택·오류·누락 | Colors의 의미별 토큰과 Figma Light/Dark |
| `ChaKakTypography` | 제목·본문·레이블의 위계 | Typography와 Asta Sans; 대상 density 검증 |
| `ChaKakSpacing` / `ChaKakShapes` | 패널 여백·간격·컨트롤 형태 | 사용 컴포넌트의 원본 치수; 미확인 값은 TBD |
| `ChaKakIcons` | 장소·차량 상태·메모·뒤로가기 | Iconography와 Figma 그리드; 아이콘과 터치 영역 구분 |
| `ChaKakElevation` / `ChaKakMotion` | 레이어와 최소 상태 전환 | Shadow·Animation; 주행 시 장식 효과 배제 |

토큰별로 출처 URL 또는 Figma 노드, 단위, 테마, 확인 상태를 기록한다. Figma에서 발견한 값과 앱에 사용할 값을 구분하고, 미확인 값을 Material 기본값으로 확정하지 않는다.

---

## 7. 핵심 사용자 흐름

## 7.1 순간 기록 흐름

### 진입점

운전자가 음성 비서를 호출한 뒤 기록 명령을 말한다. 주차 시에는 보관함의 “지금 기록” 버튼으로도 동일한 저장 기능을 실행할 수 있다. 최초 권한 설명·동의는 주차 상태에서 진행하며, 주행 중 권한 설정이나 키보드 입력을 요구하지 않는다.

### 입력

- “캡처해줘”: 별도 분류 없는 기록
- “지금 위치 캡처해줘”: 장소 기록
- “차 상태 캡처해줘”: 차량 상태 기록

### 처리

1. 허용된 요청을 받으면 기록 ID·명령 수신 시각·분류와 `collecting` 상태를 Room에 먼저 커밋한다. 이 저장이 실패하면 실패를 안내하고 수집을 시작하지 않는다.
2. 같은 기록 ID로 Location·Vehicle Adapter를 비동기 호출한다. 사용 가능성·권한을 확인하며, 각 결과의 값·출처·관측 시각·수신 시각·상태를 개별 저장한다.
3. 요청 수신부터 최대 5초의 초기 수집 예산 안에서 결과를 모은다. 이를 포함한 전체 Action은 Gleo 7초 타임아웃 전에 DB 마무리와 Callback을 완료하도록 계측한다.
4. 선택한 수집 항목을 모두 확보하면 `complete`, 누락·거절·미지원·timeout이 있으면 `partial`로 마무리한다. 실패한 필드를 정상값으로 채우지 않는다.
5. 실제 저장 범위에 맞춰 결과를 안내한다. 검증된 후속 대화 경로가 있으면 메모 여부를 한 번 묻고, 없으면 주차 후 편집으로 연결한다.

### 오류 처리

- 로컬 DB 저장 실패 시 성공 안내를 하지 않는다.
- 미지원·조회 실패 항목은 정상값으로 대체하지 않는다.
- 차칵의 부분 저장 정책: 최초 커밋은 기본 기록 생성을 의미하며 전체 수집 성공을 의미하지 않는다. 위치·차량 상태가 모두 없어도 기본 기록을 남기고, 최종 안내에서 누락을 알린다. 초기 저장 후 결과 갱신이 실패하면 기존 기록을 보존하고 실제 DB에 남은 범위만 안내한다. 기본 기록 커밋 전에는 성공으로 응답하지 않는다.

---

## 7.2 선택적 음성 메모 흐름

### 목적

숫자와 위치만으로 설명하기 어려운 상황을 같은 기록에 덧붙인다.

### 상호작용

- “오른쪽에 예쁜 공원 발견” 같은 답변을 텍스트로 저장한다.
- 답변은 최초 기록 ID에 연결한다.
- “없어”, 무응답, 대기 시간 초과 또는 인식 실패 시 추가 질문 없이 종료한다.
- 메모 추가가 실패해도 기본 기록은 유지한다.

### 구현 조건

Gleo Callback은 후속 상호작용용 `asking`을 지원한다. 다만 기본 저장 완료 후 메모 질문을 이어가는 순서와 다음 Action에 기록 ID를 전달하는 동작은 Emulator에서 검증한다. 기본 저장 Action을 메모 응답까지 열린 상태로 두지 않는다. 연결을 확인하지 못하면 주차 후 메모 입력을 제공하며, 어느 기록인지 불명확한 답변을 임의로 최근 기록에 붙이지 않는다. [Callback 가이드](https://document.pleos.ai/docs/pleos-only/app-integration-guide/guide/action_callback)

---

## 7.3 기록 보관함 흐름

### 목적

주차 후 저장한 순간을 다시 찾는다.

### 구성 요소

- 전체 / 장소 / 차량 상태 필터
- 시간순 기록 목록
- 기록 시각, 분류, 이름 또는 메모 요약

### 상호작용

- 기록 선택 시 상세 화면을 연다.
- 별도 분류 없이 저장한 기록은 전체 목록에서 확인하고 분류를 보완한다.
- 기록 이름·분류·메모 수정과 삭제를 제공한다.

---

## 7.4 장소 기록 확인 흐름

### 시나리오

1. 운전자가 풍경을 보고 “지금 위치 캡처해줘”라고 말한다.
2. 앱이 위치·시각·조회 가능한 차량 상태를 저장하고 장소로 분류한다.
3. 운전자가 원하면 주변 풍경에 관한 음성 메모를 추가한다.
4. 주차 후 장소 목록에서 저장 지점과 메모를 확인하고 이름을 보완한다.
5. 이후 방문할 장소를 찾는 단서로 활용한다.

### UX 참고

저장 지점은 차량 위치이며 주변 시설의 정확한 위치를 보장하지 않는다. 내비게이션 연결은 후속 검토 범위다.

---

## 7.5 차량 상태 확인 흐름

### 시나리오

1. 운전자가 경고나 이상 증상을 발견하고 “차 상태 캡처해줘”라고 말한다.
2. 앱이 위치·시각·조회 가능한 차량 상태를 저장하고 차량 상태로 분류한다.
3. “방지턱을 넘은 뒤부터 이상한 소리가 났어” 같은 메모를 추가한다.
4. 주차 후 상태 요약을 확인하고 증상 설명을 보완한다.
5. 정비소에서 시각·차량 정보·메모를 참고해 당시 상황을 설명한다.

### 기록 범위

기본 범위는 명령 시점에 조회 가능한 상태다. 이미 사라진 경고의 과거 조회 가능 여부는 확인이 필요하며, 지원이 확인되기 전에는 과거 경고 복원을 보장하지 않는다.

---

## 7.6 기록 수정·삭제 흐름

### 진입점

주차 상태에서 기록 상세 화면을 연다.

### 동작

- 이름, 분류, 메모를 수정하고 저장한다.
- 선택한 기록을 삭제한다.
- 상세 조작 중 주행 상태로 전환되거나 상태 확인이 불가능해지면 조작을 제한한다.

### 삭제 정책

삭제는 주차 상태에서 확인 팝업을 거쳐 수행한다. 복구·휴지통 기능은 MVP에 포함하지 않으며 삭제 전에 안내한다.

## 7.7 접근 불가·재시작 흐름

### 목적

SDK나 음성 기능을 쓸 수 없어도 이미 남긴 기록을 보존하고 확인 가능한 범위를 설명한다.

### 동작

- 권한 거절·SDK 미지원은 기록 상세의 해당 항목에 표시한다. 주행 중 설정 화면으로 이동시키지 않는다.
- 초기 커밋 후 앱이 중단되면 다음 실행에서 남은 `collecting`을 `partial`로 정리하고 미완료 항목에 `interrupted`를 표시한다. 현재 센서값으로 과거 순간을 채우지 않는다.
- 종료된 수집 요청의 늦은 응답은 무시하고, 삭제된 기록을 재생성하지 않는다.
- Mock 모드에서는 화면·기록에 “테스트 데이터”를 표시한다. 실제 SDK 환경에서 실패했다고 Mock으로 자동 전환하지 않는다.

---

## 8. 정보 구조

## 주요 화면

1. 음성 기록 처리 및 결과 안내(논리적 흐름; 독립 화면 필수 아님)
2. 기록 보관함
3. 기록 상세
4. 차량 상태 요약
5. 기록 편집

## 제안 화면 경로

- /capture
- /records
- /records/{recordId}
- /records/{recordId}/vehicle-status
- /records/{recordId}/edit

경로는 화면 구분을 위한 논리적 식별자다. Android 내비게이션 구현과 구체적인 메뉴 배치는 개발 시 확정한다. 계정 로그인과 하단 탭은 필수 요구사항이 아니다.

### 앱 윈도와 시스템 UI 경계

- Status bar, Driving view, GNB, App library는 OS 영역이다. 차칵은 할당된 Application window 안에서만 UI를 구성하며, 시스템 차량 경고를 재현하거나 가리지 않는다.
- 좁은 Portrait에서는 목록 → 상세의 단일 열로, 넓은 Landscape에서는 목록 + 선택한 기록 상세의 두 열로 구성한다. 방향·크기 변경 후 선택 기록과 필터, 편집 초안을 복원한다.
- Connect-S의 1:1 / 2:0 앱 영역, Connect-W의 2:1 / 1:2 구성을 검증한다. Connect-S 17·14.6·12.9인치와 W/W-Extended/WF에서 사용 가능한 프리셋을 시험하고 실제 window bounds·insets·density에 맞춘다. 전체 디스플레이 해상도를 앱 고정 크기로 사용하지 않는다.
- 시스템 패널·키보드가 앱을 덮는 경우와 앱이 가려졌다가 복귀하는 경우를 처리한다. `/capture`는 논리적 처리 경로이며 음성 명령마다 상세 Activity를 전면 실행할 필요는 없다.

근거: [Display Layout](https://document.pleos.ai/docs/connect/guide/docs-design/getting-started/layout), [Window Policy](https://document.pleos.ai/docs/connect/guide/docs-design/getting-started/window), [앱 구조 및 동작 모델](https://document.pleos.ai/docs/pleos-only/vehicle-app-planning-guide/app-structure-screen-model).

---

## 9. 화면 명세

## 9.1 음성 기록 안내

주행 중 headless 목표에는 별도 Activity가 없다. 아래 상태는 처리 모델이며, 시각 표시는 주차 후 앱이 보일 때 적용한다.

### 필수 UI 및 응답

- 기록 처리 상태
- 저장 완료 또는 실패 안내
- 저장 성공 후 선택적 메모 질문

### 상태

- 기본 저장 중 / 수집 중 / 전체 저장 / 부분 저장 / 기본 저장 실패
- 메모 대기 / 메모 저장 완료 / 메모 없이 종료

---

## 9.2 기록 보관함

### 필수 UI

- 전체·장소·차량 상태 필터
- 시간순 목록
- 기록 요약과 상세 진입

### 상태

- 로딩 / 기록 없음 / 정상 목록 / 조회 오류 / 주행 중 제한

---

## 9.3 장소 기록 상세

### 필수 UI

- 기록 이름과 분류
- 명령 수신 시각
- 저장 위치와 메모
- 차량 위치와 시설 위치가 다를 수 있다는 안내
- 편집 및 삭제 진입

### 상태

- 정상 / 위치 없음 또는 조회 실패 / 주행 중 제한

---

## 9.4 차량 상태 요약

### 필수 UI

- 명령 수신 시각과 항목별 수집 시각
- 조회 가능한 경고 정보·누적 주행거리·타이어 상태
- 항목별 지원 및 조회 상태
- 운전자 메모

### UX 참고

차량 데이터와 운전자 설명을 구분하고, 미지원·조회 실패를 정상 상태로 표현하지 않는다. 실제 표시 항목은 SDK 검증 결과에 따른다.

---

## 9.5 기록 편집

### 필수 UI

- 이름 입력
- 분류 선택
- 메모 편집
- 저장 동작과 결과 안내

### 상태

- 편집 중 / 저장 중 / 저장 실패 / 주행 중 제한

---

## 9.6 공통 컴포넌트와 입력 규칙

| 화면 요소 | 차칵 적용 |
| --- | --- |
| Page Navigation | 보관함 제목, 상세 화면의 뒤로가기와 최소 보조 동작 |
| Tabs | 전체·장소·차량 상태를 단일 선택으로 표시; 선택 상태 명시 |
| Basic Button | 주차 시 지금 기록·저장; 처리 중 Loading과 중복 실행 방지 |
| Text Field | 이름·메모 편집; Focused / Disabled / Read Only / Invalid 구분 |
| System Popup | 주차 시 삭제 확인; 취소와 삭제를 구분 |
| Toast | 짧은 완료 안내; 실패·누락 원인은 상세 상태에도 유지 |

- Text Field가 있는 편집 화면은 IME에 맞춰 크기를 조정해 입력 내용과 저장 버튼을 유지한다. 입력 필드가 없는 팝업에는 불필요한 IME 이동을 적용하지 않는다.
- 사용자 설치 앱의 CCP 지원은 선택 사항이다. MVP는 주차 시 필수 조작을 터치로 완결하고, 미지원 선언을 적용한다. CCP Back이 전달되면 하위 화면은 뒤로가기, 최상위는 종료 대신 앱 숨김으로 처리하고 상태를 저장한다.

근거: [Page Navigation](https://document.pleos.ai/docs/connect/guide/docs-design/components/page-navigation), [Tabs](https://document.pleos.ai/docs/connect/guide/docs-design/components/segmented-menu), [Basic Button](https://document.pleos.ai/docs/connect/guide/docs-design/components/actions/basic-button), [Text Field](https://document.pleos.ai/docs/connect/guide/docs-design/components/fields/text-field), [System Popup](https://document.pleos.ai/docs/connect/guide/docs-design/components/popups/system-popup), [Toast](https://document.pleos.ai/docs/connect/guide/docs-design/components/toast), [IME](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/ime-window-resize-handling-guide), [CCP](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/ccp-rotary-controller-development).

---

## 9.7 Compose 컴포넌트 구성과 상태

다음은 구현 전 설계안이며 Compose Preview는 추후 산출물이다. 차칵 컴포넌트가 SDK를 직접 호출하지 않도록 한다.

| 화면 | 컴포넌트 트리 | 상태와 동작 |
| --- | --- | --- |
| 앱 공통 | `ChaKakTheme` → `AppWindowScaffold` → `RestrictionGate` → 화면 | window bounds·insets·테마 적용; 주행/상태 미확인 시 제한 안내 |
| 보관함 | `RecordLibraryScreen` → `RecordCategoryTabs` + `RecordList` → `RecordRow` | 로딩·빈 목록·조회 오류·정상; 행에 완전/부분 저장·테스트 데이터 표시 |
| 상세 | `RecordDetailScreen` → `CaptureSummary` + `LocationSection` + `VehicleObservationList` + `MemoSection` + 동작 | 값과 단위·출처·시각·항목별 상태; 이미지 없으면 아이콘과 텍스트 |
| 편집 | `RecordEditScreen` → 제목 필드 + 분류 선택 + 메모 필드 + 저장 버튼 | 읽기 전용·입력 오류·저장 중·실패; draft 유지, 중복 저장 방지 |
| 삭제 | `DeleteRecordDialog` → 대상 요약 + 취소 + 삭제 | 주차 시 확인; 실패하면 원래 기록 유지 |

### 데이터 상태의 공통 표현

| 상태 | 표시 원칙 |
| --- | --- |
| 성공 | 실제 조회·저장한 값과 관측 시각 |
| 부분 성공 | 기록은 남았음을 알리고 빠진 항목·사유 표시 |
| 기본 저장 실패 | 저장하지 못했다고 안내; 성공 Toast·메모 질문 없음 |
| 권한 거절 | 권한이 없음을 표시; 주차 시에만 권한 조치 진입 |
| 미지원 | 현재 환경에서 제공되지 않는 항목으로 표시; 정상값 대체 없음 |
| 지연·시간 초과 | 수집 중과 timeout을 구분; 무한 로딩 없음 |
| 오래된 위치·중단 | 관측 시각 또는 중단 상태 표시; 최신 정보로 오인하지 않음 |
| Mock | 모든 관련 화면과 기록에 테스트 데이터임을 명시 |

`Bar`는 원문의 window/scroll bar 의미로, `Indicator`는 값·선택 표현 의미로 참고한다. 이를 앱 상단 탐색이나 비동기 로딩 컴포넌트와 동일시하지 않고, 탐색은 Page Navigation에 맞춘다.

---

## 10. 역할과 권한

## 운전자

- 음성으로 기록 생성 및 메모 추가
- 주차 후 로컬 기록 조회·수정·삭제

## 플랫폼 권한 및 이용 조건

- 위치 및 차량 데이터 접근에 필요한 권한을 확인한다.
- 음성 기능의 앱 호출·명령 전달·응답 조건을 확인한다.
- 상세 열람·편집은 플랫폼 주행 제한 및 주차 상태에 따른다.
- 정비 상담 상대에게 별도 계정이나 원격 접근 권한을 제공하는 기능은 MVP에 포함하지 않는다.

### 주행 제한과 데이터 접근

- `CarUxRestrictionsManager`의 현재 상태와 `onUxRestrictionsChanged()`를 반영한다. 속도 0 또는 기어 P만으로 사용 가능 여부를 판정하지 않는다. 차칵의 상세 열람·필터·편집·삭제는 주차 확인과 플랫폼 허용 조건을 모두 만족해야 한다.
- DO(Distraction Optimization) 선언만으로 주행 중 이용이 보장되지 않는다. 주행 중 화면·음성 Action의 허용 범위는 카테고리·지역·실행 방식별로 검증하며, 제한을 백그라운드 호출로 우회하지 않는다. [주행 규제 구현](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/driving-restriction-development-guide), [지역 정책](https://document.pleos.ai/docs/pleos-only/vehicle-app-planning-guide/driving-restiction-policy)
- 조회 권한 후보는 `pleos.car.permission.CAR_DRIVE`(Odometer), `pleos.car.permission.CAR_SAFETY`(Safety/Tire), `pleos.car.permission.FUSED_LOCATION`이다. 실제 함수의 `@RequiresPermission`을 확인하며 차량 제어 권한은 요청하지 않는다. 위치 권한은 런타임 거절·철회를 처리한다. [Vehicle 권한](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/Vehicle/vehicle-permission), [위치 권한](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/FusedLocation/declaring-permissions)
- 위치·음성 메모의 사용 목적을 주차 시 설명한다. 앱은 원음 대신 필요한 텍스트를 보관하고 위치·메모를 로그에 그대로 남기지 않는다. 차량 공유·운전자 변경 시 기록 노출과 전체 삭제 방법을 설계한다. Gleo 처리 경로까지 오프라인·차량 내부라고 보장하지 않는다. [개인정보 설계](https://document.pleos.ai/docs/pleos-only/vehicle-app-planning-guide/security-privacy)

### 기능 허용 매트릭스

공식 근거는 위의 CarUxRestrictions·지역 정책이며, 아래 표에는 차칵이 선택한 더 좁은 UI 범위도 포함한다. 실제 주행 음성 허용은 아직 미확인이다.

| 기능 | 주차 확인 + 플랫폼 허용 | 주행/규제 활성 | 상태 미확인 |
| --- | --- | --- | --- |
| Gleo headless 캡처 | 승인·권한·실행 경로 검증 후 제공 | 미확인; 승인 및 정책·실행 검증 전 비활성 | 허용을 입증할 수 없으면 새 요청 비활성 |
| 후속 음성 메모 | Gleo 연결 검증 후 제공 | 캡처와 별도로 허용·대화 지속 검증 필요 | 새 대화 시작하지 않음 |
| 터치 캡처·목록·필터·상세 | 제공 | 차칵 정책으로 제한 | 제한 |
| 이름·분류·메모 편집·삭제 | 제공 | 제한 | 제한 |
| 권한 요청·설정 이동 | 필요할 때 제공 | 사용자 조작 요구 안 함 | 사용자 조작 요구 안 함 |
| 이미 커밋된 기록 | 유지 | 유지; 제한된 새 수집은 시작하지 않음 | 유지 |

허용 여부와 기존 데이터 보존은 별개다. 상태 전환 중인 SDK 작업은 플랫폼 정책에 맞게 취소·종료하고, 이미 저장한 값과 누락 사유는 유지한다.

---

## 11. 기능 요구사항

## 필수 기능

- 음성 요청을 공통 기록 기능에 연결
- 명령에 따른 장소·차량 상태 분류
- 위치·시각·조회 가능한 차량 상태 수집
- 항목별 수집 시각과 미지원·조회 실패 상태 저장
- 기본 기록의 로컬 저장
- 선택적 음성 메모를 동일 기록에 추가
- 주차 시 터치 기록·권한 안내 및 부분 저장 상태 표시
- 전체·장소·차량 상태 목록
- 상세 조회, 이름·분류·메모 수정, 삭제
- 차량 상태 요약
- 주행·주차 상태에 따른 상세 조작 제한

## 지원 확인 후 검토할 확장 기능

- 서라운드 이미지
- 휴대폰 연동
- 동의 기반 백업·동기화
- 장소 기록의 내비게이션 연결
- 선택한 차량 상태 기록의 정비 상담 연계

---

## 12. 비기능 요구사항

- 차량 디스플레이에 맞는 읽기 쉬운 정보 구성
- 주행 중 짧은 음성 응답과 최소 조작
- 메모 실패와 기본 기록 저장의 분리
- 차량별 데이터 지원 차이를 드러내는 오류·누락 표시
- 주차 상태 확인 불가 시 상세 조작 제한
- 차량 내 로컬 저장을 기본으로 하는 데이터 처리
- 외부 공유 시 이용자 선택 존중
- Pleos Emulator에서 핵심 흐름과 실패 상황 검증
- Gleo의 7초 Action 타임아웃 전에 DB 처리와 Callback이 끝나도록 수집 시간을 제한한다. 차칵의 초기 수집 예산은 최대 5초로 제안하며 cold start를 포함해 계측 후 조정한다. 음성 메모 대기는 별도 후속 동작이다.
- 저장 용량·보관 기간과 메모 대기 시간은 구현 전 확정

### 리소스·생명주기·음성 출력

- 비내비게이션 앱 기준 주행 중 CPU 5% 미만, 정차 중 25% 이하, RAM 400 MB 이하를 검증 목표로 적용한다. 요청 시 수집을 기본으로 하고 상시 위치 추적·차량 상태 폴링은 MVP에서 제외한다. [성능 가이드](https://document.pleos.ai/docs/pleos-only/vehicle-app-planning-guide/performance-resource-mgmt)
- 화면 비표시·프로세스 재생성·Suspend 후에도 커밋된 기록을 복원한다. 필요 없는 SDK listener와 리소스를 해제하고, 완료되지 않은 대화는 저장 기록과 분리해 종료한다.
- 기본 음성 출력은 Gleo가 담당한다. 자체 TTS를 도입하면 Gleo와 중복 재생하지 않도록 하고, 오디오 포커스 거절·상실 및 Suspend 시 출력 중지·포커스 반납을 처리한다. 자체 안내 시작에는 권장 선행 묵음 150~200 ms를 적용한다. [오디오 포커스](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/audio-focus-development-guide/audio-focus-development), [Suspend](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/audio-focus-development-guide/suspend-mode-audio-focus), [음성 안내](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/voice-guidance-preroll-silence-guide)

---

## 13. 권장 기술 스택

## 앱

- Kotlin + Jetpack Compose 화면 및 Compose Preview
- Room 영속 저장, ViewModel과 Coroutines/Flow 기반 상태 전달
- Pleos Connect / Android Automotive OS 실행 환경
- Pleos Emulator 검증 환경

## 음성 연동

- Gleo Interface
- App Schema에 순간 기록 및 메모 추가 기능 정의
- Gleo Action/Callback으로 요청과 처리 결과 연결

## 데이터 수집

- Pleos FusedLocation SDK를 우선 검증한다. 일반 Android/Google 위치 API가 동일하게 동작한다고 가정하지 않는다.
- Vehicle SDK의 Safety·Odometer·Tire 도메인 검토
- 좌표·정확도·관측 시각과 차량 상태의 실제 제공 범위 확인

## 저장 및 애플리케이션 구조

- Room을 사용한 차량 내 로컬 DB와 트랜잭션·마이그레이션 관리
- 음성 요청 처리, 데이터 수집, 기록 저장, 화면 표시의 책임 분리
- 기본 저장 완료 후 기록 ID를 통해 메모 추가
- 초기 MVP에는 별도 서버를 두지 않는 방향

## 선택 이유

차량 데이터와 음성 요청을 직접 연결하고, 서버 운영 부담을 줄이면서 두 핵심 시나리오를 검증한다. API 명칭·권한·지원 범위는 구현 전에 공식 문서와 Emulator에서 확인한다.

### 개발 환경 기준과 가용성 확인

- Android Studio, Android SDK API 34, `minSdk >= 28`, Gradle 8.0 이상을 준비한다. 문서 예시 의존성은 `ai.pleos.playground:Vehicle:2.3.1`과 `ai.pleos.playground:FusedLocation:1.1.3`이다. 이는 이번 문서 확인 기준이며 실제 프로젝트·Emulator와 맞는 버전을 고정한다. AVD는 Cold boot·RAM 4 GB로 구성한다. [시작 가이드](https://document.pleos.ai/docs/pleos-only/getting-started), [환경 구성](https://document.pleos.ai/docs/connect/guide/getting-started/application-development/setup-connect-sdk-emulator), [FusedLocation 설정](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/FusedLocation/project-setup)
- 프로젝트 생성/신청 안내에는 Vehicle State가 사용 가능, FusedLocation·TTS·STT·Vehicle Control이 준비 중으로 기재되어 있다. API 문서 존재와 실제 프로젝트 권한은 별개다. My Project의 상태와 실행 환경에서 사용 가능 여부를 확인하고, 미제공 기능을 시연에서 구현 완료로 표시하지 않는다. [SDK 신청 안내](https://document.pleos.ai/docs/pleos-only/create-my-project)

### Gleo 연동 계약

- 제안 Action은 `CAPTURE_RECORD`와 `ADD_RECORD_MEMO` 두 개다. 최대 3개 제한 안에서 분류는 parameter로 전달하고, descriptions는 영어로 작성한다. 필수 값 누락·타입·enum은 앱에서도 검증한다. [App Schema](https://document.pleos.ai/docs/pleos-only/app-integration-guide/guide/app-capability)
- `assets/schema.json`과 Action별 manifest filter를 구성한다. 화면을 열지 않는 capture는 `component="service"`의 broadcast 방식을 우선 검증한다. 스키마 등록·검토와 활성화는 별도 선행 조건이다. [전달 가이드](https://document.pleos.ai/docs/pleos-only/app-integration-guide/guide/deliver-schema)
- Callback은 실제 결과에 맞는 `success` / `asking` / `fail`과 영어 `message`, 필요한 최소 `data`를 반환한다. 한국어 안내 예문은 사용자 UX 예시이며 Callback 문자열과 동일하지 않다. `recordId`는 상관관계용 앱 필드로 검증하며 플랫폼이 자동 보장한다고 가정하지 않는다.
- 예제의 `ACTION_PARAM` JSON 수신 및 `APP_ACTION_RESPONSE` 응답을 검증한다. 문서 본문의 개별 extras 설명과 코드 예제 사이 차이가 있으므로 설치된 Gleo의 실제 payload를 확인한다. 원문 메모·좌표를 디버그 로그에 남기지 않는다. [코드 예제](https://document.pleos.ai/docs/pleos-only/app-integration-guide/guide/examples)

### 데이터 조회 계약

- Vehicle을 초기화한 후 도메인을 얻고 `checkXxxCapabilities`에서 READ 또는 READ_WRITE 및 해당 영역 지원을 확인한 후 Get을 호출한다. null·미지원·권한 오류·timeout을 구분하고 사용 종료 시 release한다. 읽기 전용 앱으로 Set은 호출하지 않는다. [Capability](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/Vehicle/check-capability), [Get](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/Vehicle/get-property)
- FusedLocation은 `isSupported()` → `initialize()` 후 조회한다. `getLastKnownLocation()`은 새 위치가 아닌 마지막 관측값이므로 관측 시각과 신뢰도, null을 처리한다. 필요 시 제한된 시간 동안 callback을 받고 종료 시 unregister 및 release한다. [위치 소개](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/FusedLocation/intro), [위치 접근](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/FusedLocation/location-data-access)
- Safety의 지원 경고 상태, Odometer, Tire의 실제 제공 항목만 저장한다. 경고 상태를 과거 이력·DTC·고장 진단으로 확대 해석하지 않는다. 위치 API 미지원 시에는 위치 누락을 표시하며 검증하지 않은 대체 API로 성공을 가장하지 않는다.

### 애플리케이션 아키텍처

- **Presentation**: Compose 화면·디자인 토큰·ViewModel. Room 관찰 결과를 UI 상태로 변환하며 차량값을 UI에서 만들어내지 않는다.
- **Domain/Application**: `CaptureRecordUseCase`, `AddMemoUseCase`, `UpdateRecordUseCase`, `DeleteRecordUseCase`. 선저장·수집 마감·중복 방지·메모 연계·조작 허용 규칙을 담당한다.
- **Data**: `RecordRepository`와 Room DAO/Entity. 외부 SDK의 콜백 형식을 앱의 관측 결과 모델로 변환하고, 동일 기록의 업데이트를 원자적으로 처리한다.
- **SDK Adapter**: `GleoAdapter`, `VehicleStateProvider`, `LocationProvider`, `RestrictionProvider`를 경계로 둔다. SDK 연결·권한·수집·listener 해제를 각 adapter 안에서 처리한다.
- **Mock Provider**: 동일 인터페이스의 테스트 구현으로 성공·지연·거절·미지원·오류를 재현한다. 실제 SDK와 빌드/DI 설정으로 분리하고 각 관측의 `source_kind`를 저장한다. release 기본 구성은 실제 provider이며 Mock 자동 대체를 금지한다.
- **Headless 실행**: UI의 ViewModel 생존에 의존하지 않는 수집 조정자를 둔다. broadcast 수신 후 비동기 작업의 실행 시간·프로세스 수명과 Android 제약을 검증한다. `component="service"`는 Gleo의 broadcast 호출 구분이며 무제한 Android Service 실행 보장이 아니다.

### STT와 버전 검증

- SpeechToText는 발화를 텍스트로 바꾸는 SDK이며 Gleo 호출·wake-up을 대체하지 않는다. 자체 메모 STT는 필요성과 권한이 확인된 경우에만 추가한다. ON_DEVICE와 HYBRID/SERVER의 처리 위치·등록·네트워크 조건을 별도로 검증한다. [SpeechToText 소개](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/SpeechToText/intro)
- 시작 문서의 Vehicle 2.3.1 예시와 Vehicle 소개/샘플의 2.3.2 기준이 다르다. Service도 소개 2.3.21, 샘플 2.3.22로 다르므로 SDK·Service·Emulator 조합을 기록해 검증한다. 샘플의 minSdk 23을 차칵에 복사하지 않고 제품 요구 `minSdk >= 28`을 유지한다. [Vehicle 소개](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/Vehicle/intro), [Sample 튜토리얼](https://document.pleos.ai/docs/pleos-only/tutorial/vehicle-sdk-sample-app)

---

## 14. 제안 데이터 모델 (MVP)

구현을 위한 초안이며, 타입과 제약조건은 개발 시 확정한다.

### records

- id: 기록 ID
- category: unclassified / place / vehicle_status
- title: 주차 후 보완할 수 있는 이름, 선택
- command_received_at: 명령 수신 시각
- capture_status: collecting / complete / partial; 기본 저장 이후 수집 진행·완전성
- finalized_at: 수집 종료 시각, 수집 중에는 없음
- data_mode: real / mock; 실제 기록과 테스트 기록 구분
- request_key: 중복 Action 방지용 앱 관리 키, 전달 방식 검증 필요
- note_text: 음성 또는 편집으로 입력한 메모, 선택
- created_at
- updated_at

### record_locations

- record_id
- latitude, longitude: 위치 확보 시 저장
- accuracy: 실제 API가 정확도·단위를 제공할 때만 저장
- confidence: SDK가 제공하는 신뢰도, 선택; accuracy와 동일시하지 않음
- observed_at: 위치 관측 시각
- collected_at: 앱이 위치 결과를 받은 시각
- source_kind: pleos / mock
- source_api: 조회 출처 식별자
- collection_status: pending / available / unsupported / permission_denied / unavailable / timeout / error / interrupted
- unavailable_reason: 미지원·권한 거절·조회 실패 등의 사유, 선택

### vehicle_observations

- id
- record_id
- domain: Safety / Odometer / Tire 등 검증된 도메인
- field_key: 실제 지원되는 항목 식별자
- value: 수집한 값, 미확보 시 없음
- unit: 해당하는 경우 저장
- observed_at: 원본 관측 시각, 제공되는 경우
- collected_at: 앱이 항목별 결과를 받은 시각
- source_kind: pleos / mock
- source_api: 조회 출처 식별자
- collection_status: record_locations와 동일한 상태 집합
- unavailable_reason: 선택

### 후속 확장 후보

이미지는 필수 저장 필드가 아니다. API·권한·지원 차종·주행 중 허용 범위가 확인된 후 선택적 첨부 모델을 검토한다.

### 저장 및 대화 상태 규칙

- 위치·차량 관측 누락은 nullable 값과 상태로 저장한다. `unsupported`, `permission_denied`, `unavailable`, `timeout`, `error`를 구분하며 0이나 정상 경고 상태로 대체하지 않는다.
- `observed_at`은 원본 관측 시각, `collected_at`은 앱 수신 시각으로 구분하고 원본 시각 미제공 시 이를 명시한다. 늦은 결과로 기록 시점을 조용히 바꾸지 않는다.
- 메모 대기 상태는 `record_id`, 만료 시각, 처리 여부로 관리한다. 만료·중복·삭제된 대상에 대한 응답은 다른 기록으로 재연결하지 않는다. 재전달 Action의 식별 수단은 Gleo 검증 후 확정한다.

### 비동기 저장 규칙

- 기본 기록 생성 트랜잭션과 관측 결과 갱신 트랜잭션을 분리한다. 최초 `collecting`과 요청 시각은 데이터 수집 전에 커밋한다.
- 결과 갱신은 존재하는 기록 ID와 활성 수집 세션을 확인한 후 적용한다. 편집된 제목·메모를 SDK 응답으로 덮어쓰지 않는다.
- 마감 시 미완료 항목을 timeout으로 바꾸고 `finalized_at`을 저장한다. 프로세스 중단 후 복구 시에는 interrupted를 사용한다. 초기 저장 실패는 성공한 record 상태로 생성하지 않는다.
- `complete`는 정의한 수집 대상의 완료이며 모든 차량 데이터가 정상이라는 진단이 아니다. Room 스키마 변경 시 기존 기록과 관측 상태의 마이그레이션을 검증한다.

---

## 15. 예외 상황

- 위치 권한 거절, 위치 미확보 또는 오래된 위치
- 차량별 경고·주행거리·타이어 정보 미지원
- 명령 이전에 경고가 사라져 현재 조회로 확인할 수 없음
- 일부 차량 상태 조회 실패 또는 시간 초과
- 로컬 DB 저장 실패 또는 저장 공간 부족
- 기본 기록 저장 후 메모 거절·무응답·인식 실패
- 메모 답변이 늦게 도착하거나 다른 기록과 혼동될 가능성
- 주차 상태를 확인할 수 없거나 상세 화면 사용 중 주행 시작
- 장소 기록에 위치가 없거나 핀이 의도한 시설과 다름

각 상황의 처리 정책 중 미확정 항목은 다음 절에서 관리한다.

---

## 16. 구현 전 확인 사항

다음 다섯 가지를 설계·SDK 검증 단계에서 결정하고 근거를 기록한다.

1. **카테고리와 주행 중 허용**: Mobility Services/Lifestyle 중 실제 분류, DO 필요 여부, 국가·주행 상태별 headless Action 허용, 공모전 참가자의 Gleo 승인 가능 여부. 미확인인 동안 주행 기능을 사용 가능으로 표시하지 않는다.
2. **실제 SDK 조합과 데이터 범위**: Vehicle SDK·Service·Emulator 버전, FusedLocation/STT 프로젝트 접근 상태, Safety/Odometer/Tire의 실제 함수·권한·단위·timestamp·capability. 문서 존재, 승인, 실행 결과를 구분한다.
3. **음성·비동기 트랜잭션**: 수신 payload, `asking` 후속 순서, recordId 전달, 중복 식별, 프로세스 수명, 수집 예산과 메모 timeout. 기본 기록 보존과 늦은 응답 처리를 검증한다.
4. **디자인과 개인정보 정책**: Figma/웹의 단위·버전 차이, 최종 토큰·터치 영역, 오래된 위치 기준, 기록 보관·용량, 공유 차량 기록 분리와 전체 삭제. 미확인 치수는 TBD로 유지한다.
5. **검증·제출 완료 기준**: 실제/Mock 구분, 필수 검증 환경, 팀명과 APK 파일명, 저장소 공개 범위, 심사용 계정·접근 확인, README와 제출 경로. 파일럿 지표·기간도 별도로 정하며 근거 없는 MAU 목표를 만들지 않는다.

서라운드 이미지·휴대폰 연동·백업은 계속 확장 후보이며 위 검증을 위해 MVP 필수 범위로 승격하지 않는다.

---

## 17. 개발 우선순위

### 0단계: 원문 확인과 설계 산출물

앱 코드 작성 전에 다음을 정리한다. 경로는 앞으로 만들 산출물이며, 이 명세 수정만으로 생성·검증된 것은 아니다.

| 산출물 | 필수 내용 |
| --- | --- |
| `docs/pleos/verified-links.md` | 원문 제목·URL·확인일·핵심 요구·열람 상태; 읽지 못한 링크는 UNREAD |
| `docs/design/design-tokens.md` | 출처가 있는 색상·타입·간격·아이콘 토큰 후보; 미확인 값 TBD |
| `docs/design/ux-flows.md` | headless 목표, 주차 보관함, 오류 흐름, 기능 허용 매트릭스 |
| `docs/design/screens.md` | 좁은/넓은 창·주간/야간 레이아웃, 컴포넌트 트리, 빈 상태·부분 저장·접근 실패 |
| `docs/technical/sdk-feasibility.md` | 문서·프로젝트 승인·Emulator·실차 증거를 구분한 SDK 검증 표와 미결정 5개 |

### 1단계: SDK 사용 가능성 검증

- 프로젝트 접근·Gleo 승인 및 스키마 등록·headless 호출·주행 허용을 우선 확인한다.
- 이어서 Vehicle capability/Get, FusedLocation, 선택적 STT, 후속 대화를 검증한다.
- 사용 불가 항목의 대안과 실제/Mock 시연 범위를 기록한다.

### 2단계: 저장 기반과 테스트 Provider

- Room 모델·DAO·Repository와 Adapter 인터페이스
- 선저장, 비동기 갱신, 부분 저장, 중복·중단·삭제와 늦은 응답 처리
- 분리된 Mock Provider로 실패·권한 거절·미지원·지연 재현

### 3단계: 보관함과 Compose 디자인 시스템

- 확정 근거에 따른 토큰, 앱 창, 목록·상세·편집·삭제
- 좁은/넓은 창·주간/야간·글꼴 확대·IME·제한 상태 Compose Preview
- 이미지 없는 기록은 아이콘·타이포그래피로 구성

### 4단계: 실제 캡처와 선택적 메모

- 검증된 Gleo·Vehicle·Location Adapter 연결
- 7초 Action 제한 안의 결과 반환과 실제 관측값 저장
- 승인된 후속 메모 연결, 실패 시 기록 보존 및 주차 후 편집

### 5단계: 통합 검증

- CarUxRestrictions, Virtual Console, FormFactorConsole, 재시작·Suspend 검증
- 실제/Mock 및 Emulator/실차 결과를 분리해 기록
- release APK 설치·실행과 핵심 사용자 흐름 확인

### 6단계: 공모전 제출 준비

[git-manual.md](git-manual.md)의 제출 조건을 적용한다.

- 소스 코드와 실행 가능한 release APK를 같은 GitHub 저장소에 준비한다.
- 기본 경로는 `apk/팀명_앱이름_release.apk`이며 팀명 확정 후 정확한 파일명을 사용한다.
- GitHub 업로드 한도를 초과하면 GitHub Releases에 올리고 해당 Release 페이지 주소를 제출한다.
- 제출 페이지에는 저장소 URL과 APK 경로 또는 안내된 Release URL을 입력한다.
- Private 저장소라면 공지된 심사용 GitHub 계정을 Collaborator로 초대하고 마감 전에 접근을 검증한다. 개인 Private 저장소의 쓰기 권한도 초대 전에 확인한다.
- README에는 앱명·한 줄 소개, 실행 방법, 테스트 환경·필요 설정, APK 위치 또는 Release 링크를 작성한다. 실제 SDK 승인 조건과 Mock 사용 여부도 실행 설명에 구분한다.
- 시연 영상에는 확인한 기능과 지원 범위를 정확히 표시한다. 저장소 생성·업로드·초대·제출은 이 문서 수정 작업의 실행 범위가 아니다.

### 검증·제출 보완

- 1단계에서 프로젝트/SDK 상태, Gleo 스키마 활성화, 주행 허용 조건을 먼저 확인한다. 디자인 토큰과 Portrait/Landscape 화면 골격도 함께 검증한다.
- Virtual Console에서 기어·주행거리·지원 Telltale 상태를 바꿔 저장값을 대조한다. Tire 등 콘솔 미제공 항목은 별도 지원 환경이나 명시된 테스트 대역으로 검증하며 실차 확인과 구분한다. [Virtual Console](https://document.pleos.ai/docs/pleos-only/app-test-guide/virtual-console-guide)
- FormFactorConsole에서 지원 프리셋·방향·dp/DPI/px를 기록하고, 테마·IME·Back·주행 전환·프로세스 복원까지 확인한다. 폼 팩터 검증에는 별도 창의 Emulator와 Host Display Bridge를 사용한다. [폼 팩터 테스트](https://document.pleos.ai/docs/pleos-only/app-test-guide/form-factor-test-guide)
- 공모전 제출과 App Market 심사를 구분한다. 마켓 배포 시 APK, 아이콘·스크린샷, 개인정보처리방침, 위치정보 관련 약관, 권한·카테고리 정보 및 동작 설명을 준비한다. [앱 심사 제출](https://document.pleos.ai/docs/pleos-only/distribute/app-submission-guide)

---

## 18. UX 수용 기준

- 사용자는 분류 선택 화면 없이 “캡처해줘”로 기록을 시작할 수 있다.
- 장소·차량 상태를 언급하면 같은 저장 흐름에서 해당 분류로 기록된다.
- 저장 성공 후 추가 메모 질문은 한 번만 제공된다.
- 메모를 남기지 않거나 메모 저장이 실패해도 기본 기록이 남는다.
- 기본 기록 저장 실패를 성공으로 안내하지 않는다.
- 주차 후 기록의 시각·위치·차량 상태·메모를 확인하고 내용을 보완할 수 있다.
- 위치가 차량이 있던 지점임을 이해할 수 있다.
- 지원되지 않거나 조회에 실패한 차량 정보를 정상값으로 오해하지 않는다.
- 주행 중 또는 주차 상태 확인 불가 시 상세 조작이 제한된다.

### Pleos 적합성 수용 기준

- 허용된 음성 Action은 7초 타임아웃 전에 실제 저장 결과를 반환하고, 메모 무응답을 기다리지 않는다. 부분 저장은 누락을 알리며 중복·지연 응답이 다른 기록을 변경하지 않는다.
- 주행 규제 변경 즉시 제한 대상 입력·필터·상세 조작이 차단되고, 기본 기록은 유지된다. 주차 복귀 시 현재 규제 상태를 다시 확인한다.
- 지원 폼 팩터의 Portrait/Landscape·Light/Dark에서 잘림·겹침 없이 기록을 열고 저장할 수 있다. 키보드·시스템 패널·앱 숨김 이후 선택 기록과 편집 상태가 일관된다.
- 권한 거절·위치 null·오래된 위치·읽기 불가능 capability는 정상 데이터로 표시되지 않는다. FusedLocation 지원 여부와 프로젝트 사용 가능 상태를 각각 검증한다.
- 앱의 자체 오디오가 있다면 긴급/통화·포커스 상실·Suspend에 대응한다. 음성 명령의 네트워크 의존성이 확인되기 전에는 오프라인 음성 지원을 약속하지 않는다.

### 구현·제출 수용 기준

- 코드 구현 전에 원문 근거·화면별 상태·토큰 후보·미결정 5개가 정리되어 있다. UNREAD/TBD를 검증 완료로 표시하지 않는다.
- 느린 SDK에서도 기록 ID와 요청 시각이 먼저 영속화된다. 중단·삭제·지연 응답·메모 실패에도 데이터가 다른 기록에 섞이지 않는다.
- 초기 저장 실패, 부분 저장, 권한 거절, 미지원, timeout이 화면과 피드백에서 구분된다. 가짜 이미지나 정상 센서값이 나타나지 않는다.
- Mock Preview는 테스트 표시가 있고 release가 실제 SDK 실패를 Mock 성공으로 바꾸지 않는다.
- release APK 파일명·위치와 README 실행 방법이 `git-manual.md`에 부합하며, 제출 저장소·APK 접근 가능성을 확인한다.

---

## 19. 참고 자료

### 입력 자료와 적용 우선순위

| 자료 | 명세 반영 |
| --- | --- |
| [Astra 시작 프롬프트](chakak_astra_start_prompt.md) | 코드보다 설계 우선, 컴포넌트·상태·매트릭스·토큰·5가지 결정 |
| [Pleos 문서 인덱스와 설계 지침](chakak_pleos_docs_for_astra.md) | headless 목표, 주차 후 UI, Compose·Room·Adapter·Mock, SDK 미확인 구분 |
| [전체 좌측 메뉴 URL](pelos_guide_url.txt) | 공식 원문 탐색용; URL 수집을 원문 전체 열람이나 SDK 검증으로 간주하지 않음 |
| [GitHub 제출 안내](git-manual.md) | release APK 이름·경로, Releases 대안, Private 접근, README 요구 |
| [디자인 에셋](docs/Pleos_Connect_design_assets_215.fig) | 색상·타입·컴포넌트의 기준 자료 |
| [디스플레이 가이드](docs/Pleos_Connect_display_guide_215.fig) | 창 크기·방향·테마·폼 팩터 기준 자료 |

플랫폼 동작·디자인은 공식 원문을 우선하며 로컬 인덱스의 요약은 원문과 대조한다. 공모전 저장소 제출 조건은 제공된 git-manual.md를 따른다. 제품별 선택은 차칵 설계안으로 명시한다. 인덱스의 44개 링크는 이번 대화에서 원문을 열어 확인했으며, 전체 URL 목록의 911개 고유 링크를 모두 읽었다는 뜻은 아니다. SDK 실행·승인·Figma 전체 렌더링은 별도 검증으로 남는다.

## Pleos 참고 자료 및 검토 범위

확인일: 2026-11-03. 아래 문서에서 연결된 디자인 하위 페이지와 차칵에 관련된 개발·검증 문서를 확인했다. 웹의 공개 설명, 로컬 디자인 자료, 실제 프로젝트에서 검증해야 할 사항을 구분한다.

| 자료 | 적용 내용 |
| --- | --- |
| [UI Design Guide](https://document.pleos.ai/docs/connect/guide/docs-design/Introduction/intro) | Getting Started의 Layout·Status bar·GNB·App library·Window Policy, Foundations의 색상·타이포그래피·아이콘·앱 아이콘·애니메이션·그림자, Components 하위 문서 검토 |
| [앱 개발 시작하기](https://document.pleos.ai/docs/pleos-only/getting-started) | 프로젝트/SDK 신청, 환경 구성, 앱 설계·개발, Vehicle 권한·Capability·Get, Virtual Console·폼 팩터 테스트, 제출 가이드 검토 |
| [Gleo Interface](https://document.pleos.ai/docs/pleos-only/app-integration-guide/guide/core-concepts) | App Schema, Callback, 코드 예제, 스키마 전달 문서 검토 |
| [FusedLocation](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/FusedLocation/intro) | 의존성, 권한, 위치 접근, 초기화·해제, 오류 처리 문서 검토 |
| [Design assets 2.1.5](docs/Pleos_Connect_design_assets_215.fig) | 색상 토큰, Asta Sans, 아이콘 그리드, 버튼·필드·팝업 등의 텍스트 및 컴포넌트 이름 확인 |
| [Display guide 2.1.5](docs/Pleos_Connect_display_guide_215.fig) | Connect-S/W/Waterfall, Portrait/Landscape, Light/Dark 및 브랜드별 변형 이름 확인 |

Figma 파일은 아카이브의 메타데이터·썸네일과 압축 해제한 캔버스의 텍스트를 확인했다. 모든 프레임의 렌더링과 픽셀 단위 치수 검증을 완료한 것은 아니다. 따라서 정확한 spacing·터치 영역·토큰 값은 디자인 구현 시 원본 Figma에서 검증한다. 로컬 파일의 Connect-L 표기는 현재 웹 가이드의 Connect-S 12.9인치 분류와 구분하고, 파일명 `215`를 SDK 또는 Emulator 버전으로 해석하지 않는다.

부분 저장, 주차 시 터치 기록, 두 개의 Action, 5초 수집 예산, 삭제 확인은 이 명세에서 정한 차칵 설계안이다. 공식 플랫폼 요구와 제품 선택을 혼동하지 않는다. SDK의 실제 실행·권한 승인·실차 지원을 이번 문서 검토만으로 검증한 것은 아니다.

## 공모전 맥락

- 현대자동차그룹 차량용 앱 개발 공모전
- 슬로건: Code your App into Pleos App Market
- 과제: Pleos Playground 공개 API를 활용한 차량용 앱 아이디어·개발

### 기존 기획서에 기재된 평가 항목

| 평가 항목 | 차칵의 대응 방향 |
| --- | --- |
| 차량 특화성 | 주행 중 음성 기록, 차량 상태와 위치 결합, 주차 후 상세 조작 |
| 창의성 | 다시 찾을 장소와 정비 상담 상황을 하나의 순간 기록 흐름으로 처리 |
| 기술 활용성 | Gleo 음성 연동과 위치·Vehicle SDK 활용 계획 검증 |
| 사업성 | 대상 운전자, 반복 사용 가치, 로컬 저장 기반 운영 및 확장 가능성 |
| 가산점 | 시연 영상, GitHub 소스 코드, APK 제출 준비 |

공모전 정보는 기존 기획서에서 옮겼으며, 제출 전 최신 공식 안내를 확인한다.

### 검토 문서

- 구조 참고: `/Users/hannah.b/Module/moniq/spec.md`

### 사업성 및 확장 검토

- 장소 기록을 일상적 이용 계기로, 차량 상태 기록을 정비 상담 시 활용하는 가치로 검증한다.
- 첫 기록 생성률, 기록 재열람률, 4주 내 재사용률은 파일럿 측정 후보이며 정의·대상·기간을 확정해야 한다.
- 예상 MAU를 제시할 경우 지원 차량 규모·도달 이용자 수·설치율·활성화율의 가정을 함께 제시한다.
- 휴대폰 연동, 동의 기반 백업·동기화, 내비게이션 및 정비 상담 연계는 SDK 지원과 이용 조건 확인 후 검토한다.
- 확장 시 지도·음성·동기화 서비스 비용을 확인하고 차량별 지원 차이를 반영한다.
