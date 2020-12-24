package com.game.topscore.service;

import com.game.topscore.controller.TopScoreController;
import com.game.topscore.model.Score;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TopScoreModelAssembler implements RepresentationModelAssembler<Score, EntityModel<Score>> {

    @Override
    public EntityModel<Score> toModel(Score score) {

        return EntityModel.of(score,
                WebMvcLinkBuilder.linkTo(methodOn(TopScoreController.class).getScoreById(score.getId())).withSelfRel(),
                linkTo(methodOn(TopScoreController.class).getAllScores()).withRel("allScores"));

    }
}