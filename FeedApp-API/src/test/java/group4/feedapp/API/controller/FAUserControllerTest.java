package group4.feedapp.API.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import group4.feedapp.API.controller.FAUserController;
import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.service.FAUserService;
import group4.feedapp.API.service.PollService;

@WebMvcTest(FAUserController.class)
public class FAUserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FAUserService userService;
	@MockBean
	private PollService pollService;
	
	FAUser testUser = new FAUser("test@gmail.com", "testing", "Test User", false);

	@Test
	public void getUserByEmailReturnMessageFromService() throws Exception {
		when(userService.getUserByEmail(testUser.getEmail())).thenReturn(testUser);
		
		this.mockMvc.perform(get("/users/email/" + testUser.getEmail())).andDo(print()).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.email")
		.value(testUser.getEmail()));
		
	}
	
}
