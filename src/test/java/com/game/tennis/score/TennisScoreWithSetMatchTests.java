package com.game.tennis.score;

import com.game.tennis.score.pojo.*;
import com.game.tennis.score.service.TennisSetMatchServiceImpl;
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
class TennisScoreWithSetMatchTests {

	public static final String PLAYER_1 = "Player1";
	public static final String PLAYER_2 = "Player2";
	@Autowired
	@InjectMocks
	TennisSetMatchServiceImpl setMatchService;

	@Mock ApplicationProperties applicationProperties;

	@BeforeEach
	public void disableDeuceRule() {
		MockitoAnnotations.openMocks(this);
		Mockito.when(applicationProperties.getDeuceEnabled()).thenReturn(false);
	}

	@Test
	void makePlayer1SetWinWithSimpleCondition() {
		//Start First Game
		List<PlayerPoint> playerPoints = getPlayerPointsForNewGame();

		SetMatchScoreInput gameScoreInput = new SetMatchScoreInput(playerPoints, "FirstGame", "Set1");
		List<SetMatchScoreCard> scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Stecore should be updated");
		Assertions.assertEquals(0, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Second Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "SecondGame", "Set1");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(2, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(0, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Third Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "ThirdGame", "Set1");

		scoreCards = makePlayer2WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(2, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 2 Won the Match Set score should be updated");

		// Start Fourth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "FourthGame", "Set1");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(3, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Fifth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "FifthGame", "Set1");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(4, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Sixth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "SixthGame", "Set1");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(5, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		Assertions.assertFalse(getScoreCardForPlayer(scoreCards, PLAYER_1).isWinner(),
				"Player 1 Won the match but he did not score required to win set");


		// Start Seventh Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "SixthGame", "Set1");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(6, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		Assertions.assertTrue(getScoreCardForPlayer(scoreCards, PLAYER_1).isWinner(),
				"As Player 1 Won the Match and Set as" +
						" he got 6 as set score and difference with his opponent is > 4");

	}

	@Test
	void makePlayer1SetWinWithConditionOtherPlayerToReachScore5() {
		//Start First Game
		List<PlayerPoint> playerPoints = getPlayerPointsForNewGame();

		SetMatchScoreInput gameScoreInput = new SetMatchScoreInput(playerPoints, "FirstGame", "Set2");
		List<SetMatchScoreCard> scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Stecore should be updated");
		Assertions.assertEquals(0, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Second Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "SecondGame", "Set2");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(2, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(0, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Third Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "ThirdGame", "Set2");

		scoreCards = makePlayer2WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(2, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 2 Won the Match Set score should be updated");

		// Start Fourth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "FourthGame", "Set2");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(3, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Fifth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "FifthGame", "Set2");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(4, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Sixth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "SixthGame", "Set2");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(5, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		Assertions.assertFalse(getScoreCardForPlayer(scoreCards, PLAYER_1).isWinner(),
				"Player 1 Won the match but he did not score required to win set");


		// Start Seventh Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "SeventhGame", "Set2");
		scoreCards = makePlayer2WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(5, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(2, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Eighth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "EighthGame", "Set2");
		scoreCards = makePlayer2WinGame(gameScoreInput);

		// Start Ninth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "NinthGame", "Set2");
		scoreCards = makePlayer2WinGame(gameScoreInput);

		// Start 10th Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "10ThGame", "Set2");
		scoreCards = makePlayer2WinGame(gameScoreInput);

		// Start 11th Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "11ThGame", "Set2");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertFalse(getScoreCardForPlayer(scoreCards, PLAYER_1).isWinner(),
				"Player 1 Won the match but other player has a score of 5");
		Assertions.assertEquals(5, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore());

		// Start 12th Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "11ThGame", "Set2");
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertTrue(getScoreCardForPlayer(scoreCards, PLAYER_1).isWinner(),
				"Player 1 Won the match but other player has a score of 5");
		Assertions.assertEquals(5, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore());

	}

	private List<PlayerPoint> getPlayerPointsForNewGame() {
		List<PlayerPoint> playerPoints = new ArrayList<>();

		playerPoints.add(new PlayerPoint(PLAYER_1, false));
		playerPoints.add(new PlayerPoint(PLAYER_2, false));
		return playerPoints;
	}

	private List<SetMatchScoreCard> makePlayer1WinGame(SetMatchScoreInput gameScoreInput) {
		PlayerPoint playerOne = gameScoreInput.getPlayerPoints().get(0);
		PlayerPoint playerTwo = gameScoreInput.getPlayerPoints().get(1);

		List<SetMatchScoreCard> scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);

		// secure a Point for player 1 and assert
		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);

		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);

		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);

		playerOne.setPointSecured(true);
		playerTwo.setPointSecured(false);

		scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);

		return scoreCards;
	}

	private List<SetMatchScoreCard> makePlayer2WinGame(SetMatchScoreInput gameScoreInput) {

		PlayerPoint playerOne = gameScoreInput.getPlayerPoints().get(0);
		PlayerPoint playerTwo = gameScoreInput.getPlayerPoints().get(1);
		List<SetMatchScoreCard> scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);

		// secure a Point for player 1 and assert
		playerOne.setPointSecured(false);
		playerTwo.setPointSecured(true);

		scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);

		playerOne.setPointSecured(false);
		playerTwo.setPointSecured(true);

		scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);

		playerOne.setPointSecured(false);
		playerTwo.setPointSecured(true);

		scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);

		playerOne.setPointSecured(false);
		playerTwo.setPointSecured(true);

		scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);

		return scoreCards;
	}


	private  SetMatchScoreCard getScoreCardForPlayer(List<SetMatchScoreCard> scoreCards, String player) {

		return scoreCards.stream().filter(scoreCard -> {
			return scoreCard.getPlayerID().equals(player);
		}).findFirst().orElse(null);

	}

}
