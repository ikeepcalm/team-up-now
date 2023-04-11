package dev.ua.ikeepcalm.teamupnow.database.entities;

import dev.ua.ikeepcalm.teamupnow.database.entities.source.ProgressENUM;
import jakarta.persistence.*;

@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress", nullable = false)
    private ProgressENUM progressENUM;

    @OneToOne
    @JoinColumn(name = "credentialsId")
    private Credentials credentialsId;

    public ProgressENUM getProgressENUM() {
        return progressENUM;
    }

    public void setProgressENUM(ProgressENUM progressENUM) {
        this.progressENUM = progressENUM;
    }

    public void setCredentials(Credentials credentials) {
        this.credentialsId = credentials;
    }
}
