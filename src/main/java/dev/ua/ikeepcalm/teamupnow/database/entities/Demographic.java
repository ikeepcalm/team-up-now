package dev.ua.ikeepcalm.teamupnow.database.entities;

import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "demographic")
public class Demographic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "age", nullable = false)
    private AgeENUM age;

    @OneToOne
    @JoinColumn(name = "credentials")
    private Credentials credentials;
}