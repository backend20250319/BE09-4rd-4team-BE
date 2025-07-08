package olive.oliveyoung.member.user.domain;

import jakarta.persistence.*;
import lombok.*;
import olive.oliveyoung.member.user.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "users")
@ToString(exclude = "addresses")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String userId;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @Column(nullable = false, length = 20)
    private String phone;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Address> addresses = new ArrayList<>();

    @Enumerated(EnumType.STRING) // Enum 타입을 문자열 자체로 저장 (e.g., "USER", "ADMIN")
    @Column(nullable = false)
    private Role role;

    // 비밀번호 업데이트 메서드 추가
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    // 연관관계 편의 메서드
    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }

    // 사용자 정보 업데이트 메서드
    public void updateUserInfo(String userName, String email, String phone) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }
}
