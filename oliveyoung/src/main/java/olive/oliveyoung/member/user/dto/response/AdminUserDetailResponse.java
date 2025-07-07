package olive.oliveyoung.member.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import olive.oliveyoung.member.user.Role;

import java.time.LocalDateTime;

/* 관리자가 사용자 목록에서 특정 사용자 한 명을 클릭하여 상세 정보를 조회할 때 사용하는 response dto */
@Getter
@AllArgsConstructor
@Builder
public class AdminUserDetailResponse {
    private Long userId;
    private String userName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
}
