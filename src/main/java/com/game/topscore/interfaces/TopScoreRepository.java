package com.game.topscore.interfaces;

import com.game.topscore.model.Score;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TopScoreRepository extends PagingAndSortingRepository<Score, Long>, JpaRepository<Score, Long> {

    List<Score> findByplayerNameIgnoreCase(String playerName);
    @Query("select s from score s where s.createdTime > ?1")
    List<Score> findBycreatedTimeGreaterThan(long time, PageRequest pageRequest);
    @Query("select s from score s where s.createdTime < ?1")
    List<Score> findBycreatedTimeLessThan(long time, PageRequest pageRequest);
    @Query("select s from score s where s.createdTime < ?2 and s.createdTime > ?1")
    List<Score> findBycreatedTimeBetween(long time1, long time2, PageRequest pageRequest);
    @Query("select s from score s where s.createdTime < ?2 or s.createdTime > ?1")
    List<Score> findBycreatedTimeOr(long time1, long time2, PageRequest pageRequest);
    @Query("select s from score s where lower(s.playerName) in ?1")
    List<Score> findByplayerNameList(List<String> playerName, PageRequest pageRequest);
}
