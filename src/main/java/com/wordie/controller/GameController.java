package com.wordie.controller;

import com.wordie.model.AppUser;
import com.wordie.model.Game;
import com.wordie.repository.GameRepository;
import com.wordie.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameController(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/distribution/{userId}")
    public ResponseEntity<?> getGuessDistribution(@PathVariable Long userId) {
        List<Object[]> rows = gameRepository.findGuessDistribution(userId);
        Map<String, Integer> dist = new HashMap<>();
        for (int i = 1; i <= 6; i++) dist.put(String.valueOf(i), 0);
        for (Object[] row : rows) {
            dist.put(String.valueOf(row[0]), ((Number) row[1]).intValue());
        }
        return ResponseEntity.ok(dist);
    }

    @PostMapping
    public ResponseEntity<?> saveResult(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        boolean won = (boolean) body.get("won");
        int guessNum = Integer.parseInt(body.get("guessNum").toString());

        var userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        AppUser user = userOpt.get();
        user.setGamesPlayed(user.getGamesPlayed() + 1);
        if (won) user.setGamesWon(user.getGamesWon() + 1);
        userRepository.save(user);

        gameRepository.save(new Game(user, won, guessNum));

        return ResponseEntity.ok(Map.of(
            "id", user.getId(),
            "username", user.getUsername(),
            "gamesPlayed", user.getGamesPlayed(),
            "gamesWon", user.getGamesWon()
        ));
    }
}
