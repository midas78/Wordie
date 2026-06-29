package com.wordie.service;

import com.wordie.model.Word;
import com.wordie.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class WordService {

    private final WordRepository wordRepository;
    private final Random random = new Random();

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word getRandomWord() {
        List<Word> allWords = wordRepository.findAll();
        if (allWords.isEmpty()) {
            return new Word("GHOST");
        }
        return allWords.get(random.nextInt(allWords.size()));
    }

    public boolean isValidWord(String word) {
        return wordRepository.existsByWord(word.toUpperCase());
    }

    public long getWordCount() {
        return wordRepository.count();
    }
}
