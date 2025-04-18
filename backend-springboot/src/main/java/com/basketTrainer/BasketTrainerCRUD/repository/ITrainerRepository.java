package com.basketTrainer.BasketTrainerCRUD.repository;

import com.basketTrainer.BasketTrainerCRUD.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrainerRepository extends JpaRepository<Trainer, String> {
    Trainer findByUsername(String username);

}
