package kr.ac.kku.cs.wp.kwanho10.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import kr.ac.kku.cs.wp.kwanho10.user.entity.User;
import kr.ac.kku.cs.wp.kwanho10.user.entity.UserRole;
import kr.ac.kku.cs.wp.kwanho10.utils.CryptoUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/app-context.xml")
public class UserControllerTest {

    private static final Logger logger = LogManager.getLogger(UserControllerTest.class);
    
    private MockMvc mockMvc;
    
    private boolean isDebugEnabled = true;


    @Autowired
    private WebApplicationContext wac;

    
	@Test
	public void exampleTest() {
		assertTrue(true); // 간단한 검증
		logger.info("Test ran successfully.");
	}
    
    
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    private void logResultIfDebug(MvcResult result) throws Exception {
        if (logger.isDebugEnabled()) {
            MockMvcResultHandlers.print().handle(result);
        }
    }

    @Test
    public void testUserlist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/user/userlist"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andReturn();

        logResultIfDebug(mvcResult);

        ModelAndView mav = mvcResult.getModelAndView();
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) mav.getModel().get("users");

        assertNotNull(users);
        assertFalse(users.isEmpty(), "User list should not be empty.");
        assertEquals("kku_1000", users.get(0).getId());
    }

    @Test
    public void testCreateUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/user/create")
                .contentType("application/x-www-form-urlencoded")
                .param("name", "Test User")
                .param("email", "test@example.com")
                .param("password", "zaqwsx123!")
                .param("status", "퇴직")
                .param("userRoles[0].userId", "testUser")
                .param("userRoles[0].roleId", "1001")
                .param("userRoles[0].roleName", "개발자"))
                .andExpect(status().isOk())
                .andReturn();

        logResultIfDebug(mvcResult);

        ModelAndView mav = mvcResult.getModelAndView();
        assertEquals("/user/userSuccess", mav.getViewName());

        User createdUser = (User) mav.getModel().get("user");
        assertEquals("Test User", createdUser.getName());
        assertEquals("test@example.com", createdUser.getEmail());
    }

    @Test
    public void testUpdateUser() throws Exception {
        // 기존 사용자 조회
        MvcResult mvcResult = mockMvc.perform(get("/user/view").param("userId", "kku_1000"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andReturn();

        logResultIfDebug(mvcResult);

        ModelAndView mav = mvcResult.getModelAndView();
        User user = (User) mav.getModel().get("user");

        // 사용자 정보 업데이트
        mvcResult = mockMvc.perform(post("/user/update")
                .contentType("application/x-www-form-urlencoded")
                .param("id", user.getId())
                .param("name", user.getName() + " test")
                .param("email", "changed@example.com")
                .param("password", "newPassword123!")
                .param("userRoles[0].userId", user.getId())
                .param("userRoles[0].roleId", "1001")
                .param("userRoles[0].roleName", "개발자"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andReturn();

        logResultIfDebug(mvcResult);

        mav = mvcResult.getModelAndView();
        User updatedUser = (User) mav.getModel().get("user");

        assertEquals(user.getName() + " test", updatedUser.getName());
        assertEquals("changed@example.com", updatedUser.getEmail());

        // 비밀번호 및 역할 검증

        Set<String> expectedRoles = Set.of("1001");
        Set<String> actualRoles = updatedUser.getUserRoles().stream()
                .map(role -> role.getId().getRoleId())
                .collect(Collectors.toSet());
        assertEquals(expectedRoles, actualRoles);
    }
}
