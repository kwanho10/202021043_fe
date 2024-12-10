package kr.ac.kku.cs.wp.kwanho10.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {

    private String id;

    @Pattern(regexp = "^[\\p{L}\\p{M}\\p{N} ]*$", message = "특수문자를 사용할 수 없습니다.")
    private String name;

    @NotBlank
    @Email
    private String email;

    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()_\\-+=\\[\\]{}\\\\;:'\",.<>/?]).{8,}$",
        message = "비밀번호는 특수문자, 영문자, 숫자를 포함하여 8자리 이상이어야 합니다."
    )
    private String password;

    @NotBlank
    @Pattern(regexp = "남성|여성", message = "상태는 '남성' 또는 '여성'이어야 합니다.")
    private String status;

    private String address;

    private String salt;
    
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<UserRoleDTO> userRoles;


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

    public void setPhoto(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<UserRoleDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleDTO> userRoles) {
        this.userRoles = userRoles;
    }
    
    public void setSalt(String salt) {
		this.salt = salt;
	}
    
    public String getSalt() {
		return salt;
	}
    
}
