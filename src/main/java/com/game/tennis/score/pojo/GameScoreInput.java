package com.game.tennis.score.pojo;

import java.util.List;

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
