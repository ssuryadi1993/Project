package com.game.topscore.controller;

import com.game.topscore.service.TopScoreModelAssembler;
import com.game.topscore.exception.TopScoreNotFoundException;
import com.game.topscore.interfaces.TopScoreRepository;
import com.game.topscore.model.Score;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TopScoreController {

    private final TopScoreModelAssembler topScoreModelAssembler;
    private final TopScoreRepository repository;

    public TopScoreController(TopScoreModelAssembler topScoreModelAssembler,
                              TopScoreRepository repository) {
        this.topScoreModelAssembler = topScoreModelAssembler;
        this.repository = repository;
    }

    @GetMapping("/scores")
    public CollectionModel<EntityModel<Score>> getAllScores() {

        List<EntityModel<Score>> topScores = repository.findAll().stream()
                .map(topScoreModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(topScores, linkTo(methodOn(TopScoreController.class).getAllScores()).withSelfRel());
    }


    @GetMapping("/scores/filter")
    @ResponseBody
    public CollectionModel<EntityModel<Score>> getScoresByFilter(
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false, defaultValue = "true") boolean between,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
            ){

        List<EntityModel<Score>> topScores = new ArrayList<>();
        List<String> names = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            names = Arrays.asList(name.split("_"));
            names.replaceAll(String::toLowerCase);
        }
        topScores = filter(before, after, between, names, page, size);

        return CollectionModel.of(topScores, linkTo(methodOn(TopScoreController.class).getScoresByFilter(before, after, between, name, page, size)).withSelfRel());
    }

    private List<EntityModel<Score>> filter(Long before, Long after, boolean isBetween, List<String> names, int page, int size) {

        List<EntityModel<Score>> topScores = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, size);

        if (before != null && after != null) {
            if (isBetween) {
                topScores = repository.findBycreatedTimeBetween(after, before, pageRequest).stream()
                        .map(topScoreModelAssembler::toModel)
                        .collect(Collectors.toList());
            } else {
                topScores = repository.findBycreatedTimeOr(after, before, pageRequest).stream()
                        .map(topScoreModelAssembler::toModel)
                        .collect(Collectors.toList());
            }
        } else if (after != null) {

            topScores = repository.findBycreatedTimeGreaterThan(after, pageRequest).stream()
                    .map(topScoreModelAssembler::toModel)
                    .collect(Collectors.toList());
        } else if (before != null) {
            topScores = repository.findBycreatedTimeLessThan(before, pageRequest).stream()
                    .map(topScoreModelAssembler::toModel)
                    .collect(Collectors.toList());
        } else {
            topScores = repository.findByplayerNameList(names, pageRequest).stream()
                    .map(topScoreModelAssembler::toModel)
                    .collect(Collectors.toList());
        }

        return topScores;
    }

    @GetMapping("/scores/{id}")
    public EntityModel<Score> getScoreById(@PathVariable Long id) {

        Score score = repository.findById(id)
                .orElseThrow(() -> new TopScoreNotFoundException(id));

        return topScoreModelAssembler.toModel(score);
    }

    @PostMapping("/scores")
    public ResponseEntity<?> newTopSore(@RequestBody Score newScore) {

        EntityModel<Score> entityModel = topScoreModelAssembler.toModel(repository.save(newScore));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/scores/{id}")
    public ResponseEntity<?> deleteTopScore(@PathVariable Long id) throws TopScoreNotFoundException {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new TopScoreNotFoundException("Delete failed, ", id);
        }
    }
}