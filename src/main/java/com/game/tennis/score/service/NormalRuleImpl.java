package com.game.tennis.score.service;

import com.game.tennis.score.dto.PlayerPoint;
import com.game.tennis.score.dto.ScoreCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component("normalRule")
public class NormalRuleImpl implements GameRuleService {

    private static List<String> displayScoreList = new ArrayList();

    @Autowired private GameScoreService gameScoreService;

    @PostConstruct
    public void init() {
        displayScoreList.add("0");
        displayScoreList.add("15");
        displayScoreList.add("30");
        displayScoreList.add("40");
        displayScoreList.add("Won Game");
    }

    @Override
    public List<ScoreCard> processSecuredPoint(PlayerPoint playerPoint, String gameName) {
        List<ScoreCard> scoreCards = gameScoreService.getScoreCardByGame(gameName);
        ScoreCard scoreCard = getScoreCardByPlayer(scoreCards, playerPoint.getPlayerID());

        if (scoreCard == null) {
            throw new RuntimeException("Player details needs to be required to start the game");
        }

        ScoreCard player1 = scoreCards.get(0);
        ScoreCard player2 = scoreCards.get(1);

        // As winner is Already determined we don't have to process any.
        if (player1.isWinner() || player2.isWinner()) {
            throw new RuntimeException("Winner is Already Determined");
        }

        // Increment the score as player acquired one and updated display score.
        if (scoreCard.getScore() < 4) {
            scoreCard.setScore(scoreCard.getScore() + 1);
            scoreCard.setDisplayScore(displayScoreList.get(scoreCard.getScore()));
            if (scoreCard.getScore() == 4) {
                scoreCard.setWinner(true);
            }
        }
        return scoreCards;
    }

}
