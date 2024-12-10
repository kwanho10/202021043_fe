package kr.ac.kku.cs.wp.kwanho10.user.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Order;
import kr.ac.kku.cs.wp.kwanho10.mart.entity.Product;


@Entity
@Table(name = "`user`")
public class User {

    @Id
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "email", length = 200, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "status", length = 50, nullable = false)
    private String status;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "point")
    private Long point=0L;
    
    @Column(name = "salt", length = 200)
    private String salt;
    
        
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    public User() {}

    public User(String id, String name, String email, String password, String status, String address, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setPoint(Long point) {
		this.point = point;
	}
    
    public Long getPoint() {
		return point;
	}

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    
    public void setSalt(String salt) {
		this.salt = salt;
	}
    
    public String getSalt() {
		return salt;
	}

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(status, user.status) &&
                Objects.equals(address, user.address) &&
                Objects.equals(createdAt, user.createdAt) &&
                Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, status, address, createdAt, updatedAt);
    }

	
    
    
    
    
}
