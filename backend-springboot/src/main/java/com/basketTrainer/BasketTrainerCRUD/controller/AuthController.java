package com.basketTrainer.BasketTrainerCRUD.controller;

import com.basketTrainer.BasketTrainerCRUD.dto.TrainerDTO;
import com.basketTrainer.BasketTrainerCRUD.model.Trainer;
import com.basketTrainer.BasketTrainerCRUD.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TrainerService trainerService;

    @PostMapping("/register")
    public ResponseEntity<TrainerDTO> registerTrainer(@RequestBody TrainerDTO trainerDTO) {
        Trainer trainer = new Trainer();
        trainer.setDni(trainerDTO.getDni());
        trainer.setUsername(trainerDTO.getUsername());
        trainer.setPassword(trainerDTO.getPassword());
        trainer.setName(trainerDTO.getName());
        trainer.setSurname1(trainerDTO.getSurname1());
        trainer.setSurname2(trainerDTO.getSurname2());
        trainer.setBirthdate(trainerDTO.getBirthdate() != null ? LocalDate.parse(trainerDTO.getBirthdate()) : null);
        trainer.setEmail(trainerDTO.getEmail());
        trainer.setTelephone(trainerDTO.getTelephone());

        Trainer createdTrainer = trainerService.registerTrainer(trainer);

        TrainerDTO createdDTO = new TrainerDTO();
        createdDTO.setDni(createdTrainer.getDni());
        createdDTO.setUsername(createdTrainer.getUsername());
        createdDTO.setPassword(createdTrainer.getPassword());
        createdDTO.setName(createdTrainer.getName());
        createdDTO.setSurname1(createdTrainer.getSurname1());
        createdDTO.setSurname2(createdTrainer.getSurname2());
        createdDTO.setBirthdate(createdTrainer.getBirthdate() != null ? createdTrainer.getBirthdate().toString() : null);
        createdDTO.setEmail(createdTrainer.getEmail());
        createdDTO.setTelephone(createdTrainer.getTelephone());

        return ResponseEntity.ok(createdDTO);
    }
}