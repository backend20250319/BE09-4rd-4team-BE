package olive.oliveyoung.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import olive.oliveyoung.member.user.common.CustomUserDetails; // CustomUserDetails import 추가
import olive.oliveyoung.member.user.domain.User; // User import 추가
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 요청 헤더의 JWT를 파싱해 인증 처리하는 필터
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(jwtConfig.getHeader());

        if (authHeader != null && authHeader.startsWith(jwtConfig.getPrefix() + " ")) {
            String token = authHeader.substring(jwtConfig.getPrefix().length() + 1);

            if (jwtTokenProvider.validateToken(token)) {
                User user = jwtTokenProvider.getUserFromJWT(token); // User 객체 가져오기
                CustomUserDetails customUserDetails = new CustomUserDetails(user); // CustomUserDetails 생성

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(customUserDetails, null,
                                customUserDetails.getAuthorities()); // CustomUserDetails와 권한 사용

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
