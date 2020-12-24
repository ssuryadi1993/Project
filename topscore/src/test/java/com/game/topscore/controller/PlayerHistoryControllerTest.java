package com.game.topscore.controller;

import com.game.topscore.interfaces.TopScoreRepository;
import com.game.topscore.model.PlayerHistory;
import com.game.topscore.model.Score;
import com.game.topscore.service.PlayerHistoryModelAssembler;
import com.game.topscore.util.ScoreApplicationUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerHistoryControllerTest {

    @Mock
    private TopScoreRepository topScoreRepository;
    @Mock
    private PlayerHistoryModelAssembler playerHistoryModelAssembler;
    private PlayerHistoryController playerHistoryController;
    private List<Score> scores = new ArrayList<>();

    @BeforeEach
    public void initialize() {
        playerHistoryModelAssembler = mock(PlayerHistoryModelAssembler.class);
        topScoreRepository = mock(TopScoreRepository.class);
        playerHistoryController = new PlayerHistoryController(playerHistoryModelAssembler, topScoreRepository);

        scores.add(new Score(1L, "Stephen", 80, 201208080000L));
        scores.add(new Score(2L, "Stephen", 70, 201209080000L));
        scores.add(new Score(3L, "Stephen", 60, 201209235959L));

        System.out.println("Test for PlayerHistoryController started!");
    }

    @Test
    public void testGetPlayerHistory_ExpectSucceed() {

        PlayerHistory playerHistory = new PlayerHistory("Stephen", 80, 60, 70, new ArrayList<>(Arrays.asList(80, 70, 60)));
        EntityModel<PlayerHistory> result = EntityModel.of(playerHistory, new Link("test"));

        when(topScoreRepository.findByplayerNameIgnoreCase("Stephen")).thenReturn(scores);
        when(playerHistoryModelAssembler.toModel(any(PlayerHistory.class))).thenReturn(result);

        EntityModel<PlayerHistory> history = playerHistoryController.getPlayerHistory("Stephen");
        Assertions.assertEquals(history.getContent().getPlayerName(), "Stephen");
    }

    @Test
    public void testConstructPlayerHistory_ExpectSucceed() {
        PlayerHistory playerHistory = ScoreApplicationUtil.constructPlayerHistory(scores, "Stephen");

        Assertions.assertEquals(playerHistory.getAverageScore(), 70);
        Assertions.assertEquals(playerHistory.getLowestScore(), 60);
        Assertions.assertEquals(playerHistory.getTopScore(), 80);
    }
}
