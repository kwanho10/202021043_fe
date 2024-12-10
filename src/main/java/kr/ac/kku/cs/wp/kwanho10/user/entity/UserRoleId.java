package kr.ac.kku.cs.wp.kwanho10.user.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;



@Embeddable
public class UserRoleId implements Serializable {

	private static final long serialVersionUID = 1L;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "role_id", length = 50)
    private String roleId;
	
	public UserRoleId() {}
	
	public UserRoleId(String userId, String roleId) {
		this.userId=userId;
		this.roleId=roleId;
	}
		
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)return true;
		if (obj == null)return false;
		if (getClass() != obj.getClass())
			return false;
		UserRoleId other = (UserRoleId) obj;
		return Objects.equals(roleId, other.roleId) && Objects.equals(userId, other.userId);
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(roleId, userId);
	}
	
	
	
}
