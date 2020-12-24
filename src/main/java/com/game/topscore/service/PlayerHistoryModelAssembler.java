package com.game.topscore.service;

import com.game.topscore.controller.PlayerHistoryController;
import com.game.topscore.model.PlayerHistory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlayerHistoryModelAssembler implements RepresentationModelAssembler<PlayerHistory, EntityModel<PlayerHistory>> {

    @Override
    public EntityModel<PlayerHistory> toModel(PlayerHistory playerHistory) {

        return EntityModel.of(playerHistory,
                WebMvcLinkBuilder.linkTo(methodOn(PlayerHistoryController.class).getPlayerHistory(playerHistory.getPlayerName())).withSelfRel());
//                linkTo(methodOn(TopScoreController.class).getAllTopScore()).withRel("topscores"));

    }
}