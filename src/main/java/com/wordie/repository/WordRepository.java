package com.wordie.repository;

import com.wordie.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    boolean existsByWord(String word);

    @Query(value = "SELECT w FROM Word w ORDER BY FUNCTION('RAND')")
    java.util.List<Word> findRandomWord();
}
