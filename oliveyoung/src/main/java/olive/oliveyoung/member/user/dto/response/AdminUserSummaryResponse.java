package olive.oliveyoung.member.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import olive.oliveyoung.member.user.Role;

/* 관리자 페이지에서 여러 명의 사용자 목록을 보기 위한 response dto */
@Getter
@AllArgsConstructor
@Builder
public class AdminUserSummaryResponse {

    private Long userId;
    private String userName;
    private String email;
    private String phone;
    private Role role;
    private java.time.LocalDateTime createdAt;
}
