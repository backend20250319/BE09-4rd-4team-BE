package olive.oliveyoung.admin.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "stocklog")
public class StockLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockLogId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int beforeStock;
    private int afterStock;

    @CreationTimestamp
    private LocalDateTime changedAt;

    private String reason;

    @PrePersist
    public void prePersist() {
        this.changedAt = LocalDateTime.now();
    }
}

