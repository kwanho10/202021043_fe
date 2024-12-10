package kr.ac.kku.cs.wp.kwanho10.user.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {
   
	@EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;
    
    
    @Column(name = "role", length = 45)
    private String roleName;

    // 기본 생성자
    public UserRole() {}

    // 모든 필드를 포함하는 생성자
    public UserRole(User user, Role role, String roleName) {
        this.user = user;
        this.role = role;
        this.roleName = roleName;
        this.id = new UserRoleId(user.getId(), role.getId());
    }
    
    
    public UserRoleId getId() {
		return id;
	}

	public void setId(UserRoleId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id);
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    
    
    
}
