package dev.ua.ikeepcalm.teamupnow.database.entities;

import dev.ua.ikeepcalm.teamupnow.database.entities.source.AgeENUM;
import jakarta.persistence.*;

@Entity
@Table(name = "demographic")
public class Demographic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "age", nullable = false)
    private AgeENUM age;

    @OneToOne
    @JoinColumn(name = "credentialsId")
    private Credentials credentialsId;

    public AgeENUM getAge() {
        return age;
    }

    public void setAge(AgeENUM age) {
        this.age = age;
    }

    public void setCredentialsId(Credentials credentials) {
        this.credentialsId = credentials;
    }
}