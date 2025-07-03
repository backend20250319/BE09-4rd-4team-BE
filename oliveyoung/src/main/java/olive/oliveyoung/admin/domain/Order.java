package olive.oliveyoung.admin.domain;

import jakarta.persistence.*;

@Entity
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false )
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="product_id", nullable = false)
    private Product product;

    private Integer quantity;

}
