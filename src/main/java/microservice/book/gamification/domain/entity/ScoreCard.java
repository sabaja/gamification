package microservice.book.gamification.domain.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * This class represents the Score linked to an attempt in the game, with an
 * associated user and the timestamp in which the score is registered.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "SCORE_CARD")
public final class ScoreCard implements Serializable {

	private static final long serialVersionUID = -8110821707173595409L;

	// The default score assigned to this card, if not specified.
	public static final int DEFAULT_SCORE = 10;

	@Id
	@GeneratedValue
	@Column(name = "CARD_ID")
	private final Long cardId;

	@Column(name = "USER_ID")
	private final Long userId;

	@Column(name = "ATTEMPT_ID")
	private final Long attemptId;

	@Column(name = "SCORE_TS")
	private final long scoreTimestamp;

	@Column(name = "SCORE")
	private final int score;

	// Empty constructor for JSON / JPA
	public ScoreCard() {
		this(null, null, null, 0, 0);
	}

	public ScoreCard(final Long userId, final Long attemptId) {
		this(null, userId, attemptId, Instant.now().toEpochMilli(), DEFAULT_SCORE);
	}

}