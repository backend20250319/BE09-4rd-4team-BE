package olive.oliveyoung.member.user.domain;

import jakarta.persistence.*;
import lombok.*;
import olive.oliveyoung.member.user.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "users")
@ToString(callSuper = true)
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

    // 사용자 정보 업데이트 메서드 (기존 메서드는 삭제 또는 변경)
    // PATCH 요청에 맞춰 개별 필드 업데이트를 위한 setter 추가

}
