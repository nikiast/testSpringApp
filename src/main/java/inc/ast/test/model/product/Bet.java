package inc.ast.test.model.product;

import inc.ast.test.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Bet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product productId;

    private Integer price;

    protected Bet() {
    }

    public Bet(User userId, Product productId, Integer price) {
        this.userId = userId;
        this.productId = productId;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int compareTo(Bet o) {
        return this.getPrice().compareTo(o.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return Objects.equals(getId(), bet.getId()) && Objects.equals(getUserId(), bet.getUserId())
                && Objects.equals(getProductId(), bet.getProductId()) && Objects.equals(getPrice(), bet.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getProductId(), getPrice());
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", price='" + price + '\'' +
                '}';
    }

//    public static void main(String[] args) {
//        Bet a = new Bet(new User("111","111",true, Role.USER), new Product("111", "111"), 5);
//        Bet b = new Bet(new User("222","222",true, Role.USER), new Product("222", "222"), 10);
//        System.out.println(a.compareTo(b));
//    }
}
