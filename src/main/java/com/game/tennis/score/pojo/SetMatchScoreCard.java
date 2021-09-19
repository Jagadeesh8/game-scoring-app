package com.game.tennis.score.pojo;

public class SetMatchScoreCard {
    private String playerID;
    private ScoreCard scoreCard; // Current Game Score Card
    private Integer setScore;
    private boolean winner;
    private Integer tieBreakScore;

    public Integer getTieBreakScore() {
        return tieBreakScore;
    }

    public void setTieBreakScore(Integer tieBreakScore) {
        this.tieBreakScore = tieBreakScore;
    }

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
                "playerID='" + playerID + '\'' +
                ", scoreCard=" + scoreCard +
                ", setScore=" + setScore +
                ", winner=" + winner +
                ", tieBreakScore=" + tieBreakScore +
                '}';
    }

}
