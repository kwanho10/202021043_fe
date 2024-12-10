package kr.ac.kku.cs.wp.kwanho10.user.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.kku.cs.wp.kwanho10.tools.message.MessageException;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserDTO;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserLoginDTO;
import kr.ac.kku.cs.wp.kwanho10.user.entity.Role;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;
import kr.ac.kku.cs.wp.kwanho10.user.entity.UserRole;
import kr.ac.kku.cs.wp.kwanho10.user.mapper.UserMapper;
import kr.ac.kku.cs.wp.kwanho10.user.repository.UserRepository;
import kr.ac.kku.cs.wp.kwanho10.user.repository.UserSpecifications;
import kr.ac.kku.cs.wp.kwanho10.utils.CryptoUtil;

@Service("userServiceJpaImpl")
public class UserServiceJpaImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceJpaImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;
	
	@Override
	@Transactional
	public User getUserById(String userId) {
		logger.info("Fetching user with ID: {}", userId);
		return userRepository.findById(userId)
				.orElseThrow(() -> new MessageException("User not found with ID: " + userId));
	}

	private String generateNewId() {
	    String lastId = userRepository.findLastUserId();

	    String prefix = "kku_";
	    int newNumber;

	    if (lastId == null) {
	        newNumber = 1;
	    } else {
	        int lastNumber = Integer.parseInt(lastId.split("_")[1]);
	        newNumber = lastNumber + 1;
	    }

	    return prefix + newNumber;
	}


	@Override
	@Transactional
	public void createUser(User user) {  
	    try {
	        user.setId(generateNewId());
	        List<UserRole> userRoles = user.getUserRoles();
	        Date now = new Date();
	        
	        if (userRoles != null) {
	            for (UserRole userRole : userRoles) { 
	                userRole.setUser(user);
	                userRole.setRole(new Role(userRole.getId().getRoleId(), userRole.getRoleName(), now, now));
	                
	                String roleName="관리자";
	                if(userRole.getId().getRoleId().equals("1001")) {
	                	roleName="판매자";
	                }else if(userRole.getId().getRoleId().equals("1002")) {
	                	roleName="구매자";
	                }	                
	                userRole.setRoleName(roleName);	                
	            }
	        }
	        

	        // 비밀번호 암호화
	        String salt = CryptoUtil.genSalt();  // Salt 생성
	        user.setSalt(salt);  // 사용자에 Salt 저장
	        user.setPassword(CryptoUtil.hash(user.getPassword(), salt));  // Salt와 함께 비밀번호 해시화
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    userRepository.saveAndFlush(user);
	}
	
	

	@Override
	public User getUser(User user) {
		logger.info("Fetching user with details: {}", user);
		Optional<User> foundUser = userRepository.findById(user.getId());
		return foundUser.orElse(null);
	}

	@Override
	@Transactional
	public User updateUser(User user) {
		logger.info("Updating user with ID: {}", user.getId());
		User existingUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new MessageException("User not found with ID: " + user.getId()));
		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		return userRepository.save(existingUser);
	}


	
	@Override
	@Transactional
	public void deleteUser(User user) {
		logger.info("Deleting user with ID: {}", user.getId());
		if (!userRepository.existsById(user.getId())) {
			throw new MessageException("Cannot delete. User not found with ID: " + user.getId());
		}
		userRepository.deleteById(user.getId());
	}



	@Override
	public List<User> getUsers(User user) {
		logger.info("Fetching users matching criteria: {}", user);
		Specification<User> spec=UserSpecifications.filterUsers(user);
		return userRepository.findAll(spec);
	}
	

	
	@Override
	@Transactional
	public List<User> getUsersByQueryString(String queryString){	
		return userRepository.findAll();
	}

	
	@Override
	public UserDTO findByEmail(String email) { 
		User user = userRepository.findByEmail(email);
	    if (user==null) return null;	    
		return userMapper.toDto(user);
	}

	
	

	@Override
	public UserDTO loginProcess(UserLoginDTO userLoginDTO) {
	    User getUser = userRepository.findByEmail(userLoginDTO.getEmail());

	    if (getUser == null) {
	        throw new IllegalArgumentException("해당 이메일의 사용자를 찾을 수 없습니다.");
	    }

	    // 저장된 Salt 값을 사용하여 입력된 비밀번호를 해시화
	    String hashedPassword = CryptoUtil.hash(userLoginDTO.getPassword(), getUser.getSalt());

	    // 저장된 비밀번호와 비교
	    if (!hashedPassword.equals(getUser.getPassword())) {
	        throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
	    }

	    return userMapper.toDto(getUser);
	}



	
	
	
}



