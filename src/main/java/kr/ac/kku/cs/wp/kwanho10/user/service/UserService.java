package kr.ac.kku.cs.wp.kwanho10.user.service;

import java.util.List;

import jakarta.validation.Valid;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserDTO;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserLoginDTO;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;


public interface UserService {
	
	public User getUserById(String userId);
	
	public User getUser(User user);
	
	public User updateUser(User user);
	
	public void deleteUser(User user);
	
	public void createUser(User user);
	
	public List<User> getUsers(User user);
	
	public List<User> getUsersByQueryString(String queryString);

	public UserDTO findByEmail(String email);
    
	public UserDTO loginProcess(@Valid UserLoginDTO userLoingDTO);
}


