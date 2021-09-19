package com.game.tennis.score.service;

import com.game.tennis.score.pojo.GameScoreInput;
import com.game.tennis.score.pojo.ScoreCard;
import com.game.tennis.score.pojo.SetMatchScoreInput;

import java.util.List;

public interface GameScoreService {
    /**
     * Process Game Score Input and updated scored cards of each player
     * It will trigger the Games rules {Normal, Deuce} ad per score cards.
     * @param scoreInput
     * @return
     */
    public List<ScoreCard> processGameScore(GameScoreInput scoreInput);

    /**
     * Fetch Score cards by game name.
     * @param gameName
     * @return
     */
    public List<ScoreCard> getScoreCardByGame(String gameName);

}
