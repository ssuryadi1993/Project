package com.game.topscore.service;

import com.game.topscore.interfaces.TopScoreRepository;
import com.game.topscore.model.Score;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadTopScoreData {
    private static final Logger log = LoggerFactory.getLogger(LoadTopScoreData.class);

    @Bean
    CommandLineRunner initDatabase(TopScoreRepository topScoreRepository) {

        return args -> {
            topScoreRepository.save(new Score(1L, "Stephen", 80, 201208080000L));
            topScoreRepository.save(new Score(2L, "Kevin", 70, 201209080000L));
            topScoreRepository.save(new Score(3L, "Marta", 60, 201209235959L));
            topScoreRepository.save(new Score(4L, "STEPHEN", 60, 201209235959L));

            topScoreRepository.findAll().forEach(score -> log.info("Preloaded " + score));
        };
    }
}