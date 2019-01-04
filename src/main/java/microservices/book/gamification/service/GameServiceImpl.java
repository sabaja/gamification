package microservices.book.gamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.client.MultiplicationResultAttemptClient;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;

/**
 * Representation of business logic
 * 
 * @author sabaja
 *
 */
@Service
@Slf4j
class GameServiceImpl implements GameService {

	public static final int LUCKY_NUMBER = 42;
	public static final int BRONZE_LEVEL = 100;
	public static final int SILVER_LEVEL = 500;
	public static final int GOLD_LEVEL = 999;

	private ScoreCardRepository scoreCardRepository;
	private BadgeCardRepository badgeCardRepository;
	private MultiplicationResultAttemptClient attemptClient;

	GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository,
			MultiplicationResultAttemptClient attemptClient) {
		this.scoreCardRepository = scoreCardRepository;
		this.badgeCardRepository = badgeCardRepository;
		this.attemptClient = attemptClient;
	}

	/**
	 * 
	 */
	@Override
	public GameStats newAttemptForUser(final Long userId, final Long attemptId, final boolean correct) {
		// For the first version we'll give points only if it's correct
		if (correct) {
			// we create a ScoreCard object (with a default score of 10)
			ScoreCard scoreCard = new ScoreCard(userId, attemptId);
			// and persist it in the database.
			scoreCardRepository.save(scoreCard);
			log.info("User with id {} scored {} points for attempt id {}", userId, scoreCard.getScore(), attemptId);
			List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
			return new GameStats(userId, scoreCard.getScore(),
					badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
		}
		return GameStats.emptyStats(userId);
	}

	/**
	 * Checks the total score and the different score cards obtained to give new
	 * badges in case their conditions are met.
	 */
	private List<BadgeCard> processForBadges(final Long userId, final Long attemptId) {
		int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
		log.info("New score for user {} is {}", userId, totalScore);

		List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
		List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
		List<BadgeCard> badgeCards = new ArrayList<>();
		
		// Badges depending on score
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLICATOR, totalScore, BRONZE_LEVEL, userId)
				.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore, SILVER_LEVEL, userId)
				.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD_MULTIPLICATOR, totalScore, GOLD_LEVEL, userId)
				.ifPresent(badgeCards::add);

		// First won badge
		if (scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)) {
			BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
			badgeCards.add(firstWonBadge);
		}

		// Lucky number badge
		MultiplicationResultAttempt attempt = attemptClient.retrieveMultiplicationResultAttemptbyId(attemptId);
		if (!containsBadge(badgeCardList, Badge.LUCKY_NUMBER) && (LUCKY_NUMBER == attempt.getMultiplicationFactorA()
				|| LUCKY_NUMBER == attempt.getMultiplicationFactorB())) {
			BadgeCard luckyNumberBadge = giveBadgeToUser(Badge.LUCKY_NUMBER, userId);
			badgeCards.add(luckyNumberBadge);
		}

		return badgeCards;
	}

	@Override
	public GameStats retrieveStatsForUser(final Long userId) {
		int score = scoreCardRepository.getTotalScoreForUser(userId);
		List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
		return new GameStats(userId, score, badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
	}

	/**
	 * Convenience method to check the current score against the different
	 * thresholds to gain badges. It also assigns badge to user if the conditions
	 * are met.
	 */
	private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCards, final Badge badge,
			final int score, final int scoreThreshold, final Long userId) {
		if (score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
			return Optional.of(giveBadgeToUser(badge, userId));
		}
		return Optional.empty();
	}

	/**
	 * Checks if the passed list of badges includes the one being checked
	 */
	private boolean containsBadge(final List<BadgeCard> badgeCards, final Badge badge) {
		return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
	}

	/**
	 * Assigns a new badge to the given user
	 */
	private BadgeCard giveBadgeToUser(final Badge badge, final Long userId) {
		BadgeCard badgeCard = new BadgeCard(userId, badge);
		badgeCardRepository.save(badgeCard);
		log.info("User with id {} won a new badge: {}", userId, badge);
		return badgeCard;
	}

}
