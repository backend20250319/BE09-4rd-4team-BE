package olive.oliveyoung.member.user.controller;

import olive.oliveyoung.member.user.common.ApiResponse;
import olive.oliveyoung.member.user.common.CustomUserDetails; // CustomUserDetails import 추가
import olive.oliveyoung.member.user.domain.User;
import olive.oliveyoung.member.user.dto.request.DuplicateCheckRequest;
import olive.oliveyoung.member.user.dto.request.UserSignUpRequest;
import olive.oliveyoung.member.user.dto.request.UserWithdrawRequest;
import olive.oliveyoung.member.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/* 회원가입, 회원 정보 수정, 회원 탈퇴 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     * 회원가입 전 회원 중복 체크
     */
    @PostMapping("/user/checkduplicate")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkDuplicate(@RequestBody DuplicateCheckRequest request) {
        boolean isDuplicate = userService.existsByUserNameAndPhone(request.getUserName(), request.getPhone());

        Map<String, Boolean> result = new HashMap<>();
        result.put("isDuplicate", isDuplicate);

        return ResponseEntity.ok(
                ApiResponse.success(result, HttpStatus.OK.value())
        );
    }

    /**
     * 회원가입
     */
    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
        userService.signUp(userSignUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .success(true)
                        .message("회원가입이 완료되었습니다.")
                        .status(HttpStatus.CREATED.value())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/mypage/withdrawal")
    public ResponseEntity<ApiResponse<Void>> withdraw(
            @AuthenticationPrincipal CustomUserDetails customUserDetails, // CustomUserDetails로 변경
            @RequestBody UserWithdrawRequest request) {

        User user = customUserDetails.getUser(); // CustomUserDetails에서 User 객체 가져오기
        userService.withdraw(user.getUserId(), request);

        String farewellMessage = user.getUserName() + "님, 안녕히 가십시오."; // 사용자 이름 포함 메시지
        String fullMessage = "회원탈퇴가 완료되었습니다. " + farewellMessage; // 기존 메시지와 결합

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message(fullMessage) // 수정된 메시지 사용
                        .status(HttpStatus.OK.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}

