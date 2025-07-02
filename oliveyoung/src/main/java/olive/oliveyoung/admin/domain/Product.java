package olive.oliveyoung.admin.domain;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;


}
