package olive.oliveyoung.member.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* 로그인한 회원이 자신의 정보를 수정할 때 쓰는 request dto */
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {

    private String userName;
    private String userId;
    private String password;
    private String phone;
    private String email;

}
