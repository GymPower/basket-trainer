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
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;  // ID del equipo, generado automáticamente

    @Column(nullable = false, name = "name")
    private String name;  // Nombre del equipo

    @Column(nullable = false, name = "category")
    private String category;  // Categoría del equipo

    @Column(name = "league")
    private String league;  // Liga en la que compite el equipo

    @ManyToOne
    @JoinColumn(name = "trainer_dni")
    private Trainer trainer;  // Relación muchos-a-uno con el entrenador

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;  // Lista de jugadores del equipo (relación uno-a-muchos)
}