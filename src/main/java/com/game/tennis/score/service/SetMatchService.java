package com.game.tennis.score.service;

import com.game.tennis.score.dto.SetMatchScoreCard;
import com.game.tennis.score.dto.SetMatchScoreInput;

import java.util.List;

public interface SetMatchService {

    /**
     * Process Game Point for Set Match
     * @param setMatchScoreInput
     * @return
     */
    public List<SetMatchScoreCard> processSetMatchGamePoint(SetMatchScoreInput setMatchScoreInput);
}
