package com.basketTrainer.BasketTrainerCRUD.repository;

import com.basketTrainer.BasketTrainerCRUD.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p WHERE p.team.teamId = :teamId")
    List<Player> findByTeamId(@Param("teamId") Long teamId);
    List<Player> findByTrainerDni(String trainerDni);
}
