package olive.oliveyoung.admin.repository;

import jakarta.persistence.Embeddable;

@Embeddable
public enum ProductStatus {

    판매중,
    품절임박,
    품절
}
