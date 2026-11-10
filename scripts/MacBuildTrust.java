import java.nio.file.*;
import java.io.*;
import java.security.*;
import java.security.cert.X509Certificate;

/** Copies public trust anchors only; never exports private keys or changes the macOS keychain. */
public class MacBuildTrust {
    public static void main(String[] args) throws Exception {
        Path defaults = Path.of(System.getProperty("java.home"), "lib", "security", "cacerts");
        KeyStore javaTrust = KeyStore.getInstance(defaults.toFile(), "changeit".toCharArray());
        KeyStore combined = KeyStore.getInstance("PKCS12");
        combined.load(null, null);
        int defaultsCount = copyAuthorities(javaTrust, combined, "java-");
        KeyStore macTrust = KeyStore.getInstance("KeychainStore");
        macTrust.load(null, null);
        int macCount = copyAuthorities(macTrust, combined, "mac-");
        if (macCount == 0) {
            throw new IllegalStateException("Mac 키체인에서 신뢰된 CA를 읽지 못했습니다. 회사 CA의 신뢰 등록 상태를 확인해 주세요.");
        }
        try (OutputStream output = Files.newOutputStream(Path.of(args[0]))) {
            combined.store(output, "changeit".toCharArray());
        }
        System.out.println("빌드용 신뢰 저장소 준비: Java CA " + defaultsCount + "개, Mac CA " + macCount + "개");
    }

    private static int copyAuthorities(KeyStore from, KeyStore to, String prefix) throws Exception {
        int count = 0;
        var aliases = from.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            if (!from.isCertificateEntry(alias)) continue;
            var certificate = from.getCertificate(alias);
            if (!(certificate instanceof X509Certificate x509) || x509.getBasicConstraints() < 0) continue;
            to.setCertificateEntry(prefix + count++, certificate);
        }
        return count;
    }
}
