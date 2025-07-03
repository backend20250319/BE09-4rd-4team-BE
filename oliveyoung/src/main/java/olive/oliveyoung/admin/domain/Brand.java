package olive.oliveyoung.admin.domain;

import jakarta.persistence.*;

@Entity
@Table(name="BRAND")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    private String brandName;

}
