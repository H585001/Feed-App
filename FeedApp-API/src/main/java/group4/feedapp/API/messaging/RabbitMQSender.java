package group4.feedapp.API.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import group4.feedapp.API.config.MessagingConfig;
import group4.feedapp.API.model.Poll;

@Component
public class RabbitMQSender{
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public boolean publishPollEvent(Topics topic, Poll poll) {
		String message;
		boolean success = true;
		try {
			message = new ObjectMapper().writeValueAsString(poll); // Converting the Poll to JSON
			rabbitTemplate.convertAndSend(MessagingConfig.POLL_EVENT_EXCHANGE, topic.toString(), message);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

}
