package com.game.topscore.controller;

import com.game.topscore.exception.TopScoreNotFoundException;
import com.game.topscore.interfaces.TopScoreRepository;
import com.game.topscore.model.PlayerHistory;
import com.game.topscore.model.Score;
import com.game.topscore.service.PlayerHistoryModelAssembler;
import com.game.topscore.util.ScoreApplicationUtil;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayerHistoryController {

    private final PlayerHistoryModelAssembler playerHistoryModelAssembler;
    private final TopScoreRepository repository;

    public PlayerHistoryController(PlayerHistoryModelAssembler playerHistoryModelAssembler,
                                   TopScoreRepository repository) {
        this.playerHistoryModelAssembler = playerHistoryModelAssembler;
        this.repository = repository;
    }


    @GetMapping("/playerHistory/{playerName}")
    public EntityModel<PlayerHistory> getPlayerHistory(@PathVariable String playerName) {

        try {
            List<Score> scores = repository.findByplayerNameIgnoreCase(playerName);
            PlayerHistory playerHistory = ScoreApplicationUtil.constructPlayerHistory(scores, playerName);

            return playerHistoryModelAssembler.toModel(playerHistory);
        } catch (Exception e) {
            throw new TopScoreNotFoundException(playerName);
        }
    }

}