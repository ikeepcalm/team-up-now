package dev.ua.ikeepcalm.teamupnow.database.entities;


import dev.ua.ikeepcalm.teamupnow.database.entities.source.GameENUM;
import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Column(name = "connectionToken", nullable = false)
    private int connectionToken;

    @OneToMany(mappedBy = "credentials", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public int getConnectionToken() {
        return connectionToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConnectionToken(int connectionToken) {

        this.connectionToken = connectionToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credentials)) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}