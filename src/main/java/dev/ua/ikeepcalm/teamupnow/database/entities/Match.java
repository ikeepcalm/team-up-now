package dev.ua.ikeepcalm.teamupnow.database.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    @Column(name = "hidden", nullable = false, columnDefinition = "boolean default false")
    private boolean hidden;

}

