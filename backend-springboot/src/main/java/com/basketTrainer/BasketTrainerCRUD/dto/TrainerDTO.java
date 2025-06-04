package com.basketTrainer.BasketTrainerCRUD.dto;

import lombok.Data;

@Data
public class TrainerDTO {
    private String dni;
    private String username;
    private String password;
    private String name;
    private String surname1;
    private String surname2;
    private String birthdate;
    private String email;
    private String telephone;
}