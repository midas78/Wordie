package com.wordie.model;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(nullable = false)
    private boolean won;

    @Column(name = "guess_num", nullable = false)
    private int guessNum;

    public Game() {}

    public Game(AppUser user, boolean won, int guessNum) {
        this.user = user;
        this.won = won;
        this.guessNum = guessNum;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }

    public boolean isWon() { return won; }
    public void setWon(boolean won) { this.won = won; }

    public int getGuessNum() { return guessNum; }
    public void setGuessNum(int guessNum) { this.guessNum = guessNum; }
}
