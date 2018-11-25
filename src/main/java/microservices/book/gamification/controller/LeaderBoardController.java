package microservices.book.gamification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.service.LeaderBoardService;

@RestController
@RequestMapping(value = "/leaders")
public class LeaderBoardController {

	private final LeaderBoardService leaderBoardService;

	LeaderBoardController(LeaderBoardService leaderBoardService) {
		super();
		this.leaderBoardService = leaderBoardService;
	}

	@GetMapping
	public List<LeaderBoardRow> leaders() {
		return this.leaderBoardService.getCurrentLeaderBoard();
	}

	@GetMapping(value = "/pages")
	public List<LeaderBoardRow> leaders(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "1") Integer size) throws Exception {
		return this.leaderBoardService.getCurrentLeaderBoard(page, size);
	}

}
