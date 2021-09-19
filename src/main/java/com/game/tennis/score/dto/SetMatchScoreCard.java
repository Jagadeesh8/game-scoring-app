package com.game.tennis.score.dto;

public class SetMatchScoreCard {
    private String playerID;
    private ScoreCard scoreCard; // Current Game Score Card
    private Integer setScore;
    private boolean winner;

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public SetMatchScoreCard(String playerId, ScoreCard scoreCard, Integer setScore) {
        this.playerID = playerId;
        this.scoreCard = scoreCard;
        this.setScore = setScore;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public ScoreCard getScoreCard() {
        return scoreCard;
    }

    public void setScoreCard(ScoreCard scoreCard) {
        this.scoreCard = scoreCard;
    }

    public Integer getSetScore() {
        return setScore;
    }

    public void setSetScore(Integer setScore) {
        this.setScore = setScore;
    }

    @Override
    public String toString() {
        return "SetMatchScoreCard{" +
                "playerId='" + playerID + '\'' +
                ", scoreCard=" + scoreCard +
                ", setScore=" + setScore +
                '}';
    }
}
