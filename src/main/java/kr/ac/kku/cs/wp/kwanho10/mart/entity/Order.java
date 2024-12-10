package kr.ac.kku.cs.wp.kwanho10.mart.entity;

import jakarta.persistence.*;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 구매자

    @Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    public Order() {
        this.orderDate = new Date();
    }

    public Order(User user) {
        this.user = user;
        this.orderDate = new Date();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}
