package com.game.topscore.util;

import com.game.topscore.model.PlayerHistory;
import com.game.topscore.model.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoreApplicationUtil {
    public static PlayerHistory constructPlayerHistory(List<Score> scores, String playerName) {
        int lowestScore = Integer.MAX_VALUE;
        int topScore = Integer.MIN_VALUE;
        int totalScore = 0;
        double avgScore = 0;
        List<Integer> scoreList = new ArrayList<>();
        for (Score score : scores) {
            lowestScore = Math.min(score.getScore(), lowestScore);
            topScore = Math.max(score.getScore(), topScore);
            totalScore += score.getScore();
            scoreList.add(score.getScore());
        }
        avgScore = totalScore / scores.size();

        PlayerHistory playerHistory = new PlayerHistory(
                playerName,
                topScore,
                lowestScore,
                avgScore,
                scoreList
        );

        return playerHistory;
    }
}
