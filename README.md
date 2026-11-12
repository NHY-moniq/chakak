# ChaKak (차칵)

## 앱 이름 및 한 줄 소개

**ChaKak(차칵)** — 말 한마디로 그 순간의 위치와 차량 상태를 차량 안에 저장하고, 주차한 뒤 살펴보고 메모를 다는 Hyundai Pleos Connect 앱입니다.

- 주행 중에는 Gleo 음성("지금 위치 저장해줘", "타이어 상태 기록해줘")으로만 기록하고, 화면 조작은 주차(P) 상태에서만 열립니다.
- 기록은 차량 내 Room DB에만 저장합니다. 구현 기준 문서는 [spec-en.md](spec-en.md)(한국어판 [spec.md](spec.md))입니다.

## 실행 방법

1. `local.properties`에 Android SDK 경로를 적습니다.

   ```properties
   sdk.dir=/Users/<사용자>/Library/Android/sdk
   ```

2. JDK 17로 빌드합니다. 저장소의 `apk/메이비올낫_차칵_release.apk`를 그대로 쓸 때는 이 단계를 건너뜁니다.

   ```sh
   ./gradlew :app:assembleRealRelease   # Pleos 에뮬레이터·차량용 release (패키지 ai.chakak)
   ./gradlew :app:assembleMockDebug     # 일반 Android 기기용 모의 데이터 (패키지 ai.chakak.mock)
   ```

3. Pleos Connect 에뮬레이터를 띄우고 설치·실행합니다.

   ```sh
   adb install -r apk/메이비올낫_차칵_release.apk
   adb shell am start -n ai.chakak/.MainActivity
   ```

4. 기어가 P인 상태에서 화면의 "차량·위치 조회 권한 허용" 버튼을 눌러 권한을 허용한 뒤 기록합니다.

음성으로 기록하려면 앱을 한 번 실행한 뒤 Gleo에게 말하거나, 마이크 없이 문장을 주입합니다.

```sh
adb shell am broadcast --user 10 -a ai.umos.intent.action.ASR -p ai.umos.ambientai --es ASR "'차칵으로 지금 위치 저장해줘'"
```

## 테스트 환경 및 필요한 설정

| 항목 | 값 |
|---|---|
| JDK | 17 (Android Studio 내장 JBR 25는 Gradle 8.12에서 실패) |
| Gradle | 8.12 (wrapper) |
| Android SDK | compileSdk·targetSdk 35, minSdk 28 |
| Pleos SDK | Vehicle 2.3.2, FusedLocation 1.1.3, NaviHelper 3.0.5, SpeechToText 3.3.1 |
| 네트워크 | `real` 빌드는 Pleos Nexus(`nexus-playground.pleos.ai`)에 접근할 수 있어야 합니다. 막혀 있으면 `mock`만 빌드됩니다. |

```sh
./gradlew :core:test                      # 순수 JVM (캡처 엔진, 접근 게이트)
./gradlew :app:testMockDebugUnitTest      # Robolectric (Room 저장소)
./gradlew :app:testRealDebugUnitTest      # Robolectric (Gleo 스키마·리소스 계약)
./gradlew :app:lintMockDebug :app:lintRealDebug
```

단위 테스트는 JVM·Robolectric에서 실행합니다. Gleo 음성 기록(스키마 읽기 → `CAPTURE_RECORD` 호출 → 저장 → 응답)은 Pleos Connect v2.1.0 에뮬레이터에서 확인했고, 실차에서는 검증하지 않았습니다.

에뮬레이터에서 필요한 설정:

- **권한**: 앱 화면의 권한 버튼으로 허용합니다. adb로 줄 때는 사용자 10에 부여합니다.

  ```sh
  adb shell pm grant --user 10 ai.chakak pleos.car.permission.FUSED_LOCATION
  ```

- **기어 변경**: 기기 안 8088 포트의 Virtual Console을 씁니다. 세션 신호가 1초마다 와야 적용됩니다.

  ```sh
  adb forward tcp:18088 tcp:8088
  while true; do curl -s -X POST "http://127.0.0.1:18088/browser/session?client=cli" >/dev/null; sleep 1; done &
  curl -s -X PUT "http://127.0.0.1:18088/vehicle/gear?gear=Drive"   # Park | Reverse | Neutral | Drive
  ```

- **주행 중 음성 기록**: 주행(D) 중에도 Gleo 음성으로 기록합니다. 터치 화면은 주행 중 잠깁니다.
- **실제 마이크**: 에뮬레이터를 `-allow-host-audio`로 띄우고, 부팅이 끝난 뒤 `hostmicon`을 실행합니다. 실행 옵션만으로는 무음이 들어갑니다. 그다음 호출어 "글레오"로 Gleo를 깨워 말합니다.

  ```sh
  ~/Library/Android/sdk/emulator/emulator -avd Pleos_Connect_v2_1_0 -allow-host-audio
  ~/Library/Android/sdk/platform-tools/adb emu avd hostmicon
  ```
- **메모 음성 입력**: Pleos Playground 앱 등록 정보(Client ID·Secret)가 없어 기기 내장 인식으로 동작합니다.

## 개발에 사용한 Pleos Connect 에뮬레이터 버전과 화면 규격

| 항목 | 값 |
|---|---|
| 에뮬레이터 | Pleos Connect v2.1.0 (Android Automotive, API 34, arm64-v8a) |
| Gleo | 4.2.0 (`ai.umos.ambientai`) |
| 폼팩터 | Connect-S-17 (기본), 16:9, 17인치 |
| 전체 해상도 | 2560 × 1440 px, 160 dpi (1dp = 1px) |
| 앱 영역 | 가로 1700 × 1184 dp, 세로 840 × 1184 dp |

폭 840dp 이상이면 목록·상세 2단, 그보다 좁으면 단일 패널로 표시합니다.

## APK 파일 위치 또는 GitHub Release 링크

- 저장소: [`apk/메이비올낫_차칵_release.apk`](apk/메이비올낫_차칵_release.apk) — release 빌드, 버전 1.0.0, 패키지 `ai.chakak`
- GitHub Release: [v1.0.0](https://github.com/NHY-moniq/chakak/releases/tag/v1.0.0)

Android debug 키로 서명해 에뮬레이터에 바로 설치됩니다. 직접 빌드하면 `app/build/outputs/apk/real/release/app-real-release.apk`에 같은 APK가 생깁니다.
