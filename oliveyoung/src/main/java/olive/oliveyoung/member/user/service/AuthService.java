package olive.oliveyoung.member.user.service;

import jakarta.servlet.http.HttpServletRequest;
import olive.oliveyoung.member.user.dto.request.LoginRequest;
import olive.oliveyoung.member.user.dto.response.TokenResponse;

public interface AuthService {

    public TokenResponse login(LoginRequest loginRequest);

    public TokenResponse refresh(HttpServletRequest request);

    public void logout(HttpServletRequest request);

}
