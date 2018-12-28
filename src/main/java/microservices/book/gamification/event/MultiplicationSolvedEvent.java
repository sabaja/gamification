package microservices.book.gamification.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Event received when a multiplication has been solved in the system.
 * Provides some context information about the multiplication.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
class MultiplicationSolvedEvent implements Serializable {

    /**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = -7213852059842340086L;
	
	@JsonProperty("multiplicationResultAttemptId")
	private final Long multiplicationResultAttemptId;
	@JsonProperty("userId")
    private final Long userId;
	@JsonProperty("correct")
    private final boolean correct;
	
	public MultiplicationSolvedEvent() {
		super();
		this.multiplicationResultAttemptId = 0L;
		this.userId = 0L;
		this.correct = false;
	}
}
