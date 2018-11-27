package microservices.book.gamification.client;

import microservices.book.gamification.client.dto.MultiplicationResultAttempt;

/**
 * 
 * This interface allows us to connect to the Multiplication microservice.
 * Note that it's agnostic to the way of communication.
 *
 * @author sabaja
 *
 */
public interface MultiplicationResultAtemptClient {

	public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(final Long multiplicationResultAttemptId);
}
