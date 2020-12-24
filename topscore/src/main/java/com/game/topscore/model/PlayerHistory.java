package com.game.topscore.model;

import java.util.List;

public class PlayerHistory {

    private String playerName;
    private int topScore;
    private int lowestScore;
    private double averageScore;
    private List<Integer> scoreList;

    public PlayerHistory() {
    }

    public PlayerHistory(String playerName, int topScore, int lowestScore, double averageScore, List<Integer> scoreList) {
        this.playerName = playerName;
        this.topScore = topScore;
        this.lowestScore = lowestScore;
        this.averageScore = averageScore;
        this.scoreList = scoreList;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getTopScore() {
        return topScore;
    }

    public int getLowestScore() {
        return lowestScore;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public List<Integer> getScoreList() {
        return scoreList;
    }
}
