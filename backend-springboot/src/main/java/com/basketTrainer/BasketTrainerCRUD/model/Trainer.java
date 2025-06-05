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
@Table(name = "trainer")
public class Trainer {

    @Id
    @Column(nullable = false, name = "trainer_dni", length = 9)
    private String dni;  // DNI del entrenador, clave primaria

    @Column(nullable = false, name = "username", unique = true)
    private String username;  // Nombre de usuario único del entrenador

    @Column(nullable = false, name = "password")
    private String password;  // Contraseña del entrenador (debería estar encriptada)

    @Column(nullable = false, name = "name")
    private String name;  // Nombre del entrenador

    @Column(nullable = false, name = "first_surname")
    private String surname1;  // Primer apellido del entrenador

    @Column(name = "second_surname")
    private String surname2;  // Segundo apellido del entrenador (opcional)

    @Column(name = "birthdate")
    private LocalDate birthdate;  // Fecha de nacimiento del entrenador

    @Column(name = "e_mail")
    private String email;  // Correo electrónico del entrenador

    @Column(name = "telephone")
    private String telephone;  // Teléfono del entrenador

    @OneToMany(mappedBy = "trainer")
    private List<Team> teams;  // Lista de equipos asociados al entrenador (relación uno-a-muchos)

    @OneToMany(mappedBy = "trainer")
    private List<Player> players;  // Lista de jugadores asociados al entrenador (relación uno-a-muchos)
}