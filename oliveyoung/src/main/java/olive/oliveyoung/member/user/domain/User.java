package olive.oliveyoung.member.user.domain;

import jakarta.persistence.*;
import lombok.*;
import olive.oliveyoung.member.user.Role;
import olive.oliveyoung.member.user.common.entity.BaseEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "user") // 'user' 테이블과 매핑됨을 명시
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "user_no") // PK 컬럼 이름 지정
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

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING) // Enum 타입을 문자열 자체로 저장 (e.g., "USER", "ADMIN")
    @Column(nullable = false)
    private Role role;


}
