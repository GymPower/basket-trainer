package com.basketTrainer.BasketTrainerCRUD.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Data
@Entity
@Table(name="player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long playerId;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "first_surname")
    private String surname1;

    @Column(name = "second_surname")
    private String surname2;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "e_mail")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "trainer_dni")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "team_dni")
    private Team team;



}
