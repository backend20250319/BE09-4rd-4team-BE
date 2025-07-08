package olive.oliveyoung.member.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String recipientName; // 받는 분

    private String phone; // 연락처

    private String zipcode; // 우편번호

    private String streetAddress; // 도로명 주소

    private String streetNumber; // 지번

    private String detailAddress; // 상세 주소

    private boolean isDefault; // 기본 배송지 여부

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
    }

    // 수정 메서드
    public void updateAddress(String recipientName, String phone, String zipcode, String streetAddress, String detailAddress, boolean isDefault) {
        this.recipientName = recipientName;
        this.phone = phone;
        this.zipcode = zipcode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.isDefault = isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
