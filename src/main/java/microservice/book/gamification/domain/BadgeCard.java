package microservice.book.gamification.domain;

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
 * This class links a Badge to a User. Contains also a timestamp with the moment
 * in which the user got it.
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "BADGE_CARD")
public final class BadgeCard implements Serializable {

	private static final long serialVersionUID = -2098178213996160105L;

	@Id
	@GeneratedValue
	@Column(name = "BADGE_ID")
	private final Long badgeId;

	@Column(name = "USER_ID")
	private final Long userId;

	@Column(name = "BADGE_TIMESTAMP")
	private final long badgeTimestamp;

	@Column(name = "BADGE")
	private final Badge badge;

	// Empty constructor for JSON / JPA public
	public BadgeCard() {
		this(null, null, 0, null);
	}

	public BadgeCard(final Long userId, final Badge badge) {
		this(null, userId, Instant.now().toEpochMilli(), badge);
	}
}
