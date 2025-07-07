package olive.oliveyoung.member.user.service;

import olive.oliveyoung.member.user.dto.request.UserSignUpRequest;
import olive.oliveyoung.member.user.dto.request.UserWithdrawRequest;


public interface UserService {

    public void signUp(UserSignUpRequest request);

    public void withdraw(String username, UserWithdrawRequest request);

}
