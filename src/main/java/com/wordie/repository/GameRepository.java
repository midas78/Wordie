package com.wordie.repository;

import com.wordie.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g.guessNum, COUNT(g) FROM Game g WHERE g.user.id = :userId AND g.won = true GROUP BY g.guessNum ORDER BY g.guessNum")
    List<Object[]> findGuessDistribution(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Game g WHERE g.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
