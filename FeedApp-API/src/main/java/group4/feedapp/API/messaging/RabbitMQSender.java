package group4.feedapp.API.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import group4.feedapp.API.model.Poll;

@Component
public class RabbitMQSender{
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public boolean publishPollEvent(Topics topic, Poll poll) {
		String message;
		boolean success = true;
		try {
			message = new ObjectMapper().writeValueAsString(poll); // Converting the Poll to json
			rabbitTemplate.convertAndSend(topic.toString(), message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

}
