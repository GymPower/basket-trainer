package com.basketTrainer.BasketTrainerCRUD.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name="trainer")
public class Trainer {

    @Id
    @Column(name = "trainer_dni")
    private String dni;

    @Column(nullable = false, name = "username", unique = true)
    private String username;

    @Column(nullable = false, name = "password")
    private String password;


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

    @OneToMany(mappedBy = "trainer")
    private List<Team> teams;

    @OneToMany(mappedBy = "trainer")
    private List<Player> players;


}
