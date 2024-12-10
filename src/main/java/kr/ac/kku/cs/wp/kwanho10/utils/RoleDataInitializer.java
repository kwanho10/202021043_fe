package kr.ac.kku.cs.wp.kwanho10.utils;

import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import kr.ac.kku.cs.wp.kwanho10.user.entity.Role;
import kr.ac.kku.cs.wp.kwanho10.user.repository.RoleRepository;

@Component
public class RoleDataInitializer  {

	  private final RoleRepository roleRepository;

	    public RoleDataInitializer(RoleRepository roleRepository) {
	        this.roleRepository = roleRepository;
	    }
	
	    @PostConstruct
	    public void init() {
	        if (roleRepository.count() == 0) {
	            Role admin = new Role("1000", "관리자", new Date(), new Date());
	            Role developer = new Role("1001", "판매자", new Date(), new Date());
	            Role systemAdmin = new Role("1002", "구매자", new Date(), new Date());
	            roleRepository.saveAll(Arrays.asList(admin, developer, systemAdmin));
	            System.out.println("초기 Role 데이터가 삽입되었습니다.");
	        }
	    }
}
