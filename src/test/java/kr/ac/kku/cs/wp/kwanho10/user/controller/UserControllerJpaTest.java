package kr.ac.kku.cs.wp.kwanho10.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import kr.ac.kku.cs.wp.kwanho10.user.entity.User;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/app-context.xml")
public class UserControllerJpaTest {

	private static final Logger logger = LogManager.getLogger(UserControllerJpaTest.class);

	private MockMvc mockMvc;

	private boolean isDebugEnabled = true;

	@Autowired
	private WebApplicationContext wac;

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		if (isDebugEnabled) {
			logger.debug("MockMvc setup complete");
		}
	}

	@Test
	public void testGetUser() throws Exception {
		// 정상조회 테스트
		MvcResult mvcResult = mockMvc.perform(get("/jpa/user/view").param("userId", "kku_1000"))
				.andExpect(status().isOk()).andDo(isDebugEnabled ? MockMvcResultHandlers.print() : result -> {
				}).andExpect(model().attributeExists("user")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();

		User user = (User) mav.getModel().get("user");
		assertEquals(user.getId(), "kku_1000");
		logger.debug("user id: { ", user.getId());

		// 없는 사용자 테스트
		mvcResult = mockMvc.perform(get("/jpa/user/view").param("userId", "kku_100011"))
				.andDo(isDebugEnabled ? MockMvcResultHandlers.print() : result -> {
				}).andExpect(status().isOk()).andReturn();

		assertEquals(mvcResult.getResponse().getContentAsString(), "User not found: kku_100011");
		logger.debug(mvcResult.getResponse().getContentAsString());
	}

	// @Test
	public void testUserlist() throws Exception {
		// queryString filtering 테스트
		MvcResult mvcResult = mockMvc.perform(get("/jpa/user/userlist").param("queryString", "안중근"))
				.andDo(isDebugEnabled ? MockMvcResultHandlers.print() : result -> {
				}).andExpect(status().isOk()).andExpect(model().attributeExists("users")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		List<User> users = (List<User>) mav.getModel().get("users");

		logger.debug(" users size: {}", users.size());
		assertTrue(users.get(0).getName().contains("안중근"));
	}

}
