#!/bin/bash
set -euo pipefail
adb_bin="${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}/platform-tools/adb"
serial="${1:-emulator-5554}"
"$adb_bin" -s "$serial" get-state
printf '\n내비게이션 서비스 설치 여부\n'
"$adb_bin" -s "$serial" shell pm path ai.pleos.playground.service.navi
printf '\n내비게이션 서비스 버전과 구성\n'
"$adb_bin" -s "$serial" shell dumpsys package ai.pleos.playground.service.navi | grep -E 'versionName|versionCode|NaviService|NAVI_START|enabled=' || true
printf '\n차칵 내비게이션 권한\n'
"$adb_bin" -s "$serial" shell dumpsys package ai.chakak | grep -F 'pleos.car.permission.NAVI_ROUTE' || true
printf '\n최근 내비게이션 관련 진단 로그 (공유 전 위치 등 개인정보를 확인해 주세요)\n'
"$adb_bin" -s "$serial" logcat -d -t 1500 -v brief | grep -Ei 'ChakakNavi|NaviHelper|NaviService|navi.helper|service.navi' | tail -100 || true

printf '\n현재 Android 사용자\n'
"$adb_bin" -s "$serial" shell am get-current-user
printf '\n설치된 내비게이션 관련 패키지\n'
"$adb_bin" -s "$serial" shell pm list packages | grep -Ei 'navi|navigation|map' || true
printf '\n지금부터 45초 동안 차칵 진단 로그를 수집합니다. 앱에서 새 기록을 한 번 캡처해 주세요.\n'
log_file="$(mktemp "${TMPDIR:-/tmp}/chakak-navi-live.XXXXXX")"
"$adb_bin" -s "$serial" logcat -T 1 -v brief 'ChakakNavi:I' '*:S' > "$log_file" 2>&1 &
log_pid=$!
trap 'kill "$log_pid" 2>/dev/null || true' EXIT
sleep 45
kill "$log_pid" 2>/dev/null || true
wait "$log_pid" 2>/dev/null || true
trap - EXIT
printf '\n캡처 중 차칵 진단 로그\n'
cat "$log_file"
printf '\n진단 로그 저장 위치: %s\n' "$log_file"
if ! grep -q 'capture lookup started' "$log_file"; then
    printf '새 캡처 시작 로그가 없습니다. 최신 APK 설치 여부와 새 기록 실행 여부를 확인해 주세요.\n'
fi
printf '\n내비게이션 서비스 연결 상태\n'
"$adb_bin" -s "$serial" shell dumpsys activity services ai.pleos.playground.service.navi
