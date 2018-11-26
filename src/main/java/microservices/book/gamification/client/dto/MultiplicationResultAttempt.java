package microservices.book.gamification.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import microservices.book.gamification.client.MultiplicationResultAttemptDeserializer;


/**
 * This class implements the {@link JsonDeserialize} annotation is to instruct
 * our {@link RestTemplate} ’s message converter to use a special deserializer
 * to read the JSON data. We need this since the JSON structure we’ll receive
 * doesn’t match with our Java class (since it’s matching the original
 * MultiplicationResultAttempt in the multiplication microservice), so the
 * default deserializer won’t work. We’ll cover that implementation in the
 * following subsection .
 * 
 * @author sabaja
 *
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@JsonDeserialize(using = MultiplicationResultAttemptDeserializer.class)
public class MultiplicationResultAttempt {

	private final String userAlias;

	private final int multiplicationFactorA;
	private final int multiplicationFactorB;
	private final int resultAttempt;

	private final boolean correct;

	// Empty constructor for JSON/JPA
	public MultiplicationResultAttempt() {
		super();
		this.userAlias = null;
		this.multiplicationFactorA = -1;
		this.multiplicationFactorB = -1;
		this.resultAttempt = -1;
		this.correct = false;
	}

}
