package olive.oliveyoung.member.user.dto.request;

import lombok.*;

/* 회원가입 시 전송하는 request dto */

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpRequest {
    private String userName;
    private String userId;
    private String password;
    private String phone;
    private String email;
}
