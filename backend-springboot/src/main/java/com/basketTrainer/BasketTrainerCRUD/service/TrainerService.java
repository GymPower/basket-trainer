package com.basketTrainer.BasketTrainerCRUD.service;

import com.basketTrainer.BasketTrainerCRUD.model.Trainer;
import com.basketTrainer.BasketTrainerCRUD.repository.ITrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {

    @Autowired
    private ITrainerRepository trainerRepository;

    public Trainer registerTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }
}