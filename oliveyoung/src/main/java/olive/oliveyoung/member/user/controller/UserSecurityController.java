package olive.oliveyoung.member.user.controller;

import lombok.RequiredArgsConstructor;
import olive.oliveyoung.member.user.common.ApiResponse;
import olive.oliveyoung.member.user.common.CustomUserDetails;
import olive.oliveyoung.member.user.dto.request.PasswordUpdateRequest;
import olive.oliveyoung.member.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/* 비밀번호 변경 등 보안 로직 분리 */
@RestController
@RequiredArgsConstructor
public class UserSecurityController {

    private final UserService userService;

    /**
     * 비밀번호 변경
     */
    @PatchMapping("/mypage/user/modifyactinfo/modifypwd")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody PasswordUpdateRequest request) {

        userService.updatePassword(customUserDetails.getUser().getUserId(), request);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("비밀번호가 성공적으로 변경되었습니다.")
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }
}
