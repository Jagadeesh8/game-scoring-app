package com.game.tennis.score.service;

import com.game.tennis.score.dto.GameScoreInput;
import com.game.tennis.score.dto.ScoreCard;

import java.util.List;

public interface GameScoreService {

    public List<ScoreCard> evaluateGameResult(List<ScoreCard> scoreCards);

    public List<ScoreCard> processGameScore(GameScoreInput scoreInput);
}
