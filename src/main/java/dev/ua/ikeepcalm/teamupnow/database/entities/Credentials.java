package dev.ua.ikeepcalm.teamupnow.database.entities;


import dev.ua.ikeepcalm.teamupnow.database.entities.source.LanguageENUM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @OneToOne(mappedBy = "credentials", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Demographic demographic;

    @OneToOne(mappedBy = "credentials", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Description description;

    @OneToOne(mappedBy = "credentials", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Progress progress;

    @OneToMany(mappedBy = "firstUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> firstMatches;

    @OneToMany(mappedBy = "secondUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> secondMatches;

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