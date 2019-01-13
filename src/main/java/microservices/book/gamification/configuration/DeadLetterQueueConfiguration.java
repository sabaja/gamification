package microservices.book.gamification.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


//@Configuration
//@Import(RabbitMQConfiguration.class)
public class DeadLetterQueueConfiguration {
	@Value("${multiplication.exchange}")
	private String topicExchange;

	@Value("${dlq.exchenge}")
	private String dlqExchange;

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(this.topicExchange);
	}

	@Bean
	public Queue deadLetterQueue() {
		return new Queue(this.dlqExchange, true);
	}

	@Bean
	public Queue deadQueue() {
		Map<String, Object> args = new HashMap<>();
		args.put("x-dead-letter-exchange", "${multiplication.exchange}");
		args.put("x-dead-letter-routing-key", "${dlq.exchenge}");
		return new Queue("${multiplication.queue}", true, false, false, args);
	}

	@Bean
	Binding bindingDeadLetter() {
		return BindingBuilder.bind(deadQueue()).to(exchange()).with("${dlq.exchenge}");
	}
}
