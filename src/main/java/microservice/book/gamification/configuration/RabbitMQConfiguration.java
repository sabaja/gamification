package microservice.book.gamification.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures RabbitMQ to use events in our application.
 */
@Configuration
public class RabbitMQConfiguration implements RabbitListenerConfigurer {

	/**
	 * 1 - Bean for existing Multiplication Topic Exchange
	 * 
	 * @param exchangeName
	 * @return
	 */
	@Bean
	public TopicExchange multiplicationExchange(@Value("${multiplication.exchange}") final String exchangeName) {
		return new TopicExchange(exchangeName);
	}

	/**
	 * 2 - Bean for existing Queue Multiplication, we make it durable so if the
	 * broker goes down we can still process it.
	 * 
	 * @param exchangeName
	 * @return
	 */
	@Bean
	public Queue multiplicationQueue(@Value("${multiplication.queue}") final String queueName) {
		return new Queue(queueName, true);
	}

	/**
	 * 3 - Binding existing Queue Multiplication
	 * 
	 * @param exchangeName
	 * @return
	 */
	@Bean
	public Binding binding(final Queue queue, final TopicExchange exchange,
			@Value("${multiplication.anything.routing-key}") final String routingKey) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		// TODO Auto-generated method stub

	}

}
