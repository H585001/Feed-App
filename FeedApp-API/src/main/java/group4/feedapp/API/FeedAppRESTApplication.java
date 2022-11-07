package group4.feedapp.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FeedAppRESTApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FeedAppRESTApplication.class, args);
	}

}
