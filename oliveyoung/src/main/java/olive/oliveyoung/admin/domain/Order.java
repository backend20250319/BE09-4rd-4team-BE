package olive.oliveyoung.admin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Order {

    @Id @GeneratedValue
    private Long id; // 동일 ID로 공유 가능

    private OrderStatus status;
    private boolean flagged; // 이상 주문 여부
    private String memo;     // 처리 내역 메모

}
