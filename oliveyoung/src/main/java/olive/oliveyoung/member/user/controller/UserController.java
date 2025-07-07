package olive.oliveyoung.member.user.controller;

import olive.oliveyoung.member.user.common.ApiResponse;
import olive.oliveyoung.member.user.domain.User;
import olive.oliveyoung.member.user.dto.request.UserSignUpRequest;
import olive.oliveyoung.member.user.dto.request.UserWithdrawRequest;
import olive.oliveyoung.member.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/* 회원가입, 회원 정보 수정, 회원 탈퇴 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp (@RequestBody UserSignUpRequest userSignUpRequest){
        userService.signUp(userSignUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(null, HttpStatus.CREATED.value()));
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdraw(
            @AuthenticationPrincipal User user, // 현재 로그인된 사용자 정보
            @RequestBody UserWithdrawRequest request) {

        // principal.getUserId()은 현재 로그인된 사용자의 ID를 반환합니다.
        userService.withdraw(user.getUserId(), request);

        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }
}
