package microservice.book.gamification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import microservice.book.gamification.domain.LeaderBoardRow;
import microservice.book.gamification.service.LeaderBoardService;

@RestController
public class LeaderBoardController {

	private final LeaderBoardService leaderBoardService;

	LeaderBoardController(LeaderBoardService leaderBoardService) {
		super();
		this.leaderBoardService = leaderBoardService;
	}

	@GetMapping(value = "/leaders")
	public List<LeaderBoardRow> leaders() {
		return this.leaderBoardService.getCurrentLeaderBoard();
	}

}
