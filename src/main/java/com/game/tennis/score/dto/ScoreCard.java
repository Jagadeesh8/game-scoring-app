package com.game.tennis.score.dto;

public class ScoreCard {
    private String gameName;
    private String playerID;
    private Integer score;
    private boolean isWinner;
    private String displayScore;

    public String getDisplayScore() {
        return displayScore;
    }

    public void setDisplayScore(String displayScore) {
        this.displayScore = displayScore;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public ScoreCard(String gameName, String playerID, Integer score) {
        this.gameName = gameName;
        this.playerID = playerID;
        this.score = score;
    }

    public String getGameName() {
        return gameName;
    }

    public String getPlayerID() {
        return playerID;
    }

    public Integer getScore() {
        return score;
    }

    public ScoreCard setScore(Integer score) {
        this.score = score;
        return this;
    }

    @Override
    public String toString() {
        return "ScoreCard{" +
                "gameType='" + gameName + '\'' +
                ", playerName='" + playerID + '\'' +
                ", score=" + score +
                '}';
    }
}
