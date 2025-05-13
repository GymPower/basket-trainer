package com.basketTrainer.BasketTrainerCRUD.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlayerDTO {
    private Long playerId;
    private String name;
    private String surname1;
    private String surname2;
    private LocalDate birthdate;
    private String email;
    private String telephone;
    private String category;
    private Long teamId;
    private String trainerDni;
}