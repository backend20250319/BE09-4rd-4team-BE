package olive.oliveyoung.member.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordUpdateRequest {
    private String password;
    private String newPassword;
}
