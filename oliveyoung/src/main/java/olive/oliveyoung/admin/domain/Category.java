package olive.oliveyoung.admin.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private List<Product> products = new ArrayList<>();

    //카테고리 계층구조 필드
    @ManyToOne
    @JoinColumn(name="PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //연관관계 메소드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

    public void addProduct(Product product){
        products.add(product);
    }


    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChild() {
        return child;
    }

    public void setChild(List<Category> child) {
        this.child = child;
    }

    private boolean isActive; // 노출 여부
    private int sortOrder;    // 정렬 순서
}
