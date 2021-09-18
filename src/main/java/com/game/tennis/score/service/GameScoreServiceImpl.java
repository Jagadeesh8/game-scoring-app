package com.game.tennis.score.service;

import com.game.tennis.score.dto.GameScoreInput;
import com.game.tennis.score.dto.PlayerPoint;
import com.game.tennis.score.dto.ScoreCard;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class GameScoreServiceImpl implements GameScoreService {

    // Key is Player Key, Score card is player score.
    Map<String, ScoreCard> scoreCards = new HashMap<>();
    Map<String, List<ScoreCard>> gameScoreCards = new HashMap<>();
    private static List<String> displayScoreList = new ArrayList();

    @PostConstruct
    public void init() {
        displayScoreList.add("0");
        displayScoreList.add("15");
        displayScoreList.add("30");
        displayScoreList.add("40");
        displayScoreList.add("Won Game");
    }

    private List<ScoreCard> starGame(List<PlayerPoint> playerPoints, String gameName) {
        if (CollectionUtils.isEmpty(playerPoints)) {
            throw new RuntimeException("Player details needs to required to start the game");
        }
        playerPoints.stream().forEach(playerPoint -> {
            scoreCards.put(playerPoint.getPlayerID(), new ScoreCard(gameName, playerPoint.getPlayerID(), 0));
        });
        List<ScoreCard> updatedScoreCards = new ArrayList<>(scoreCards.values());
        gameScoreCards.put(gameName, updatedScoreCards);

        return updatedScoreCards;
    }

    private List<ScoreCard> updateGameScore(PlayerPoint playerPoint, String gameName) {

        if (!scoreCards.containsKey(playerPoint.getPlayerID())) {
            throw new RuntimeException("Player details needs to required to start the game");
        }
        scoreCards.get(playerPoint.getPlayerID())
                .setScore(scoreCards.get(playerPoint.getPlayerID()).getScore() + 1);

        List<ScoreCard> updatedScoreCards = new ArrayList<>(scoreCards.values());

        gameScoreCards.put(gameName, updatedScoreCards);

        return updatedScoreCards;
    }

    @Override
    public List<ScoreCard> evaluateGameResult(@NotNull List<ScoreCard> scoreCards) {
        if (scoreCards != null && scoreCards.size() != 2) {
            throw new RuntimeException("Invalid Inputs for Game Result Evaluation");
        }

        ScoreCard player1 = scoreCards.get(0);
        ScoreCard player2 = scoreCards.get(1);

        if (player1.isWinner() || player2.isWinner()) {
            throw new RuntimeException("Winner is Already Determined");
        }

        if ((player1.getScore().compareTo(player2.getScore()) == 1) && player1.getScore() == 4) {
            // Player 1 Won the game
             player1.setWinner(true);
        }
        else if ((player2.getScore().compareTo(player1.getScore()) == 1) && player2.getScore() == 4) {
            // Player 2 Won the game
            player2.setWinner(true);
        }

       return scoreCards;

    }

    private List<ScoreCard> transformScore(List<ScoreCard> scoreCards) {
        scoreCards.stream().forEach(scoreCard -> {
            scoreCard.setDisplayScore(displayScoreList.get(scoreCard.getScore()));
        });

        return  scoreCards;
    }

    @Override
    public List<ScoreCard> processGameScore(GameScoreInput scoreInput) {
        PlayerPoint point = scoreInput.getPlayerPoints().
                stream().filter
                (i -> i.isPointSecured()).findFirst().orElse(null);
        List<ScoreCard> scoreCards = new ArrayList<>();
        // If no one secured a point i.e we are starting the game.
        if (point == null) {
            scoreCards = starGame(scoreInput.getPlayerPoints(), scoreInput.getGameName());
        } else {
            scoreCards = updateGameScore(point, scoreInput.getGameName());
        }

        evaluateGameResult(scoreCards);

        return transformScore(scoreCards);
    }
}
