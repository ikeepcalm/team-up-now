package dev.ua.ikeepcalm.teamupnow.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Bonus {
    @Id
    private Long id;

    @Column(unique = true)
    private String code;

    @Column
    private boolean validated;
}
