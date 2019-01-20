package microservices.book.gamification.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * Configures RabbitMQ to use events in our application.
 * 
 * Implementing RabbitListenerConfigurer then we found
 * RabbitListenerContainerFactory to use or for registering Rabbit endpoints in
 * a programmatic fashion as opposed to the declarative approach of using the
 * {@link RabbitListener} annotation.
 */
@Configuration
public class RabbitMQConfiguration implements RabbitListenerConfigurer {

	/**
	 * 1 - Bean for binding existing Multiplication Topic Exchange
	 * 
	 * @param exchangeName
	 * @return
	 */
	@Bean
	public TopicExchange multiplicationExchange(@Value("${multiplication.exchange}") final String exchangeName) {
		return new TopicExchange(exchangeName);
	}

	/**
	 * 2 - Bean for binding existing Queue Multiplication, we make it durable so if
	 * the broker goes down we can still process it. 
	 * 
	 * Dead letter update:
	 * https://fatihdirlikli.wordpress.com/2016/06/22/spring-rabbit-dead-letter-queue-configuration/
	 * https://docs.spring.io/autorepo/docs/spring-cloud-stream-binder-rabbit-docs/1.1.0.RELEASE/reference/html/rabbit-dlq-processing.html
	 * 
	 * @param exchangeName
	 * @return
	 */
	@Bean
	@Primary
	@Qualifier("gamificationMultiplicationQueue")
	public Queue gamificationMultiplicationQueue(@Value("${multiplication.queue}") final String queueName,
			@Value("${multiplication.exchange}") final String exchangeName) {
		Map<String, Object> args = new HashMap<>();
		args.put("x-dead-letter-exchange", exchangeName);
		args.put("x-dead-letter-routing-key", deadLetterQueue());
		return new Queue(queueName, true, false, false, args);
	}

	/** 
	 * Dead letter queue 
	 *
	 * Dead letter update:
	 * https://fatihdirlikli.wordpress.com/2016/06/22/spring-rabbit-dead-letter-queue-configuration/
	 * https://docs.spring.io/autorepo/docs/spring-cloud-stream-binder-rabbit-docs/1.1.0.RELEASE/reference/html/rabbit-dlq-processing.html
	 * 
	 */
	@Bean
	@Qualifier("deadLetterQueue")
	public Queue deadLetterQueue() {
		return new Queue("${deadletter.queue}", true);
	}

	/**
	 * 3 - Binding existing Queue Multiplication
	 * 
	 * @param exchangeName
	 * @return
	 */
	@Bean
	public Binding binding(final Queue queue, final TopicExchange exchange,
			@Value("${multiplication.solved.key}") final String routingKey) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	/**
	 * To convert json Seralization/Deserialization for consumer
	 * 
	 * @return
	 */
	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	/**
	 * We need to configure the RabbitListenerEndpointRegistrar in a way that uses a
	 * MappingJackson2MessageConverter
	 */
	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}
}
