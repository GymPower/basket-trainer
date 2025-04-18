package com.basketTrainer.BasketTrainerCRUD.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String trainerDni;
}
