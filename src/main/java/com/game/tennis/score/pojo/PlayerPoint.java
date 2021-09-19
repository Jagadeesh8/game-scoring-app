package com.game.tennis.score.pojo;

public class PlayerPoint {
    private String playerID;
    private boolean pointSecured;

    public PlayerPoint(String playerID, boolean poinntSecured) {
        this.playerID = playerID;
        this.pointSecured = poinntSecured;
    }

    public String getPlayerID() {
        return playerID;
    }

    public boolean isPointSecured() {
        return pointSecured;
    }

    public void setPointSecured(boolean pointSecured) {
        this.pointSecured = pointSecured;
    }
}
