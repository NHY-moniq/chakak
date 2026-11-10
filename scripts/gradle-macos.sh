#!/bin/bash
set -euo pipefail
cd "$(dirname "$0")/.."
if [ "$(uname -s)" != Darwin ]; then
    exec ./gradlew "$@"
fi
if [ -z "${JAVA_HOME:-}" ]; then
    export JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home'
fi
# A temporary project build truststore, retaining TLS certificate validation.
trust_dir="$(mktemp -d "${TMPDIR:-/tmp}/chakak-build-trust.XXXXXX")"
trap 'rm -rf "$trust_dir"' EXIT
"$JAVA_HOME/bin/java" scripts/MacBuildTrust.java "$trust_dir/trust.p12"
./gradlew --no-daemon \
    -Djavax.net.ssl.trustStore="$trust_dir/trust.p12" \
    -Djavax.net.ssl.trustStoreType=PKCS12 \
    -Djavax.net.ssl.trustStorePassword=changeit \
    "$@"
