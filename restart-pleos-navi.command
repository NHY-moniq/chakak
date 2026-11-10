#!/bin/bash
set -euo pipefail
adb_bin="${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}/platform-tools/adb"
serial="${1:-emulator-5554}"
"$adb_bin" -s "$serial" get-state
android_user="$("$adb_bin" -s "$serial" shell am get-current-user | tr -d '\r')"
if ! [[ "$android_user" =~ ^[0-9]+$ ]]; then
    printf '현재 Android 사용자를 확인하지 못했습니다.\n' >&2
    exit 1
fi
printf '사용자 %s의 내비게이션 앱을 다시 시작합니다. 앱 데이터는 유지합니다.\n' "$android_user"
"$adb_bin" -s "$serial" shell am force-stop --user "$android_user" ai.umos.maps.android.navigation.app
"$adb_bin" -s "$serial" shell monkey --user "$android_user" -p ai.umos.maps.android.navigation.app -c android.intent.category.LAUNCHER 1
printf '\n20초 후 관련 로그를 수집합니다. 내비게이션 화면을 확인해 주세요.\n'
sleep 20
log_file="$(mktemp "${TMPDIR:-/tmp}/chakak-navi-restart.XXXXXX")"
"$adb_bin" -s "$serial" logcat -d -t 3000 -v time > "$log_file"
printf '\n관련 오류 및 내비게이션 로그\n'
grep -Ei 'NaviService|ai\.umos\.maps|UnknownHost|SSLHandshake|ConnectException|FATAL EXCEPTION' "$log_file" | tail -100 || true
printf '\n로그 파일: %s\n공유 전 위치 등 개인정보를 확인해 주세요.\n' "$log_file"
