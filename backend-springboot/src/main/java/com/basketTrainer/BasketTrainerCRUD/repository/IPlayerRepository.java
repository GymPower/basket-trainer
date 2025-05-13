package com.basketTrainer.BasketTrainerCRUD.repository;

import com.basketTrainer.BasketTrainerCRUD.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByTeamId(Long teamId);
    List<Player> findByTrainerDni(String trainerDni);
}
