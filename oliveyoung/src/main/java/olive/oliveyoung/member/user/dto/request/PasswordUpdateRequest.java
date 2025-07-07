package olive.oliveyoung.member.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* 비밀번호를 수정할 때 쓰는 request dto */
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordUpdateRequest {
    private String password;
    private String newPassword;
}
