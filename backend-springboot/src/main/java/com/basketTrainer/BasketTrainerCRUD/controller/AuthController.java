package com.basketTrainer.BasketTrainerCRUD.controller;

import com.basketTrainer.BasketTrainerCRUD.dto.LoginRequest;
import com.basketTrainer.BasketTrainerCRUD.dto.LoginResponse;
import com.basketTrainer.BasketTrainerCRUD.dto.TrainerDTO;
import com.basketTrainer.BasketTrainerCRUD.model.Trainer;
import com.basketTrainer.BasketTrainerCRUD.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.basketTrainer.BasketTrainerCRUD.service.AuthService;

import java.time.LocalDate;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private AuthService authService;

    // Endpoint para registrar un nuevo entrenador
    @PostMapping("/register")
    public ResponseEntity<TrainerDTO> registerTrainer(@RequestBody TrainerDTO trainerDTO) {
        // Mapear los datos del DTO a un objeto Trainer
        Trainer trainer = new Trainer();
        trainer.setDni(trainerDTO.getDni());
        trainer.setUsername(trainerDTO.getUsername());
        trainer.setPassword(trainerDTO.getPassword());
        trainer.setName(trainerDTO.getName());
        trainer.setSurname1(trainerDTO.getSurname1());
        trainer.setSurname2(trainerDTO.getSurname2());
        // Convertir la fecha de nacimiento de String a LocalDate si no es nula
        trainer.setBirthdate(trainerDTO.getBirthdate() != null ? LocalDate.parse(trainerDTO.getBirthdate()) : null);
        trainer.setEmail(trainerDTO.getEmail());
        trainer.setTelephone(trainerDTO.getTelephone());

        // Registrar el entrenador usando el servicio
        Trainer createdTrainer = trainerService.registerTrainer(trainer);

        // Mapear el Trainer creado a un DTO para la respuesta
        TrainerDTO createdDTO = new TrainerDTO();
        createdDTO.setDni(createdTrainer.getDni());
        createdDTO.setUsername(createdTrainer.getUsername());
        createdDTO.setPassword(createdTrainer.getPassword());
        createdDTO.setName(createdTrainer.getName());
        createdDTO.setSurname1(createdTrainer.getSurname1());
        createdDTO.setSurname2(createdTrainer.getSurname2());
        // Convertir la fecha de nacimiento de LocalDate a String para el DTO
        createdDTO.setBirthdate(createdTrainer.getBirthdate() != null ? createdTrainer.getBirthdate().toString() : null);
        createdDTO.setEmail(createdTrainer.getEmail());
        createdDTO.setTelephone(createdTrainer.getTelephone());

        // Devolver el DTO con el entrenador registrado y un código 200 OK
        return ResponseEntity.ok(createdDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}