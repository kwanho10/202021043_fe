package kr.ac.kku.cs.wp.kwanho10.user.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.ac.kku.cs.wp.kwanho10.user.dao.UserDAO;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;

@Service("userServiceImpl")
public class UserServiceImpl  {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
//
//	@Autowired
//	private UserDAO dao;
//
//	@Override
//	@Transactional
//	public User getUserById(String userId){
//		User user = dao.getUserById(userId);		
//		return user;
//	}
//	
//
//	@Override
//	@Transactional
//	public User getUser(User user) {
//		return dao.getUser(user);
//	}
//
//	@Override
//	@Transactional
//	public User updateUser(User user) {
//		return dao.updateUser(user);
//	}
//
//	@Override
//	@Transactional
//	public void deleteUser(User user) {
//		dao.deleteUser(user);
//	}
//
//	@Override
//	@Transactional
//	public void createUser(User user) {
//		dao.createUser(user);
//	}
//
//	@Override
//	@Transactional
//	public List<User> getUsers(User user) {
//		return dao.getUsers(user);
//	}
//
//
//	@Override
//	public List<User> getUsersByQueryString(String queryString) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
