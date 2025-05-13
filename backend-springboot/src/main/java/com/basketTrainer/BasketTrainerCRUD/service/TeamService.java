package com.basketTrainer.BasketTrainerCRUD.service;

import com.basketTrainer.BasketTrainerCRUD.model.Team;
import com.basketTrainer.BasketTrainerCRUD.repository.ITeamRepository;
import com.basketTrainer.BasketTrainerCRUD.repository.ITrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private ITeamRepository teamRepository;

    @Autowired
    private ITrainerRepository trainerRepository;

    public List<Team> getTeamsByTrainer(String trainerDni) {
        return this.teamRepository.findByTrainerDni(trainerDni);
    }

    public Team createTeam(Team team, String trainerDni) {
        this.trainerRepository.findById(trainerDni).ifPresent(team::setTrainer);
        return this.teamRepository.save(team);
    }

    public Team updateTeam(Long teamId, Team updatedTeam) {
        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        team.setName(updatedTeam.getName());
        team.setCategory(updatedTeam.getCategory());
        team.setLeague(updatedTeam.getLeague());
        return this.teamRepository.save(team);
    }

    public void deleteTeam(Long teamId) {
        this.teamRepository.deleteById(teamId);
    }
}