package kr.ac.kku.cs.wp.kwanho10.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserDTO;
import kr.ac.kku.cs.wp.kwanho10.user.dto.UserLoginDTO;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;
import kr.ac.kku.cs.wp.kwanho10.user.mapper.UserMapper;
import kr.ac.kku.cs.wp.kwanho10.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	@Qualifier("userServiceJpaImpl")
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@RequestMapping(value = "/view")
	public ModelAndView userView(@RequestParam(name = "userId", required = true) String userId) {
		User user = userService.getUserById(userId);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/userView");
		mav.addObject("user", user);

		return mav;
	}

	@RequestMapping("/userlist")
	public ModelAndView userlist(@RequestParam(name = "queryString", required = false, defaultValue = "") String queryString) {
		logger.debug("queryString: {}", queryString);

		List<User> users = userService.getUsersByQueryString(queryString);

		ModelAndView mav = new ModelAndView();
		mav.addObject("users", users);
		mav.setViewName("/user/userList");
		return mav;
	}

	@GetMapping("/create")
	public String createUserPage() {
		System.out.println("회원 가입 페이지 ");
		return  "/user/userForm";
	}
		
	
	@PostMapping(value = "/create")
	@ResponseBody
	public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {	    
	    try {
			// 유효성 검사 오류 처리
		    if (bindingResult.hasErrors()) {
		        bindingResult.getAllErrors().forEach(error -> {
		            System.err.println("유효성 검사 오류: " + error.getDefaultMessage());
		        });
		        
		        return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
		    }
	
		    // 이메일 중복 확인
		    UserDTO existingUser = userService.findByEmail(userDTO.getEmail());
		    if (existingUser != null) {	    	
		        bindingResult.rejectValue("email", "error.email", "이미 등록된 이메일입니다.");
		        return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
		    }
	    
	        // 사용자 생성 로직
	        userService.createUser(userMapper.toEntity(userDTO));
	        Map<String, String> response = new HashMap<>();
	        response.put("result", "success");
	        return ResponseEntity.ok(response); 
	    } catch (Exception ex) {
	        // 예외 로깅
	        System.err.println("사용자 생성 중 오류 발생: " + ex.getMessage());
	        ex.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred: " + ex.getMessage());
	    }
	}
		
	

	@GetMapping("/loginPage")
	public String loginPage() {
		return  "/user/userLogin";
	}
		
	
	
	

	@PostMapping(value = "/login")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO userLoginDTO, BindingResult bindingResult, HttpSession session) {
	    if (bindingResult.hasErrors()) {
	        return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
	    }

	    UserDTO userDTO = userService.loginProcess(userLoginDTO);
	    if (userDTO != null) {
	        session.setAttribute("loginUser", userDTO);
	        
	        // 사용자 역할을 확인하고 세션에 설정
	        List<String> roles = getRolesFromUser(userDTO);
	        session.setAttribute("roles", roles);
	        if(roles.contains("관리자") || roles.contains("판매자") ) {
	       	  session.setAttribute("adminRole", true);
	        }
	       	        
	        return ResponseEntity.ok(Map.of("result", "success"));
	    } else {
	        throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
	    }
	}
	
	
	
	// 사용자 역할을 확인하여 리스트로 반환하는 메서드
	private List<String> getRolesFromUser(UserDTO userDTO) {
	    List<String> roles = new ArrayList<>();

	    if (userDTO.getUserRoles().stream().anyMatch(role -> role.getRoleName().contains("관리자"))) {
	        roles.add("관리자");
	    }
	    if (userDTO.getUserRoles().stream().anyMatch(role -> role.getRoleName().contains("판매자"))) {
	        roles.add("판매자");
	    }
	    if (userDTO.getUserRoles().stream().anyMatch(role -> role.getRoleName().contains("구매자"))) {
	        roles.add("구매자");
	    }

	    return roles.isEmpty() ? List.of("사용자") : roles;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return  "redirect:/";
	}

	

	
}
