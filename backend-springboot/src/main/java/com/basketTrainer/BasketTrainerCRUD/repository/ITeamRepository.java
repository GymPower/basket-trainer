package com.basketTrainer.BasketTrainerCRUD.repository;

import com.basketTrainer.BasketTrainerCRUD.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByTrainerDni(String trainerDni);
}
