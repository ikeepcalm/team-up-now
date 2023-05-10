package dev.ua.ikeepcalm.teamupnow.database.entities;

import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private GameENUM name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credentials")
    private Credentials credentials;

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name=" + name +
                ", credentials=" + credentials +
                '}';
    }
}