package com.game.tennis.score;

import com.game.tennis.score.pojo.PlayerPoint;
import com.game.tennis.score.pojo.SetMatchScoreCard;
import com.game.tennis.score.pojo.SetMatchScoreInput;
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
class TennisScoreWithSetMatchTieBreakTests {

	public static final String PLAYER_1 = "Player1";
	public static final String PLAYER_2 = "Player2";
	public static final String SET_3 = "Set3";
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
	void makePlayer1SetWinWithConditionOtherPlayerToReachScoreTieBreak() {
		//Start First Game
		List<PlayerPoint> playerPoints = getPlayerPointsForNewGame();

		SetMatchScoreInput gameScoreInput = new SetMatchScoreInput(playerPoints, "FirstGame", SET_3);
		List<SetMatchScoreCard> scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Stecore should be updated");
		Assertions.assertEquals(0, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Second Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "SecondGame", SET_3);
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(2, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(0, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Third Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "ThirdGame", SET_3);

		scoreCards = makePlayer2WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(2, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 2 Won the Match Set score should be updated");

		// Start Fourth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "FourthGame", SET_3);
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(3, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Fifth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "FifthGame", SET_3);
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(4, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(1, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Sixth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "SixthGame", SET_3);
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
		gameScoreInput = new SetMatchScoreInput(playerPoints, "SeventhGame", SET_3);
		scoreCards = makePlayer2WinGame(gameScoreInput);

		Assertions.assertNotNull(scoreCards);
		Assertions.assertEquals(5, getScoreCardForPlayer(scoreCards, PLAYER_1).getSetScore(),
				"As Player 1 Won the Match Set score should be updated");
		Assertions.assertEquals(2, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore(),
				"As Player 1 Won the Match Set score should not be updated");

		// Start Eighth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "EighthGame", SET_3);
		scoreCards = makePlayer2WinGame(gameScoreInput);

		// Start Ninth Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "NinthGame", SET_3);
		scoreCards = makePlayer2WinGame(gameScoreInput);

		// Start 10th Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "10ThGame", SET_3);
		scoreCards = makePlayer2WinGame(gameScoreInput);

		// Start 11th Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "11ThGame", SET_3);
		scoreCards = makePlayer1WinGame(gameScoreInput);

		Assertions.assertFalse(getScoreCardForPlayer(scoreCards, PLAYER_1).isWinner(),
				"Player 1 Won the match but other player has a score of 5");
		Assertions.assertEquals(5, getScoreCardForPlayer(scoreCards, PLAYER_2).getSetScore());

		// Start 12th Game
		playerPoints = getPlayerPointsForNewGame();
		gameScoreInput = new SetMatchScoreInput(playerPoints, "12ThGame", SET_3);
		scoreCards = makePlayer2WinGame(gameScoreInput);

		// Assert Either player is a Winner, i.e Tie-Break RULE IS ACTIVATED
		Assertions.assertFalse(getScoreCardForPlayer(scoreCards, PLAYER_1).isWinner());
		Assertions.assertFalse(getScoreCardForPlayer(scoreCards, PLAYER_2).isWinner());

		// make player 2 win continually
		for (int i =0; i<8; i++) {
			gameScoreInput.getPlayerPoints().get(0).setPointSecured(false);
			gameScoreInput.getPlayerPoints().get(1).setPointSecured(true);
			scoreCards = setMatchService.processSetMatchGamePoint(gameScoreInput);
		}

		Assertions.assertTrue(getScoreCardForPlayer(scoreCards, PLAYER_2).isWinner());

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

		// secure a playerOne for player 1 and assert
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

		// secure a playerOne for player 1 and assert
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
