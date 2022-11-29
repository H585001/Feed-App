package group4.feedapp.API.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import group4.feedapp.API.messaging.Topics;

@Configuration
public class MessagingConfig {
	
	public final static String POLL_EVENT_EXCHANGE = "poll-event-exchange";

	// Creating queues for poll events
	@Bean
	Queue openedPollsQueue() {
		return new Queue(Topics.POLL_OPENED.toString(), true);
	}

	@Bean
	Queue closedPollsQueue() {
		return new Queue(Topics.POLL_CLOSED.toString(), true);
	}
	
	// Creating messaging exchange
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(POLL_EVENT_EXCHANGE);
	}
	
	// Binding queues to exchange
	@Bean
	Binding openedPollsBinding(Queue openedPollsQueue, DirectExchange exchange) {
		return BindingBuilder.bind(openedPollsQueue).to(exchange).with(Topics.POLL_OPENED.toString());
	}

	@Bean
	Binding closedPollsQueueBinding(Queue closedPollsQueue, DirectExchange exchange) {
		return BindingBuilder.bind(closedPollsQueue).to(exchange).with(Topics.POLL_CLOSED.toString());
	}

}
