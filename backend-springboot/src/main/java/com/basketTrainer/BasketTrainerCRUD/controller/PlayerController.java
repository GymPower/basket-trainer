package com.basketTrainer.BasketTrainerCRUD.controller;

import com.basketTrainer.BasketTrainerCRUD.dto.PlayerDTO;
import com.basketTrainer.BasketTrainerCRUD.model.Player;
import com.basketTrainer.BasketTrainerCRUD.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTeam(@PathVariable Long teamId) {
        List<Player> players = playerService.getPlayersByTeam(teamId);
        List<PlayerDTO> playerDTOs = players.stream().map(player -> {
            PlayerDTO dto = new PlayerDTO();
            dto.setPlayerId(player.getPlayerId());
            dto.setName(player.getName());
            dto.setSurname1(player.getSurname1());
            dto.setSurname2(player.getSurname2());
            dto.setBirthdate(player.getBirthdate());
            dto.setEmail(player.getEmail());
            dto.setTelephone(player.getTelephone());
            dto.setCategory(player.getCategory());
            dto.setTeamId(player.getTeam().getTeamId());
            dto.setTrainerDni(player.getTrainer().getDni());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }

    @GetMapping("/trainer/{trainerDni}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTrainer(@PathVariable String trainerDni) {
        List<Player> players = playerService.getPlayersByTrainer(trainerDni);
        List<PlayerDTO> playerDTOs = players.stream().map(player -> {
            PlayerDTO dto = new PlayerDTO();
            dto.setPlayerId(player.getPlayerId());
            dto.setName(player.getName());
            dto.setSurname1(player.getSurname1());
            dto.setSurname2(player.getSurname2());
            dto.setBirthdate(player.getBirthdate());
            dto.setEmail(player.getEmail());
            dto.setTelephone(player.getTelephone());
            dto.setCategory(player.getCategory());
            dto.setTeamId(player.getTeam().getTeamId());
            dto.setTrainerDni(player.getTrainer().getDni());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }

    @PostMapping("/{teamId}/{trainerDni}")
    public ResponseEntity<PlayerDTO> createPlayer(@PathVariable Long teamId, @PathVariable String trainerDni, @RequestBody PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getName());
        player.setSurname1(playerDTO.getSurname1());
        player.setSurname2(playerDTO.getSurname2());
        player.setBirthdate(playerDTO.getBirthdate());
        player.setEmail(playerDTO.getEmail());
        player.setTelephone(playerDTO.getTelephone());
        player.setCategory(playerDTO.getCategory());
        Player createdPlayer = playerService.createPlayer(player, teamId, trainerDni);

        PlayerDTO createdDTO = new PlayerDTO();
        createdDTO.setPlayerId(createdPlayer.getPlayerId());
        createdDTO.setName(createdPlayer.getName());
        createdDTO.setSurname1(createdPlayer.getSurname1());
        createdDTO.setSurname2(createdPlayer.getSurname2());
        createdDTO.setBirthdate(createdPlayer.getBirthdate());
        createdDTO.setEmail(createdPlayer.getEmail());
        createdDTO.setTelephone(createdPlayer.getTelephone());
        createdDTO.setCategory(createdPlayer.getCategory());
        createdDTO.setTeamId(createdPlayer.getTeam().getTeamId());
        createdDTO.setTrainerDni(createdPlayer.getTrainer().getDni());
        return ResponseEntity.ok(createdDTO);
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long playerId, @RequestBody PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getName());
        player.setSurname1(playerDTO.getSurname1());
        player.setSurname2(playerDTO.getSurname2());
        player.setBirthdate(playerDTO.getBirthdate());
        player.setEmail(playerDTO.getEmail());
        player.setTelephone(playerDTO.getTelephone());
        player.setCategory(playerDTO.getCategory());
        Player updatedPlayer = playerService.updatePlayer(playerId, player);

        PlayerDTO updatedDTO = new PlayerDTO();
        updatedDTO.setPlayerId(updatedPlayer.getPlayerId());
        updatedDTO.setName(updatedPlayer.getName());
        updatedDTO.setSurname1(updatedPlayer.getSurname1());
        updatedDTO.setSurname2(updatedPlayer.getSurname2());
        updatedDTO.setBirthdate(updatedPlayer.getBirthdate());
        updatedDTO.setEmail(updatedPlayer.getEmail());
        updatedDTO.setTelephone(updatedPlayer.getTelephone());
        updatedDTO.setCategory(updatedPlayer.getCategory());
        updatedDTO.setTeamId(updatedPlayer.getTeam().getTeamId());
        updatedDTO.setTrainerDni(updatedPlayer.getTrainer().getDni());
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
        playerService.deletePlayer(playerId);
        return ResponseEntity.noContent().build();
    }
}
