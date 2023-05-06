package dev.ua.ikeepcalm.teamupnow.database.entities;


import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "credentials")
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "accountId", nullable = false, unique = true)
    private Long accountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "uiLanguage", nullable = false)
    private LanguageENUM uiLanguage;

    @Column(name = "avaiableTokens", nullable = false)
    private int connectionTokens;

    @Column(name = "sustainableToken", nullable = false)
    private int sustainableTokens;

    @OneToMany(mappedBy = "credentials", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Game> games;

    @OneToOne(mappedBy = "credentialsId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Demographic demographic;

    @OneToOne(mappedBy = "credentialsId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Description description;

    @OneToOne(mappedBy = "credentialsId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Progress progress;

    @OneToMany(mappedBy = "firstUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> firstMatches;

    @OneToMany(mappedBy = "secondUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> secondMatches;


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

    public Set<Game> getGames() {
        if (this.games == null){
            this.games = new HashSet<>();
        } return this.games;
    }

    public void setGames(Set<Game> input){
        games = input;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConnectionTokens() {
        return connectionTokens;
    }

    public void setConnectionTokens(int connectionTokens) {
        this.connectionTokens = connectionTokens;
    }

    public int getSustainableTokens() {
        return sustainableTokens;
    }

    public void setSustainableTokens(int sustainableTokens) {
        this.sustainableTokens = sustainableTokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credentials that)) return false;
        return Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}