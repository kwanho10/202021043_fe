package kr.ac.kku.cs.wp.kwanho10.user.dto;

public class UserRoleDTO {

	private String userId;
	private String roleId;
	private String roleName;
	
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
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String toString() {
		return "UserRoleDTO [userId=" + userId + ", roleId=" + roleId + ", roleName=" + roleName + "]";
	}
	
	
	
}
