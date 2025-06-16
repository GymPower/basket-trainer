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
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long playerId;  // ID del jugador, generado automáticamente

    @Column(nullable = false, name = "name")
    private String name;  // Nombre del jugador

    @Column(nullable = false, name = "first_surname")
    private String surname1;  // Primer apellido del jugador

    @Column(name = "second_surname")
    private String surname2;  // Segundo apellido del jugador (opcional)

    @Column(name = "birthdate")
    private LocalDate birthdate;  // Fecha de nacimiento del jugador

    @Column(name = "e_mail")
    private String email;  // Correo electrónico del jugador

    @Column(name = "telephone")
    private String telephone;  // Teléfono del jugador

    @Column(name = "category")
    private String category;  // Categoría del jugador

    @ManyToOne
    @JoinColumn(name = "trainer_dni", referencedColumnName = "trainer_dni", nullable = true)
    private Trainer trainer;  // Relación muchos-a-uno con el entrenador

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;  // Relación muchos-a-uno con el equipo
}