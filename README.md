# ChaKak

음성 요청 시점의 위치와 차량 관측값을 차량 내에 저장하고, 주차 후 검토·편집하는 Pleos Connect 앱입니다. 구현 기준은 [spec-en.md](spec-en.md)입니다.

## 실행

Android Studio에서 이 디렉터리를 열고 Android SDK 35 및 JDK 17 이상을 설정합니다. 로컬 SDK 경로는 추적하지 않는 `local.properties`에 지정합니다. Android Studio의 내장 JBR도 사용할 수 있습니다.

```sh
./gradlew :core:test :app:testMockDebugUnitTest
./gradlew :app:assembleMockDebug :app:assembleRealDebug
./gradlew :app:lintMockDebug :app:lintRealDebug
```

- `mockDebug`: 일반 Android 환경에서 화면과 테스트 데이터를 확인합니다. 패키지는 `ai.chakak.mock`이며 실제 데이터로 전환하거나 자동 대체하지 않습니다. `MockPlatform.scenario`에 성공·부분 실패·권한 거부·미지원·오류·시간 초과가 정의되어 있습니다.
- `realDebug`: Vehicle 2.3.2, FusedLocation 1.1.3 사용. 패키지는 `ai.chakak`입니다. 실제 Pleos 서비스와 권한이 필요합니다. 주차 및 CarUxRestrictions를 모두 확인하지 못하면 화면 조작을 차단합니다.
- 모의 release 변형은 생성하지 않습니다. debug APK는 제출용 release APK가 아닙니다.

APK 경로:

- `app/build/outputs/apk/mock/debug/app-mock-debug.apk`
- `app/build/outputs/apk/real/debug/app-real-debug.apk`

## 현재 확인 범위

[0–4단계 실행 기록](docs/technical/implementation-status.md)에 구현·검증·외부 조건을 구분했습니다. 테스트는 JVM 및 Robolectric에서 실행했으며 Pleos 에뮬레이터나 차량에서 실행한 결과가 아닙니다.

Gleo 스키마 및 수신 코드는 준비되어 있으나 기본적으로 비활성 상태입니다. 프로젝트 승인, 실제 호출자 서명, 발신 UID 전달, 후속 메모 계약이 검증되지 않았습니다. [연동 활성화 조건](docs/technical/gleo-integration.md)을 확인해야 합니다. 자체 마이크 상시 청취·호출어 기능은 없습니다. NaviHelper 및 앱 자체 STT/TTS는 현재 spec-en.md의 초기 경로에 포함되지 않습니다.

설계 입력 `.fig`와 기존 참고 문서는 `docs/`에 있으며 기존 Git 제외 규칙을 유지합니다. 새 설계·검증 문서는 추적 대상입니다. GitHub 업로드와 제출용 서명·배포는 5–6단계 작업으로 이번 범위에 포함하지 않았습니다.
