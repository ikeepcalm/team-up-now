package dev.ua.ikeepcalm.teamupnow.database.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "firstUser")
    private Credentials firstUser;

    @ManyToOne
    @JoinColumn(name = "secondUser")
    private Credentials secondUser;

    @Column(name = "firstProfileLiked", nullable = false)
    private boolean firstUserLiked;

    @Column(name = "secondProfileLiked", nullable = false)
    private boolean secondUserLiked;

    @Column(name = "score", nullable = false)
    private int score;

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

    public Credentials getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(Credentials firstUser) {
        this.firstUser = firstUser;
    }

    public Credentials getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(Credentials secondUser) {
        this.secondUser = secondUser;
    }
}

