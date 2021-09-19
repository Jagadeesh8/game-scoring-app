package com.game.tennis.score.dto;

import java.util.List;

public class SetMatchScoreInput extends GameScoreInput{

    public SetMatchScoreInput(List<PlayerPoint> playerPoints, String gameName) {
        super(playerPoints, gameName);
    }

    public SetMatchScoreInput(List<PlayerPoint> playerPoints, String gameName, String setMatchName) {
        super(playerPoints, gameName);
        this.setMatchName = setMatchName;
    }

    private String setMatchName;

    public String getSetMatchName() {
        return setMatchName;
    }

    public void setSetMatchName(String setMatchName) {
        this.setMatchName = setMatchName;
    }

    @Override
    public String toString() {
        return super.toString() + "SetMatchScoreInput{" +
                "setMatchName='" + setMatchName + '\'' +
                '}';
    }
}
