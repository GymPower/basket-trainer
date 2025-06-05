package com.basketTrainer.BasketTrainerCRUD.controller;

import com.basketTrainer.BasketTrainerCRUD.dto.TeamDTO;
import com.basketTrainer.BasketTrainerCRUD.model.Team;
import com.basketTrainer.BasketTrainerCRUD.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;
    // Obtener todos los equipos asociados a un entrenador por su DNI
    @GetMapping("/{trainerDni}")
    public ResponseEntity<List<TeamDTO>> getTeamsByTrainer(@PathVariable String trainerDni) {
        List<Team> teams = teamService.getTeamsByTrainer(trainerDni);
        List<TeamDTO> teamDTOs = teams.stream().map(team -> {
            TeamDTO dto = new TeamDTO();
            dto.setTeamId(team.getTeamId());
            dto.setName(team.getName());
            dto.setCategory(team.getCategory());
            dto.setLeague(team.getLeague());
            // Verificar que el entrenador no sea nulo antes de obtener su DNI
            dto.setTrainerDni(team.getTrainer() != null ? team.getTrainer().getDni() : null);
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOs);
    }

    // Crear un nuevo equipo asignándolo a un entrenador
    @PostMapping("/{trainerDni}")
    public ResponseEntity<TeamDTO> createTeam(@PathVariable String trainerDni, @RequestBody TeamDTO teamDTO) {
        Team team = new Team();
        team.setName(teamDTO.getName());
        team.setCategory(teamDTO.getCategory());
        team.setLeague(teamDTO.getLeague());
        // Crear el equipo usando el servicio, asignando el entrenador
        Team createdTeam = teamService.createTeam(team, trainerDni);

        TeamDTO createdDTO = new TeamDTO();
        createdDTO.setTeamId(createdTeam.getTeamId());
        createdDTO.setName(createdTeam.getName());
        createdDTO.setCategory(createdTeam.getCategory());
        createdDTO.setLeague(createdTeam.getLeague());
        // Verificar que el entrenador no sea nulo antes de obtener su DNI
        createdDTO.setTrainerDni(createdTeam.getTrainer() != null ? createdTeam.getTrainer().getDni() : null);
        return ResponseEntity.ok(createdDTO);
    }

    // Actualizar un equipo existente por su ID
    @PutMapping("/{teamId}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long teamId, @RequestBody TeamDTO teamDTO) {
        Team team = new Team();
        team.setName(teamDTO.getName());
        team.setCategory(teamDTO.getCategory());
        team.setLeague(teamDTO.getLeague());
        // Actualizar el equipo usando el servicio
        Team updatedTeam = teamService.updateTeam(teamId, team);

        // Mapear el equipo actualizado a un DTO para la respuesta
        TeamDTO updatedDTO = new TeamDTO();
        updatedDTO.setTeamId(updatedTeam.getTeamId());
        updatedDTO.setName(updatedTeam.getName());
        updatedDTO.setCategory(updatedTeam.getCategory());
        updatedDTO.setLeague(updatedTeam.getLeague());
        // Verificar que el entrenador no sea nulo antes de obtener su DNI
        updatedDTO.setTrainerDni(updatedTeam.getTrainer() != null ? updatedTeam.getTrainer().getDni() : null);
        return ResponseEntity.ok(updatedDTO);
    }

    // Eliminar un equipo por su ID
    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        // Devolver una respuesta 204 No Content
        return ResponseEntity.noContent().build();
    }
}