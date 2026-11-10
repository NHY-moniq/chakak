#!/bin/bash
set -euo pipefail
cd "$(dirname "$0")"
finish() {
    result=$?
    if [ "$result" -ne 0 ]; then printf '\nSDK 확인 실패 (코드 %s). 위 오류를 확인해 주세요.\n' "$result"; fi
    if [ -t 0 ]; then read -r -p 'Enter를 누르면 종료합니다. ' ignored || true; fi
}
trap finish EXIT
if [ -z "${JAVA_HOME:-}" ] && [ -d '/Applications/Android Studio.app/Contents/jbr/Contents/Home' ]; then
    export JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home'
fi
./scripts/gradle-macos.sh :app:exportNaviSdk
python3 - <<'PY'
import os
from pathlib import Path
import subprocess
import zipfile
root = Path('app/build/navi-inspection')
jars = []
for artifact in sorted((root / 'artifacts').iterdir()):
    if artifact.suffix == '.aar':
        target = root / (artifact.stem + '-classes.jar')
        with zipfile.ZipFile(artifact) as archive:
            target.write_bytes(archive.read('classes.jar'))
        jars.append(str(target))
    elif artifact.suffix == '.jar':
        jars.append(str(artifact))
javap = str(Path(os.environ['JAVA_HOME']) / 'bin/javap') if os.environ.get('JAVA_HOME') else 'javap'
classes = ['ai.pleos.playground.navi.data.CurrentLocationInfo',
           'ai.pleos.playground.navi.helper.NaviHelper',
           'ai.pleos.playground.navi.helper.listener.NaviHelperEventListener']
result = subprocess.run([javap, '-public', '-classpath', os.pathsep.join(jars), *classes],
                        text=True, capture_output=True, check=True)
report = root / 'api.txt'
report.write_text(result.stdout)
print('SDK 명세 확인 완료:', report.resolve())
print(result.stdout)
PY
