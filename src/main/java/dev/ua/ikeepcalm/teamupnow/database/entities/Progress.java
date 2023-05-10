package dev.ua.ikeepcalm.teamupnow.database.entities;

import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress", nullable = false)
    private ProgressENUM progressENUM;

    @OneToOne
    @JoinColumn(name = "credentials")
    private Credentials credentials;
}
