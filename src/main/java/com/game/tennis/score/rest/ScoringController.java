package com.game.tennis.score.rest;

import com.game.tennis.score.dto.GameScoreInput;
import com.game.tennis.score.dto.PlayerPoint;
import com.game.tennis.score.dto.ScoreCard;
import com.game.tennis.score.service.GameScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoringController {

    @Autowired private GameScoreService gameScoreService;

    @PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces  = {MediaType.APPLICATION_JSON_VALUE})
    public List<ScoreCard> updatesScore(@RequestBody GameScoreInput scoreInput) {
        return gameScoreService.processGameScore(scoreInput);
    }

}
