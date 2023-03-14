package dev.ua.ikeepcalm.teamupnow.database.entities;


import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "credentials")
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "accountId", nullable = false, unique = true)
    private Long accountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ui_language", nullable = false)
    private LanguageENUM uiLanguage;

    @OneToOne(mappedBy = "credentialsId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Language language;

    @OneToMany(mappedBy = "credentialsId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Game> games;

    @OneToOne(mappedBy = "credentialsId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Demographic demographic;

    @OneToOne(mappedBy = "credentialsId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Description description;

    @OneToOne(mappedBy = "credentialsId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Progress progress;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public LanguageENUM getUiLanguage() {
        return uiLanguage;
    }

    public void setUiLanguage(LanguageENUM uiLanguage) {
        this.uiLanguage = uiLanguage;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Demographic getDemographic() {
        return demographic;
    }

    public void setDemographic(Demographic demographic) {
        this.demographic = demographic;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
}