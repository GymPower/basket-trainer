package com.basketTrainer.BasketTrainerCRUD.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDTO {
    private Long teamId;
    private String name;
    private String category;
    private String league;
    private String trainerDni;
}
