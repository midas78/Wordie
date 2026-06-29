package com.wordie.controller;

import com.wordie.model.Word;
import com.wordie.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/word")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/random")
    public ResponseEntity<Map<String, String>> getRandomWord() {
        Word word = wordService.getRandomWord();
        return ResponseEntity.ok(Map.of("word", word.getWord()));
    }

    @GetMapping("/validate/{word}")
    public ResponseEntity<Map<String, Boolean>> validateWord(@PathVariable String word) {
        boolean valid = wordService.isValidWord(word);
        return ResponseEntity.ok(Map.of("valid", valid));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getWordCount() {
        return ResponseEntity.ok(Map.of("count", wordService.getWordCount()));
    }
}
