package microservices.book.gamification.event;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// Application doesn't run if you uncomment @component
// Event handler for dead letter cannot recognize @RabbitListener(queues = "${deadletter.queue}")

//@Component
/**
 * https://docs.spring.io/autorepo/docs/spring-cloud-stream-binder-rabbit-docs/1.1.0.RELEASE/reference/html/rabbit-dlq-processing.html
 * 
 * @author sabaja
 *
 */
public class DeadLetterEventHandler {

	public static final String X_RETRIES_HEADER = "x-retries";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RabbitListener(queues = "${deadletter.queue}")
	public void rePublish(Message failedMessage) {
		log.info("Dead letter - failedMessage: {}", failedMessage);
		Map<String, Object> headers = failedMessage.getMessageProperties().getHeaders();
		Integer retriesHeader = (Integer) headers.get(X_RETRIES_HEADER);
		if (retriesHeader == null) {
			retriesHeader = Integer.valueOf(0);
		}
		if (retriesHeader < 3) {
			headers.put(X_RETRIES_HEADER, retriesHeader + 1);
			headers.put("x-delay", 5000 * retriesHeader);
			this.rabbitTemplate.send("${dlq.exchenge}", "${multiplication.queue}", failedMessage);
		} else {
			log.info("Dead letter - failedMessage: {} in parking a lot", failedMessage);
			this.rabbitTemplate.send("${deadletter.parkiglot}", failedMessage);
		}
	}
}
