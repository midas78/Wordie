package com.wordie.service;

import com.wordie.model.Word;
import com.wordie.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class WordService {

    private final WordRepository wordRepository;
    private final Random random = new Random();

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word getRandomWord() {
        long count = wordRepository.count();
        if (count == 0) return null;
        long randomId = random.nextLong(count) + 1;
        return wordRepository.findById(randomId).orElse(null);
    }
}
