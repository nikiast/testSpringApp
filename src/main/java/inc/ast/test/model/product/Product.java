package inc.ast.test.model.product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeOfProduct typeOfProducts;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bet> prices;

    protected Product() {
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeOfProduct getTypeOfProducts() {
        return typeOfProducts;
    }

    public void setTypeOfProducts(TypeOfProduct typeOfProducts) {
        this.typeOfProducts = typeOfProducts;
    }

    public List<Bet> getPrices() {
        return prices;
    }

    public void setPrices(List<Bet> prices) {
        this.prices = prices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) && Objects.equals(getName(), product.getName()) && Objects.equals(getDescription(), product.getDescription()) && getTypeOfProducts() == product.getTypeOfProducts() && Objects.equals(getPrices(), product.getPrices());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getTypeOfProducts(), getPrices());
    }

    @Override
    public String
    toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", typeOfProducts=" + typeOfProducts +
                '}';
    }
}