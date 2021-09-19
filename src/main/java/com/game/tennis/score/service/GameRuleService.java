package com.game.tennis.score.service;

import com.game.tennis.score.pojo.PlayerPoint;
import com.game.tennis.score.pojo.ScoreCard;

import java.util.List;

public interface GameRuleService {

    /**
     * Process a Point secured by a user and runs the Rules And updates the scores
     * for a given game
     *
     * @param playerPoint
     * @param gameName
     * @return
     */
    public List<ScoreCard> processSecuredPoint(PlayerPoint playerPoint, String gameName);

    default ScoreCard getScoreCardByPlayer(List<ScoreCard> scoreCards, String player) {
        return scoreCards.stream().filter(scoreCard -> {
            return scoreCard.getPlayerID().equals(player);
        }).findFirst().orElse(null);
    }
}
