package com.game.topscore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "score")
public class Score {

    private @Id
    @GeneratedValue Long id;
    private String playerName;
    private int score;
    private long createdTime;

    public Score() {
    }

    public Score(Long id, String playerName, int score, long createdTime) {
        this.id = id;
        this.playerName = playerName;
        this.score = score;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id + " : ");
        sb.append(playerName + " - ");
        sb.append(score + " - ") ;
        sb.append(createdTime + " - ");
        return sb.toString();
    }
}
