package olive.oliveyoung.member.user.service;

import olive.oliveyoung.member.user.dto.request.PasswordUpdateRequest;
import olive.oliveyoung.member.user.dto.request.UserSignUpRequest;
import olive.oliveyoung.member.user.dto.request.UserUpdateRequest;
import olive.oliveyoung.member.user.dto.request.UserWithdrawRequest;
import olive.oliveyoung.member.user.dto.response.UserInfoResponse;


public interface UserService {

    UserInfoResponse signUp(UserSignUpRequest request);

    void withdraw(String username, UserWithdrawRequest request);

    void updatePassword(String userId, PasswordUpdateRequest request);

    void updateUser(String userId, UserUpdateRequest userUpdateRequest);

    boolean existsByNameAndPhone(String userName, String phone);

    boolean existsByUserName(String userName);

    boolean isUserIdDuplicate(String userId);

    boolean existsByPhone(String phone);
}
