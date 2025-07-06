package olive.oliveyoung.member.user.dto.request;

import lombok.*;

/* 로그인할 때 쓰는 request dto */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String userID;
    private String password;
}
