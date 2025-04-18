package com.basketTrainer.BasketTrainerCRUD.repository;

import com.basketTrainer.BasketTrainerCRUD.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlayerRepository extends JpaRepository<Player, Long> {
}
