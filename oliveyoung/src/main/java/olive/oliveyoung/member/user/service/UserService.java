package olive.oliveyoung.member.user.service;

import olive.oliveyoung.member.user.dto.request.PasswordUpdateRequest;
import olive.oliveyoung.member.user.dto.request.UserSignUpRequest;
import olive.oliveyoung.member.user.dto.request.UserUpdateRequest;
import olive.oliveyoung.member.user.dto.request.UserWithdrawRequest;


public interface UserService {

    void signUp(UserSignUpRequest request);

    void withdraw(String username, UserWithdrawRequest request);

    boolean existsByUserNameAndPhone(String userName, String phone);

    void updatePassword(String userId, PasswordUpdateRequest request);

    void updateUser(String userId, UserUpdateRequest userUpdateRequest);
}
