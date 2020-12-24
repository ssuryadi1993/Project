package com.game.topscore.exception;

public class TopScoreNotFoundException extends RuntimeException {

    public TopScoreNotFoundException(Long id) {
        super("Could not find top score with id : " + id);
    }

    public TopScoreNotFoundException(String prefix, Long id) {
        super(prefix + "could not find top score with id : " + id);
    }

    public TopScoreNotFoundException(String playerName) {
        super("Could not find player history with name : " + playerName);
    }
}