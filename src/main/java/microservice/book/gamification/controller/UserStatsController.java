package microservice.book.gamification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservice.book.gamification.domain.GameStats;
import microservice.book.gamification.service.GameService;

@RestController
@RequestMapping("/stats")
public class UserStatsController {

	
	private final GameService gameService;

	@Autowired
	public UserStatsController(GameService gameService) {
		super();
		this.gameService = gameService;
	}
	
	public GameStats getStatsForUser(@RequestParam("userId") final Long userId) {
		return gameService.retrieveStasForUser(userId);
	}
}
