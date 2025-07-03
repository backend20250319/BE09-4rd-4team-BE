package olive.oliveyoung.admin.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private String name;
    private Double discount;

    private LocalDateTime validUntil;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @CreationTimestamp
    private Integer createdBy; // 관리자 ID
    private Integer updatedBy;
}
