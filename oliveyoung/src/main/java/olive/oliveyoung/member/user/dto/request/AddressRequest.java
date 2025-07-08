package olive.oliveyoung.member.user.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressRequest {
    private String addressName; // 배송지명
    private String recipientName; // 받는 분
    private String phone; // 연락처
    private String zipcode; // 우편번호
    private String streetAddress; // 도로명 주소
    private String streetNumber; // 지번
    private String detailAddress; // 상세 주소
    private boolean isDefault; // 기본 배송지 여부
}
