package olive.oliveyoung.member.user.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import olive.oliveyoung.config.jwt.JwtTokenProvider;
import olive.oliveyoung.member.user.Role;
import olive.oliveyoung.member.user.domain.User;
import olive.oliveyoung.member.user.dto.request.UserSignUpRequest;
import olive.oliveyoung.member.user.dto.request.UserWithdrawRequest;
import olive.oliveyoung.member.user.repository.RefreshTokenRepository;
import olive.oliveyoung.member.user.repository.UserRepository;
import olive.oliveyoung.member.user.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 회원가입
     */
    @Transactional
    @Override
    public void signUp(UserSignUpRequest request) {
        // 1. 아이디 중복 체크
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        // 3. User 엔티티 생성
        User user = User.builder()
                .userId(request.getUserId())
                .password(encodedPassword)
                .userName(request.getUserName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(Role.USER) // 기본 권한 설정 (Role enum이 있다고 가정)
                .build();
        // 4. DB에 저장
        userRepository.save(user);
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    @Override
    public void withdraw(String userId, UserWithdrawRequest request) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadCredentialsException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // Refresh Token 삭제
        refreshTokenRepository.deleteByUserId(userId);

        // 사용자 삭제
        userRepository.delete(user);
    }
}
