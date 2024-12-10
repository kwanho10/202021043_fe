package kr.ac.kku.cs.wp.kwanho10.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.ac.kku.cs.wp.kwanho10.user.entity.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>{

	
	@Query("SELECT max(u.id) FROM User u")
	String findLastUserId();

	@Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
	User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	User findByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u WHERE u.id = :id")
	User findByUserId(@Param("id") String id);
	
}
