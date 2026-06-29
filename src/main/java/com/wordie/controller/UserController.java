package com.wordie.controller;

import com.wordie.model.AppUser;
import com.wordie.repository.GameRepository;
import com.wordie.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public UserController(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username and password required"));
        }

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already taken"));
        }

        AppUser user = userRepository.save(new AppUser(username.trim(), password));
        return ResponseEntity.ok(Map.of("id", user.getId(), "username", user.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username and password required"));
        }

        var userOpt = userRepository.findByUsername(username.trim());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        AppUser user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid password"));
        }

        return ResponseEntity.ok(Map.of("id", user.getId(), "username", user.getUsername(),
                "gamesPlayed", user.getGamesPlayed(), "gamesWon", user.getGamesWon()));
    }

    @PostMapping("/guest")
    public ResponseEntity<?> createGuest(@RequestBody Map<String, String> body) {
        String oldGuest = body.get("oldGuestUsername");
        if (oldGuest != null && !oldGuest.isBlank()) {
            userRepository.findByUsername(oldGuest).ifPresent(user -> {
                gameRepository.deleteByUserId(user.getId());
                userRepository.delete(user);
            });
        }
        String guestName = "guest_" + System.currentTimeMillis();
        AppUser user = userRepository.save(new AppUser(guestName, "guest"));
        return ResponseEntity.ok(Map.of("id", user.getId(), "username", user.getUsername(),
                "gamesPlayed", 0, "gamesWon", 0));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        var userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AppUser user = userOpt.get();
        return ResponseEntity.ok(Map.of("id", user.getId(), "username", user.getUsername(),
                "gamesPlayed", user.getGamesPlayed(), "gamesWon", user.getGamesWon()));
    }
}
