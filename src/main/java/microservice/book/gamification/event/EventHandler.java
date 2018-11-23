package microservice.book.gamification.event;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import microservice.book.gamification.service.GameService;

@Slf4j
@Component
public class EventHandler {

	private GameService gameService;

	EventHandler(GameService gameService) {
		super();
		this.gameService = gameService;
	}

	@RabbitListener(queues = "${multiplication.queue}")
	void handleMultiplicationSolved(MultiplicationSolvedEvent event) {
		log.info("Multiplication event received id: {}", event.getMultiplicationResultAttemptId());
		try {
			this.gameService.newAttemptForUser(event.getUserId(), event.getMultiplicationResultAttemptId(),
					event.isCorrect());
		} catch (Exception e) {
			log.error("Error when trying to process MultiplicationSolvedEvent", e);
			// Avoids the event to be re-queued and reprocessed.
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
}
