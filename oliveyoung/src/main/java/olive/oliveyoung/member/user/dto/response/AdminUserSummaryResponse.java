package olive.oliveyoung.member.user.dto.response;

import lombok.Getter;
import olive.oliveyoung.member.user.Role;

/* 관리자 페이지에서 여러 명의 사용자 목록을 보기 위한 response dto */
@Getter
public class AdminUserSummaryResponse {
    private Long userId;
    private String email;
    private String name;
    private Role role;
    private java.time.LocalDateTime createdAt;
}
