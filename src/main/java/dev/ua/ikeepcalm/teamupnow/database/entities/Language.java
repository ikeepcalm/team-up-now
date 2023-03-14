package dev.ua.ikeepcalm.teamupnow.database.entities;

import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import jakarta.persistence.*;

@Entity
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private LanguageENUM language;

    @OneToOne
    @JoinColumn(name = "credentials_id")
    private Credentials credentialsId;

    public Long getId() {
        return id;
    }

    public LanguageENUM getLanguage() {
        return language;
    }

    public void setLanguage(LanguageENUM language) {
        this.language = language;
    }

    public Credentials getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Credentials userInfo) {
        this.credentialsId = userInfo;
    }
}