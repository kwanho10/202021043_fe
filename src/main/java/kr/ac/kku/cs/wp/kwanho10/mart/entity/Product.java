package kr.ac.kku.cs.wp.kwanho10.mart.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;

@Entity
@Table(name = "products")
public class Product {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String category;
    
    
    @Column(nullable = false)
    private Long price;
    
    @Lob
    @Column(nullable = false, length = 3000)
    private String itemDetail; //상품 상세 설명
    
 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @Column(nullable = true)
    private String imgUrl;
    
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Long getPrice() {
		return price;
	}
    public void setPrice(Long price) {
		this.price = price;
	}
    

    public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}
    
    public String getItemDetail() {
		return itemDetail;
	}
    
    
    public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
    
    public String getImgUrl() {
		return imgUrl;
	}
    
}




