package dev.ua.ikeepcalm.teamupnow.database.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstUserId")
    private Long firstUserId;

    @Column(name = "secondUserId")
    private Long secondUserId;

    @Column(name = "firstProfileLiked", nullable = false)
    private boolean firstUserLiked;

    @Column(name = "secondProfileLiked", nullable = false)
    private boolean secondUserLiked;

    @Column(name = "score", nullable = false)
    private int score;

    public Match() {}

    public Long getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Long firstUserId) {
        this.firstUserId = firstUserId;
    }

    public Long getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Long secondUserId) {
        this.secondUserId = secondUserId;
    }

    public boolean isFirstUserLiked() {
        return firstUserLiked;
    }

    public void setFirstUserLiked(boolean firstUserLiked) {
        this.firstUserLiked = firstUserLiked;
    }

    public boolean isSecondUserLiked() {
        return secondUserLiked;
    }

    public void setSecondUserLiked(boolean secondUserLiked) {
        this.secondUserLiked = secondUserLiked;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

