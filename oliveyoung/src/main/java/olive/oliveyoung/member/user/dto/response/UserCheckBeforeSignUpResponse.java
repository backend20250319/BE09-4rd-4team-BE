package olive.oliveyoung.member.user.dto.response;

import lombok.*;

/* 서버가 클라이언트에게 중복 여부를 알려주기 위한 dtos */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCheckBeforeSignUpResponse {

    private boolean isDuplicate;

}
