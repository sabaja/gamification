package microservice.book.gamification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import microservice.book.gamification.domain.LeaderBoardRow;
import microservice.book.gamification.repository.ScoreCardRepository;

public class LeaderBoardServiceImpl implements LeaderBoardService {

	@Autowired
	private ScoreCardRepository scoreCardRepository;

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

	
	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard(int num) {
		
		return getCurrentLeaderBoard(num);
	}

	
}
