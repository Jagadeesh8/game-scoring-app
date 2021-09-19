package com.game.tennis.score.service;

import com.game.tennis.score.pojo.SetMatchScoreCard;
import com.game.tennis.score.pojo.SetMatchScoreInput;

import java.util.List;

public interface SetMatchService {

    /**
     * Process Game Point for Set Match, by following individual game rules
     * @param setMatchScoreInput
     * @return
     */
    public List<SetMatchScoreCard> processSetMatchGamePoint(SetMatchScoreInput setMatchScoreInput);
}
