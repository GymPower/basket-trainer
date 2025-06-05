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

    // Obtener todos los equipos asociados a un entrenador por su DNI
    public List<Team> getTeamsByTrainer(String trainerDni) {
        return this.teamRepository.findByTrainerDni(trainerDni);
    }

    // Crear un nuevo equipo asignándolo a un entrenador
    public Team createTeam(Team team, String trainerDni) {
        this.trainerRepository.findById(trainerDni).ifPresent(team::setTrainer);
        return this.teamRepository.save(team);
    }

    // Actualizar un equipo existente por su ID
    public Team updateTeam(Long teamId, Team updatedTeam) {
        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        team.setName(updatedTeam.getName());
        team.setCategory(updatedTeam.getCategory());
        team.setLeague(updatedTeam.getLeague());
        return this.teamRepository.save(team);
    }

    // Eliminar un equipo por su ID
    public void deleteTeam(Long teamId) {
        if (!this.teamRepository.existsById(teamId)) {
            throw new RuntimeException("Equipo no encontrado");
        }
        this.teamRepository.deleteById(teamId);
    }
}