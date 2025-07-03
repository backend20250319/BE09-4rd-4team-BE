package olive.oliveyoung.admin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="BRAND")
public class Brand {

    @Id
    @GeneratedValue
    private Long id;
    private String name;


}
