package com.game.topscore.controller;

import com.game.topscore.interfaces.TopScoreRepository;
import com.game.topscore.model.Score;
import com.game.topscore.service.TopScoreModelAssembler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TopScoreControllerTest {

    @Mock
    private TopScoreModelAssembler topScoreModelAssembler;
    @Mock
    private TopScoreRepository repository;
    private TopScoreController topScoreController;
    private List<Score> scores = new ArrayList<>();

    @BeforeEach
    public void initialize() {
        repository = mock(TopScoreRepository.class);
        topScoreModelAssembler = mock(TopScoreModelAssembler.class);
        topScoreController = new TopScoreController(topScoreModelAssembler, repository);

        scores.add(new Score(1L, "Stephen", 80, 201208080000L));
        scores.add(new Score(2L, "Stephen", 70, 201209080000L));
        scores.add(new Score(3L, "Stephen", 60, 201209235959L));

        System.out.println("Test for TopScoreController started!");
    }

    @Test
    public void testGetAllScores() {
        when(repository.findAll()).thenReturn(scores);

        CollectionModel<EntityModel<Score>> result = topScoreController.getAllScores();
        Assertions.assertEquals(result.getContent().size(), 3);

    }

    @Test
    public void testGetScoresByFilter() {
        List<Score> resultScores = new ArrayList<>(Arrays.asList(scores.get(0)));
        when(repository.findByplayerNameList(any(), any(PageRequest.class))).thenReturn(resultScores);

        CollectionModel<EntityModel<Score>> result = topScoreController.getScoresByFilter(null, null, false, "Stephen", 0, 1);
        Assertions.assertEquals(result.getContent().size(), 1);
    }

    @Test
    public void testGetTopScore() {
        EntityModel<Score> score = EntityModel.of(scores.get(0), new Link("http://localhost:8080/scores/1"));
        Optional<Score> score1 = Optional.of(scores.get(0));
        when(repository.findById(1L)).thenReturn(score1);
        when(topScoreModelAssembler.toModel(any(Score.class))).thenReturn(score);

        EntityModel<Score> result = topScoreController.getScoreById(1L);
        Assertions.assertEquals(result.getContent().getScore(), 80);
    }
}
