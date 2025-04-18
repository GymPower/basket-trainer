package com.basketTrainer.BasketTrainerCRUD.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name="team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;


    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "category")
    private String category;

    @Column(name = "league")
    private String league;

    @ManyToOne
    @JoinColumn(name = "trainer_dni")
    private Trainer trainer;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;
}
