package group4.feedapp.analytics.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import group4.feedapp.analytics.dao.PollRepository;
import group4.feedapp.analytics.model.Poll;
import reactor.core.publisher.Mono;

@Component
public class RabbitMQReceiver {
	
	@Autowired
	private PollRepository pollRepository;
	
	final static String DWEET_POST_URL = "https://dweet.io/dweet/for/feedapp-group4-3a991b5e-f73f-4ffc-9fd2-69d981343e27";
	
	@RabbitListener(queues = "POLL_CLOSED")
	public void pollClosedListener(String msg) {
		// Publish to dweet.io
		System.out.println("POLL_CLOSED: Message read from myQueue : " + msg);
			
		WebClient client = WebClient.builder()
				  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
				  .build();
		
		// POST msg to dweet.io
		ResponseEntity<String> resp = client.post()
		.uri(DWEET_POST_URL)
		.body(Mono.just(msg), String.class)
		.retrieve()
		.toEntity(String.class)
		.block();
		System.out.println(resp.getBody());
		
		// Store result in MongoDB
		Poll poll = null;
		try {
			poll = new ObjectMapper().readValue(msg, Poll.class);
			pollRepository.save(poll);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	@RabbitListener(queues = "POLL_OPENED")
	public void pollOpenedListener(String msg) {
		System.out.println("POLL_OPENED: Message read from myQueue : " + msg);
		
		WebClient client = WebClient.builder()
				  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
				  .build();
		
		// POST msg to dweet.io
		ResponseEntity<String> resp = client.post()
		.uri(DWEET_POST_URL)
		.body(Mono.just(msg), String.class)
		.retrieve()
		.toEntity(String.class)
		.block();
		System.out.println(resp.getBody());		
	}
}
