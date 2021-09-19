package com.game.tennis.score;

import com.game.tennis.score.pojo.GameScoreInput;
import com.game.tennis.score.pojo.PlayerPoint;
import com.game.tennis.score.pojo.ScoreCard;
import com.game.tennis.score.service.TennisGameScoreServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {
		    TennisScoreApplication.class})
@ComponentScan("com.game.tennis.score")
class TennisScoreWithDeuceTests {

	@Autowired
	@InjectMocks
	TennisGameScoreServiceImpl gameScoreService;

	@Mock ApplicationProperties applicationProperties;

	@BeforeEach
	public void disableDeuceRule() {
		MockitoAnnotations.openMocks(this);
		Mockito.when(applicationProperties.getDeuceEnabled()).thenReturn(true);
	}

	@Test
	void makePlayer1Win() {
		List<PlayerPoint> playerPoints = new ArrayList<>();
		playerPoints.add(new PlayerPoint("Player1", false));
		playerPoints.add(new PlayerPoint("Player2", false));
		GameScoreInput gameScoreInput = new GameScoreInput(playerPoints, "FirstGame");
		List<ScoreCard> scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertNotNull(scoreCards);

		PlayerPoint playerOne = playerPoints.get(0);
		PlayerPoint playerTwo = playerPoints.get(1);

		// secure a point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("0", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerOne.setPointSecured(false);
		playerTwo.setPointSecured(true);
		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("40", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("40", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and Make WIN
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

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


		PlayerPoint playerOne = playerPoints.get(0);
		PlayerPoint playerTwo = playerPoints.get(1);

		// secure a point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("0", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerOne.setPointSecured(false);
		playerTwo.setPointSecured(true);
		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());


		// secure a point for player 2 and assert
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("40", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("Won Game", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

	}


	@Test
	void testGameForWithDEUCE() {
		List<PlayerPoint> playerPoints = new ArrayList<>();
		playerPoints.add(new PlayerPoint("Player1", false));
		playerPoints.add(new PlayerPoint("Player2", false));
		GameScoreInput gameScoreInput = new GameScoreInput(playerPoints, "ThirdGame");
		List<ScoreCard> scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertNotNull(scoreCards);

		PlayerPoint playerOne = playerPoints.get(0);
		PlayerPoint playerTwo = playerPoints.get(1);

		// secure a point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("0", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerOne.setPointSecured(false);
		playerTwo.setPointSecured(true);
		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());


		// secure a point for player 2 and assert
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("40", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and Assert Such that Both has display score of Deuce.
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and Assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertNotEquals("Won Game", getScoreCardForPlayer(scoreCards,"Player1").getDisplayScore());
		Assertions.assertEquals("Advantage", getScoreCardForPlayer(scoreCards,"Player1").getDisplayScore());
		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());
		Assertions.assertTrue(getScoreCardForPlayer(scoreCards,"Player1").hasAdvantagePoint());

		// secure another point for player 1 and Make Winner
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertEquals("Win Game", getScoreCardForPlayer(scoreCards,"Player1").getDisplayScore());
		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

	}

	@Test
	void testGameWithIterationAfterDeuce() {
		List<PlayerPoint> playerPoints = new ArrayList<>();
		playerPoints.add(new PlayerPoint("Player1", false));
		playerPoints.add(new PlayerPoint("Player2", false));
		GameScoreInput gameScoreInput = new GameScoreInput(playerPoints, "FourthGame");
		List<ScoreCard> scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertNotNull(scoreCards);

		// secure a point for player 1 and assert
		PlayerPoint playerOne = playerPoints.get(0);
		PlayerPoint playerTwo = playerPoints.get(1);

		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("0", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerOne.setPointSecured(false);
		playerTwo.setPointSecured(true);
		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());


		// secure a point for player 2 and assert
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("40", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and Assert Such that Both has display score of Deuce.
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and Assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertNotEquals("Won Game", getScoreCardForPlayer(scoreCards,"Player1").getDisplayScore());
		Assertions.assertEquals("Advantage", getScoreCardForPlayer(scoreCards,"Player1").getDisplayScore());
		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());
		Assertions.assertTrue(getScoreCardForPlayer(scoreCards,"Player1").hasAdvantagePoint());

		// secure another point for player 2 and Make Both Players to Deuce
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards,"Player1").getDisplayScore());
		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());
		Assertions.assertFalse(getScoreCardForPlayer(scoreCards,"Player1").hasAdvantagePoint());

		// secure another point for player 1 and attain advantage
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("Advantage", getScoreCardForPlayer(scoreCards,"Player1").getDisplayScore());
		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());
		Assertions.assertTrue(getScoreCardForPlayer(scoreCards,"Player1").hasAdvantagePoint());

		// secure another point for player 1 and make Winner
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("Win Game", getScoreCardForPlayer(scoreCards,"Player1").getDisplayScore());
		Assertions.assertEquals("Deuce", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());
		Assertions.assertTrue(getScoreCardForPlayer(scoreCards,"Player1").hasAdvantagePoint());


	}

	@Test
	void testValidateGameInputAfterResult() {
		List<PlayerPoint> playerPoints = new ArrayList<>();
		playerPoints.add(new PlayerPoint("Player1", false));
		playerPoints.add(new PlayerPoint("Player2", false));
		GameScoreInput gameScoreInput = new GameScoreInput(playerPoints, "FifthGame");
		List<ScoreCard> scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertNotNull(scoreCards);

		PlayerPoint playerOne = playerPoints.get(0);
		PlayerPoint playerTwo = playerPoints.get(1);

		// secure a point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);

		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("0", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerOne.setPointSecured(false);
		playerTwo.setPointSecured(true);
		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("15", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());


		// secure a point for player 2 and assert
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2 and assert
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("40", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 2
		playerTwo.setPointSecured(true);
		playerOne.setPointSecured(false);

		scoreCards = gameScoreService.processGameScore(gameScoreInput);
		Assertions.assertEquals("30", getScoreCardForPlayer(scoreCards, "Player1").getDisplayScore());
		Assertions.assertEquals("Won Game", getScoreCardForPlayer(scoreCards,"Player2").getDisplayScore());

		// secure a point for player 1
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

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
