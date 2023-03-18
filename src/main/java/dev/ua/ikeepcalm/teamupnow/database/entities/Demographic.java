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
    @JoinColumn(name = "credentials_id")
    private Credentials credentialsId;

    public Long getId() {
        return id;
    }

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