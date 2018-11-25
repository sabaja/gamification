package microservices.book.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import microservices.book.gamification.domain.entity.BadgeCard;

/**
 * Handles data operations with BadgeCards
 */
public interface BadgeCardRepository extends JpaRepository<BadgeCard, Long> {

	/**
	 * Retrieves all BadgeCards for a given user.
	 * 
	 * @param userId
	 *            the id of the user to look for BadgeCards
	 * @return the list of BadgeCards, sorted by most recent.
	 */
	public List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(Long userId);

}
