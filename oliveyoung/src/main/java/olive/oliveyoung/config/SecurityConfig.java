package olive.oliveyoung.config;

import lombok.RequiredArgsConstructor;
import olive.oliveyoung.config.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/api/products/**", "/api/brands/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**/reviews").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/*/reviews/average-rating").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/*/reviews").authenticated()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/admin/users/create-admin").permitAll()
                        .requestMatchers("/api/auth/**").authenticated()
                        .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers("/api/mypage/**").hasRole("USER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  //  .permitAll()//// 관리자 API는 ADMIN 권한 필요
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}