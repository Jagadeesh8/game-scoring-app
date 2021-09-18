package com.game.tennis.score.dto;

import java.util.List;
import java.util.Map;

public class GameScoreInput {
    private List<PlayerPoint> playerPoints;
    private String gameName;

    public GameScoreInput(List<PlayerPoint> playerPoints, String gameName) {
        this.playerPoints = playerPoints;
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return "GameScoreInput{" +
                "playerPoints=" + playerPoints +
                '}';
    }

    public String getGameName() {
        return gameName;
    }

    public List<PlayerPoint> getPlayerPoints() {
        return playerPoints;
    }
}
