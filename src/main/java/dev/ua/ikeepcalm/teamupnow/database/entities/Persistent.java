package dev.ua.ikeepcalm.teamupnow.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "persistent")
public class Persistent {

    @Id
    private Long id;

    @Column(name = "totalUsers", nullable = false)
    private int totalUsers;
}
