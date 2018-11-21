package microservice.book.gamification.service;

import java.util.List;
import microservice.book.gamification.domain.LeaderBoardRow;

/**
 * Provides methods to access the LeaderBoard with users and scores.
 * https://docs.spring.io/spring-data/jpa/docs/1.7.2.RELEASE/reference/html/#repositories.single-repository-behaviour
 */
public interface LeaderBoardService {

	/**
	 * Retrieves the current leader board with the top score users
	 * 
	 * @return the users with the highest score
	 */
	public List<LeaderBoardRow> getCurrentLeaderBoard();

	/**
	 * https://stackoverflow.com/questions/20679237/jpql-limit-query
	 * Retrieves the current leader board with N° score users
	 * 
	 * @return the users with the highest score
	 * 
	 */
	public List<LeaderBoardRow> getCurrentLeaderBoard(final int page,
            final int size) throws Exception;

}
