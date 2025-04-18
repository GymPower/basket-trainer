package com.basketTrainer.BasketTrainerCRUD.service;

import com.basketTrainer.BasketTrainerCRUD.dto.LoginRequest;
import com.basketTrainer.BasketTrainerCRUD.dto.LoginResponse;
import com.basketTrainer.BasketTrainerCRUD.model.Trainer;
import com.basketTrainer.BasketTrainerCRUD.repository.ITrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ITrainerRepository iTrainerRepository;

    public LoginResponse login(LoginRequest request) {

        final Trainer trainer = this.iTrainerRepository.findByUsername(request.getUsername());

        if (trainer == null || !trainer.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        return new LoginResponse("Login correcto", trainer.getDni());
    }
}
