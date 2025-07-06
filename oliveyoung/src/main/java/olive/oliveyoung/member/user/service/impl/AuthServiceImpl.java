package olive.oliveyoung.member.user.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import olive.oliveyoung.config.jwt.JwtTokenProvider;
import olive.oliveyoung.member.user.domain.User;
import olive.oliveyoung.member.user.dto.request.LoginRequest;
import olive.oliveyoung.member.user.dto.response.TokenResponse;
import olive.oliveyoung.member.user.service.AuthService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;


    @Override
    public TokenResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new BadCredentialsException("올바르지 않은 아이디 혹은 비밀번호"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getUserPwd())) {
            throw new BadCredentialsException("올바르지 않은 아이디 혹은 비밀번호");
        }

        String accessToken = jwtTokenProvider.createToken(
                user.getUserId(),
                user.getRole().name(),
                user.getGender(),
                user.getAge()
        );
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId(), user.getRole().name());

        RefreshToken tokenEntity = RefreshToken.builder()
                .userId(user.getUserId())
                .token(refreshToken)
                .expiryDate(
                        new Date(System.currentTimeMillis()
                                + jwtTokenProvider.getRefreshExpiration())
                )
                .build();

        refreshTokenRepository.save(tokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse refresh(HttpServletRequest request) {
    // TODO: 토큰 재발급 로직 구현
        return null;
    }

    @Override
    public void logout(HttpServletRequest request) {
    // TODO: 로그아웃 로직 구현
    }
}


