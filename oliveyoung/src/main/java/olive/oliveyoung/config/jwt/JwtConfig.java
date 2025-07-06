package olive.oliveyoung.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct; // Import PostConstruct

/* JWT 관련 환경변수 모음 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {
    private String secret;
    private long jwtExpiration;
    private long jwtRefreshExpiration; // ✅ 추가
    private String header;
    private String prefix;

    @PostConstruct
    public void init() {
        System.out.println("JWT Secret Key loaded: " + (secret != null ? secret.substring(0, Math.min(secret.length(), 10)) + "..." : "null"));
    }
}
