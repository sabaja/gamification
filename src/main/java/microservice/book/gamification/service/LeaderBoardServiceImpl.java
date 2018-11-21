package microservice.book.gamification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import microservice.book.gamification.domain.LeaderBoardRow;
import microservice.book.gamification.repository.ScoreCardRepository;

@Service
@Slf4j
public class LeaderBoardServiceImpl implements LeaderBoardService {

	@Autowired
	private ScoreCardRepository scoreCardRepository;

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		log.info("getCurrentLeaderBoard");
		return scoreCardRepository.findFirst10();
	}

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard(final int page, final int size) throws Exception {
		log.info("getCurrentLeaderBoard called with page: " + page + " size: " + size);
		return this.scoreCardRepository.retrieveLeaderBoardPaged(PageRequest.of(page, size));
	}

	
}
