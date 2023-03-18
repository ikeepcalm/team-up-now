package dev.ua.ikeepcalm.teamupnow.database.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "description")
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", length = 1000)
    private String description;

    @Lob
    @Column(name = "picture", nullable = true)
    private byte[] picture;

    @OneToOne
    @JoinColumn(name = "credentials_id")
    private Credentials credentialsId;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public void setCredentials(Credentials credentials) {
        this.credentialsId = credentials;
    }
}
