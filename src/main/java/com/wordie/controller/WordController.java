package com.wordie.controller;

import com.wordie.model.Word;
import com.wordie.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/word")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/daily")
    public ResponseEntity<?> getDailyWord() {
        Word word = wordService.getRandomWord();
        if (word == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("word", word.getWord()));
    }
}
