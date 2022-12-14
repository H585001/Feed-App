package group4.feedapp.API.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import group4.feedapp.API.dao.FAUserDAO;
import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.service.FAUserService;

@ExtendWith(MockitoExtension.class)
public class FAUserServiceTest {
	
	@InjectMocks
	private FAUserService userService;
	
	@Mock
	private FAUserDAO userDAO;
	
	
	FAUser testUser = new FAUser("test@gmail.com", "testing", "Test User", false);
	
	@BeforeEach
    void setUp() {
		PasswordEncoder encoder = new BCryptPasswordEncoder(11);
        userService = new FAUserService(userDAO, encoder);
    }

	@Test
	public void getUserByEmailReturnMessageFromDAO() throws Exception {
		when(userDAO.readUserByEmail(testUser.getEmail())).thenReturn(testUser);
		
		assertEquals(userService.getUserByEmail(testUser.getEmail()).getEmail(), testUser.getEmail());
		
	}
	
}
