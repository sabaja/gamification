package microservice.book.gamification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import microservice.book.gamification.domain.LeaderBoardRow;
import microservice.book.gamification.service.LeaderBoardService;

@RestController
public class LeaderBoardController {

	private final LeaderBoardService leaderBoardService;

	@Autowired
	LeaderBoardController(LeaderBoardService leaderBoardService) {
		super();
		this.leaderBoardService = leaderBoardService;
	}

	@GetMapping(value = "/leaders")
	public List<LeaderBoardRow> leaders() {
		return this.leaderBoardService.getCurrentLeaderBoard();
	}

	/**
	 * https://stackoverflow.com/questions/20679237/jpql-limit-query
	 * @param num
	 * @return
	 */
	@GetMapping(value = "/leaders", params = "num")
	public List<LeaderBoardRow> leaders(int num) {
		return null;
	}
}
