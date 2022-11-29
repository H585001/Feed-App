package group4.feedapp.API;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import group4.feedapp.API.dao.FAUserDAO;
import group4.feedapp.API.model.FAUser;

@SpringBootApplication
@EnableScheduling
public class FeedAppRESTApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FeedAppRESTApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner(FAUserDAO userDao, PasswordEncoder encoder) {
		return args -> {
			userDao.createUser("jj@gmail.com", encoder.encode("test123"), "J-Ron", true);
		};
	}

}
