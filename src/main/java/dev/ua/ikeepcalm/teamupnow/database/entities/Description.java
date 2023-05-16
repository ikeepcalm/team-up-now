package dev.ua.ikeepcalm.teamupnow.database.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "description")
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", length = 1000)
    private String description;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @OneToOne
    @JoinColumn(name = "credentials")
    private Credentials credentials;

}
