package com.basketTrainer.BasketTrainerCRUD.repository;

import com.basketTrainer.BasketTrainerCRUD.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeamRepository extends JpaRepository<Team, Long> {
}
