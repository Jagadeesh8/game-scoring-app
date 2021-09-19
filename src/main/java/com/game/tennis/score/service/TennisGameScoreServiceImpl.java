package com.game.tennis.score.service;

import com.game.tennis.score.ApplicationProperties;
import com.game.tennis.score.pojo.GameScoreInput;
import com.game.tennis.score.pojo.PlayerPoint;
import com.game.tennis.score.pojo.ScoreCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class TennisGameScoreServiceImpl implements GameScoreService {

    // Key is Player Key, Score card is player score.
    Map<String, ScoreCard> scoreCards = new HashMap<>();
    Map<String, List<ScoreCard>> gameScoreCards = new HashMap<>();
    Map<String, String> gameRule = new HashMap();

    @Autowired private ApplicationProperties applicationProperties;

    @Autowired private  ApplicationContext applicationContext;


    private List<ScoreCard> starGame(List<PlayerPoint> playerPoints, String gameName) {
        if (CollectionUtils.isEmpty(playerPoints)) {
            throw new RuntimeException("Player details needs to required to start the game");
        }
        playerPoints.stream().forEach(playerPoint -> {
            ScoreCard scoreCard = new ScoreCard(gameName, playerPoint.getPlayerID(), 0);
            scoreCard.setDisplayScore("0");
            scoreCards.put(playerPoint.getPlayerID(), scoreCard);
        });
        List<ScoreCard> updatedScoreCards = new ArrayList<>(scoreCards.values());
        gameScoreCards.put(gameName, updatedScoreCards);

        return updatedScoreCards;
    }

    @Override
    public List<ScoreCard> processGameScore(GameScoreInput scoreInput) {
        String gameName = scoreInput.getGameName();

        PlayerPoint point = scoreInput.getPlayerPoints().
                stream().filter
                (i -> i.isPointSecured()).findFirst().orElse(null);

        List<ScoreCard> scoreCards = new ArrayList<>();

        // If no one secured a point i.e we are starting the game.
        if (point == null) {
            scoreCards = starGame(scoreInput.getPlayerPoints(), gameName);
            return scoreCards;
        } else {
            GameRuleService ruleService = (NormalRuleImpl)
                    applicationContext.getBean("normalRule");
            // verify if we need to Trigger DeuceRule or not,
            // and determine the ruleImpl Runtime
            if (triggerDeuceOrNot(gameScoreCards.get(gameName))) {
                ruleService = (DeuceRuleImpl)
                        applicationContext.getBean("deuceRule");
                return ruleService.processSecuredPoint(point, gameName);
            } else {
                scoreCards = ruleService.processSecuredPoint(point, gameName);

                if (triggerDeuceOrNot(scoreCards)) {
                    activateDeuceRule(scoreCards);
                }
                return scoreCards;
            }
        }
    }

    /**
     * Avivating Deuce Rule by setting Both Player Scores to "DEUCE"
     * @param scoreCards
     */
    private void activateDeuceRule(List<ScoreCard> scoreCards) {
        scoreCards.stream().forEach(sc-> {
            sc.setDisplayScore("Deuce");
        });
    }

    @Override
    public List<ScoreCard> getScoreCardByGame(String gameName) {
        return gameScoreCards.get(gameName);
    }

    /**
     * Method to verify to verify if we need to trigger Deuce Rule or not.
     * @param scoreCards
     * @return
     */
    private boolean triggerDeuceOrNot(List<ScoreCard> scoreCards) {
        return applicationProperties.getDeuceEnabled() &&
                scoreCards.get(0).getScore() == 3 && scoreCards.get(1).getScore() == 3;
    }

}
