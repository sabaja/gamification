package microservice.book.gamification.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import microservice.book.gamification.domain.LeaderBoardRow;
import microservice.book.gamification.domain.ScoreCard;

/**
 * Handles CRUD operations with ScoreCards
 */
public interface ScoreCardRepository extends JpaRepository<ScoreCard, Long> {

	/**
	 * 
	 * https://stackoverflow.com/questions/44647630/validation-failed-for-query-for-method-jpql?rq=1
	 * https://www.baeldung.com/jpa-sql-resultset-mapping
	 * 
	 * Gets the total score for a * given user, being the sum of the scores of all
	 * his ScoreCards.
	 * 
	 * @param userId the id of the user for which the total score should be
	 *               retrieved
	 * @return the total score for the given user
	 *
	 *         https://stackoverflow.com/questions/34744033/use-nativequery-true-with-query-in-spring-boot-does-not-work
	 */
	@Query("SELECT SUM(s.score) FROM microservice.book.gamification.domain.ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
	int getTotalScoreForUser(@Param("userId") final Long userId);

	
	/**
	 * 
	 * Retrieves a certain amount of {@link LeaderBoardRow} that represent the
	 * users' ranking and their total score.
	 * 
	 * @param page
	 *            {@link Pageable}
	 * @return the leader board, sorted by highest score first.
	 */
	@Query(value = "SELECT NEW microservice.book.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score)) "
			+ "FROM microservice.book.gamification.domain.ScoreCard s "
			+ "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
	public List<LeaderBoardRow> retrieveLeaderBoardPaged(final Pageable page);

	
	/**
	 * soluzione Errore Invocation of init method failed; nested exception is
	 * java.lang.IllegalArgumentException: Validation failed for query for method
	 * public abstract java.util.List: Retrieves a list of {@link LeaderBoardRow}s
	 * representing the Leader Board of users and their total score.
	 * 
	 * Retrieves a certain amount of {@link LeaderBoardRow} that represent the
	 * users' ranking and their total score.
	 * 
	 * @param page
	 *            {@link Pageable}
	 * @return the leader board, sorted by highest score first.
	 */
	@Query(value = "SELECT NEW microservice.book.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score)) "
			+ "FROM microservice.book.gamification.domain.ScoreCard s "
			+ "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
	List<LeaderBoardRow> findFirst10();

	/**
	 * Retrieves all the ScoreCards for a given user, identified by his user id.
	 * 
	 * @param userId the id of the user
	 * @return a list containing all the ScoreCards for the given user, sorted by
	 *         most recent.
	 */
	List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}
