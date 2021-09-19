package com.game.tennis.score.service;

import com.game.tennis.score.pojo.PlayerPoint;
import com.game.tennis.score.pojo.ScoreCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component(value = "deuceRule")
public class DeuceRuleImpl implements GameRuleService {

    private static final String DEUCE = "Deuce";
    private static final String ADVANTAGE = "Advantage";
    private static final String WIN_GAME = "Win Game";

    @Autowired private GameScoreService gameScoreService;

    @Override
    public List<ScoreCard> processSecuredPoint(PlayerPoint playerPoint, String gameName) {
        List<ScoreCard> scoreCards = gameScoreService.getScoreCardByGame(gameName);

        if(CollectionUtils.isEmpty(scoreCards)) {
            throw new RuntimeException("No Score Cards Available with this game, please start game first");
        }

        //Get Score card of player who secured point
        ScoreCard scoreCard = getScoreCardByPlayer(scoreCards, playerPoint.getPlayerID());

        //Get Score card of player who secured advantage Point
        ScoreCard cardWithAdvantage = getScoreCardWithAdvantage(scoreCards);

        // If no one has advantage point set to the player who currently got it
        if (cardWithAdvantage == null) {
            scoreCard.setAdvantagePoint(true);
            scoreCard.setDisplayScore(ADVANTAGE);
        }
        // Since Player who has advantage also secured the point we are making same player win
        else if (cardWithAdvantage.getPlayerID().equalsIgnoreCase(playerPoint.getPlayerID())) {
            scoreCard.setWinner(true);
            scoreCard.setDisplayScore(WIN_GAME);
        } else {
            // Since Player who has advantage did not secure the point we are making both players deuce.
            scoreCards.stream().forEach(sc-> {
                sc.setDisplayScore(DEUCE);
                sc.setAdvantagePoint(false);
            });
        }
        return scoreCards;
    }

    /**
     * Get Score card with advantage, returns null if nothing found.
     * @param scoreCards
     * @return
     */
    private ScoreCard getScoreCardWithAdvantage(List<ScoreCard> scoreCards) {
        return scoreCards.stream().filter(sc -> {
           return sc.hasAdvantagePoint();
        }).findFirst().orElse(null);
    }
}
