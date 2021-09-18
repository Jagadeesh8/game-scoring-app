package com.game.tennis.score;

import com.game.tennis.score.dto.GameScoreInput;
import com.game.tennis.score.dto.PlayerPoint;
import com.game.tennis.score.dto.ScoreCard;
import com.game.tennis.score.service.GameScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {
		    TennisScoreApplication.class})
@ComponentScan("com.game.tennis.score")
class TennisScoreApplicationTests {

	@Autowired
	GameScoreService gameScoreService;

	@Test
	void makePlayer1Win() {
		List<PlayerPoint> playerPoints = new ArrayList<>();
		playerPoints.add(new PlayerPoint("Player1", false));
		playerPoints.add(new PlayerPoint("Player2", false));
		GameScoreInput gameScoreInput = new GameScoreInput(playerPoints, "FirstGame");
		List<ScoreCard> scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertNotNull(scoreCards);

		// secure a point for player 1 and assert
		playerPoints.get(0).setPointSecured(true);
		playerPoints.get(1).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("0", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerPoints.get(0).setPointSecured(false);
		playerPoints.get(1).setPointSecured(true);
		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and assert
		playerPoints.get(0).setPointSecured(true);
		playerPoints.get(1).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());


		// secure a point for player 1 and assert
		playerPoints.get(0).setPointSecured(true);
		playerPoints.get(1).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("40", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerPoints.get(1).setPointSecured(true);
		playerPoints.get(0).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("40", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and Make WIN
		playerPoints.get(0).setPointSecured(true);
		playerPoints.get(1).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("Won Game", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

	}

	@Test
	void makePlayer2Win() {
		List<PlayerPoint> playerPoints = new ArrayList<>();
		playerPoints.add(new PlayerPoint("Player1", false));
		playerPoints.add(new PlayerPoint("Player2", false));
		GameScoreInput gameScoreInput = new GameScoreInput(playerPoints, "SecondGame");
		List<ScoreCard> scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertNotNull(scoreCards);

		// secure a point for player 1 and assert
		playerPoints.get(0).setPointSecured(true);
		playerPoints.get(1).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("0", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerPoints.get(0).setPointSecured(false);
		playerPoints.get(1).setPointSecured(true);
		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and assert
		playerPoints.get(0).setPointSecured(true);
		playerPoints.get(1).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());


		// secure a point for player 2 and assert
		playerPoints.get(1).setPointSecured(true);
		playerPoints.get(0).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerPoints.get(1).setPointSecured(true);
		playerPoints.get(0).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("40", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2
		playerPoints.get(1).setPointSecured(true);
		playerPoints.get(0).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("Won Game", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

	}

	@Test
	void testValidateGameInputAfterResult() {
		List<PlayerPoint> playerPoints = new ArrayList<>();
		playerPoints.add(new PlayerPoint("Player1", false));
		playerPoints.add(new PlayerPoint("Player2", false));
		GameScoreInput gameScoreInput = new GameScoreInput(playerPoints, "SecondGame");
		List<ScoreCard> scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertNotNull(scoreCards);

		// secure a point for player 1 and assert
		playerPoints.get(0).setPointSecured(true);
		playerPoints.get(1).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("0", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerPoints.get(0).setPointSecured(false);
		playerPoints.get(1).setPointSecured(true);
		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and assert
		playerPoints.get(0).setPointSecured(true);
		playerPoints.get(1).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());


		// secure a point for player 2 and assert
		playerPoints.get(1).setPointSecured(true);
		playerPoints.get(0).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerPoints.get(1).setPointSecured(true);
		playerPoints.get(0).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("40", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2
		playerPoints.get(1).setPointSecured(true);
		playerPoints.get(0).setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("Won Game", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1
		playerPoints.get(0).setPointSecured(true);
		playerPoints.get(1).setPointSecured(false);

		// ThoughOne Player Won the game we are trying
		// update score for a player which s invalid so we need to throw an exception.
		Assertions.assertThrows(RuntimeException.class, () -> {
			gameScoreService.processGameScore(gameScoreInput);
		});

	}

	private  ScoreCard getScoreCardForPlayer(List<ScoreCard> scoreCards, String player) {

		return scoreCards.stream().filter(scoreCard -> {
			return scoreCard.getPlayerID().equals(player);
		}).findFirst().orElse(null);


	}

}
