#!/bin/bash
set -euo pipefail
cd "$(dirname "$0")"

finish() {
    result=$?
    if [ "$result" -ne 0 ]; then
        printf '\n실패했습니다. 위 오류를 확인해 주세요. (종료 코드: %s)\n' "$result"
    fi
    if [ -t 0 ]; then
        read -r -p 'Enter를 누르면 종료합니다. ' ignored || true
    fi
}
trap finish EXIT

sdk_dir="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-$HOME/Library/Android/sdk}}"
adb="$sdk_dir/platform-tools/adb"
emulator="$sdk_dir/emulator/emulator"
if [ ! -x "$adb" ]; then
    printf 'Android SDK의 adb를 찾을 수 없습니다: %s\n' "$adb" >&2
    exit 1
fi
if [ -z "${JAVA_HOME:-}" ] && [ -d '/Applications/Android Studio.app/Contents/jbr/Contents/Home' ]; then
    export JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home'
fi

printf '차칵 테스트 및 Pleos용 APK 빌드 중…\n'
./scripts/gradle-macos.sh :core:test :app:testMockDebugUnitTest :app:testRealDebugUnitTest :app:assembleRealDebug

"$adb" start-server
find_pleos() {
    local device
    while read -r device state rest; do
        case "$device" in
            emulator-*)
                if [ "$state" = device ] && "$adb" -s "$device" emu avd name 2>/dev/null | tr -d '\r' | grep -qx 'chakak_pleos'; then
                    printf '%s' "$device"
                    return 0
                fi
                ;;
        esac
    done < <("$adb" devices)
    return 1
}
serial="$(find_pleos || true)"
if [ -z "$serial" ]; then
    printf 'Pleos 에뮬레이터 시작 중…\n'
    emulator_log="$(mktemp -t chakak-pleos)"
    nohup "$emulator" -avd chakak_pleos > "$emulator_log" 2>&1 < /dev/null &
    printf '에뮬레이터 로그: %s\n' "$emulator_log"
fi
for ((attempt=0; attempt<120; attempt++)); do
    serial="$(find_pleos || true)"
    if [ -n "$serial" ] && [ "$("$adb" -s "$serial" shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" = 1 ]; then
        break
    fi
    sleep 2
done
if [ -z "$serial" ] || [ "$("$adb" -s "$serial" shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" != 1 ]; then
    printf 'Pleos 에뮬레이터가 4분 안에 준비되지 않았습니다. 에뮬레이터 화면과 로그를 확인해 주세요.\n' >&2
    exit 1
fi
printf '차칵 설치 및 실행 중…\n'
"$adb" -s "$serial" install -r app/build/outputs/apk/real/debug/app-real-debug.apk
"$adb" -s "$serial" shell am start -W -n ai.chakak/.MainActivity
printf '\n완료! 차칵에서 P(주차) 상태를 확인하고, 필요한 경우 위치 권한을 허용해 주세요.\n'
