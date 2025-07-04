package olive.oliveyoung.admin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id @GeneratedValue
    private Long id; // 동일하게 참조

    private String status;        // 활성/비활성
    private boolean isBlacklisted;
    private String adminNote;
}
