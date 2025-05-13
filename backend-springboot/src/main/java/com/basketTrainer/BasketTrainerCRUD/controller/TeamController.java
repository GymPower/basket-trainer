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

    @GetMapping("/{trainerDni}")
    public ResponseEntity<List<TeamDTO>> getTeamsByTrainer(@PathVariable String trainerDni) {
        List<Team> teams = teamService.getTeamsByTrainer(trainerDni);
        List<TeamDTO> teamDTOs = teams.stream().map(team -> {
            TeamDTO dto = new TeamDTO();
            dto.setTeamId(team.getTeamId());
            dto.setName(team.getName());
            dto.setCategory(team.getCategory());
            dto.setLeague(team.getLeague());
            dto.setTrainerDni(team.getTrainer().getDni());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOs);
    }

    @PostMapping("/{trainerDni}")
    public ResponseEntity<TeamDTO> createTeam(@PathVariable String trainerDni, @RequestBody TeamDTO teamDTO) {
        Team team = new Team();
        team.setName(teamDTO.getName());
        team.setCategory(teamDTO.getCategory());
        team.setLeague(teamDTO.getLeague());
        Team createdTeam = teamService.createTeam(team, trainerDni);

        TeamDTO createdDTO = new TeamDTO();
        createdDTO.setTeamId(createdTeam.getTeamId());
        createdDTO.setName(createdTeam.getName());
        createdDTO.setCategory(createdTeam.getCategory());
        createdDTO.setLeague(createdTeam.getLeague());
        createdDTO.setTrainerDni(createdTeam.getTrainer().getDni());
        return ResponseEntity.ok(createdDTO);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long teamId, @RequestBody TeamDTO teamDTO) {
        Team team = new Team();
        team.setName(teamDTO.getName());
        team.setCategory(teamDTO.getCategory());
        team.setLeague(teamDTO.getLeague());
        Team updatedTeam = teamService.updateTeam(teamId, team);

        TeamDTO updatedDTO = new TeamDTO();
        updatedDTO.setTeamId(updatedTeam.getTeamId());
        updatedDTO.setName(updatedTeam.getName());
        updatedDTO.setCategory(updatedTeam.getCategory());
        updatedDTO.setLeague(updatedTeam.getLeague());
        updatedDTO.setTrainerDni(updatedTeam.getTrainer().getDni());
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}