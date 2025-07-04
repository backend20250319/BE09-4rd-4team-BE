package olive.oliveyoung.member.user.dto.response;

import lombok.Getter;

@Getter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
