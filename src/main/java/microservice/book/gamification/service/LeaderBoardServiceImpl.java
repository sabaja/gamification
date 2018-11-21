package microservice.book.gamification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import microservice.book.gamification.domain.LeaderBoardRow;
import microservice.book.gamification.repository.ScoreCardRepository;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

	@Autowired
	private ScoreCardRepository scoreCardRepository;

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard(final int page, final int size) throws Exception {
		return this.scoreCardRepository.retrieveLeaderBoardPaged(PageRequest.of(page, size));
	}

	
}
