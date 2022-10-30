package group4.feedapp.analytics.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {
	
	
	@RabbitListener(queues = "POLL_CLOSED")
	public void pollClosedListener(String msg) {
		
	}
	
	@RabbitListener(queues = "POLL_OPENED")
	public void pollOpenedListener(String msg) {
		System.out.println("Message read from myQueue : " + msg);
	}
}
