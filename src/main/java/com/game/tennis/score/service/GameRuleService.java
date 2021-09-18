package com.game.tennis.score.service;

import com.game.tennis.score.dto.ScoreCard;

public interface GameRuleService {
    public void processRule(ScoreCard player1, ScoreCard player2);
}
