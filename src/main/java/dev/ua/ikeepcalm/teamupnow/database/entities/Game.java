package dev.ua.ikeepcalm.teamupnow.database.entities;

import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private GameENUM name;

    @ManyToOne
    @JoinColumn(name = "credentials_id")
    private Credentials credentialsId;

    public Long getId() {
        return id;
    }

    public GameENUM getName() {
        return name;
    }

    public void setName(GameENUM name) {
        this.name = name;
    }

    public void setCredentials(Credentials credentials) {
        this.credentialsId = credentials;
    }
}