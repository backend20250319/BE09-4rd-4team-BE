package olive.oliveyoung.member.user.service.impl;

import jakarta.transaction.Transactional;
import olive.oliveyoung.config.jwt.JwtTokenProvider;
import olive.oliveyoung.member.user.Role;
import olive.oliveyoung.member.user.domain.User;
import olive.oliveyoung.member.user.dto.request.UserSignUpRequest;
import olive.oliveyoung.member.user.repository.UserRepository;
import olive.oliveyoung.member.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

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
}
