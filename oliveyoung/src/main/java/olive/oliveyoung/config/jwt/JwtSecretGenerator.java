package olive.oliveyoung.config.jwt;

import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        try {
            // HMAC-SHA256 키 생성
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSha256");
            keyGen.init(256); // 256비트 키
            SecretKey secretKey = keyGen.generateKey();

            // Base64 인코딩된 키 문자열 출력
            String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println("Generated HMAC Secret Key (Base64):");
            System.out.println(base64Key);
        } catch (Exception e) {
            System.err.println("Error generating secret key: " + e.getMessage());
        }
    }
}