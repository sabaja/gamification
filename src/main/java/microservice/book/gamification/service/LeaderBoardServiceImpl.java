package microservice.book.gamification.service;

import java.util.List;

import microservice.book.gamification.domain.LeaderBoardRow;
import microservice.book.gamification.repository.ScoreCardRepository;

public class LeaderBoardServiceImpl implements LeaderBoardService {

	private ScoreCardRepository scoreCardRepository;

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

}
