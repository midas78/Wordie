package com.wordie.repository;

import com.wordie.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "SELECT guess_num, COUNT(*) FROM game WHERE user_id = ?1 AND won = TRUE GROUP BY guess_num ORDER BY guess_num", nativeQuery = true)
    List<Object[]> findGuessDistribution(Long userId);

    void deleteByUserId(Long userId);
}
