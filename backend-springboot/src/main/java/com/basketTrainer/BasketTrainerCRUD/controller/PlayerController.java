package com.basketTrainer.BasketTrainerCRUD.controller;

import com.basketTrainer.BasketTrainerCRUD.dto.PlayerDTO;
import com.basketTrainer.BasketTrainerCRUD.model.Player;
import com.basketTrainer.BasketTrainerCRUD.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // Obtener todos los jugadores de un equipo por su ID de equipo
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTeam(@PathVariable Long teamId) {
        // Obtener la lista de jugadores del equipo usando el servicio
        List<Player> players = playerService.getPlayersByTeam(teamId);
        // Mapear los jugadores a DTOs para la respuesta
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
            // Verificar que el equipo no sea nulo antes de obtener su ID
            dto.setTeamId(player.getTeam() != null ? player.getTeam().getTeamId() : null);
            // Verificar que el entrenador no sea nulo antes de obtener su DNI
            dto.setTrainerDni(player.getTrainer() != null ? player.getTrainer().getDni() : null);
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }

    // Obtener todos los jugadores asociados a un entrenador por su DNI
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
            // Verificar que el equipo no sea nulo antes de obtener su ID
            dto.setTeamId(player.getTeam() != null ? player.getTeam().getTeamId() : null);
            // Verificar que el entrenador no sea nulo antes de obtener su DNI
            dto.setTrainerDni(player.getTrainer() != null ? player.getTrainer().getDni() : null);
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }

    // Crear un nuevo jugador asignándolo a un equipo y un entrenador
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
        // Crear el jugador usando el servicio, asignando equipo y entrenador
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
        // Verificar que el equipo no sea nulo antes de obtener su ID
        createdDTO.setTeamId(createdPlayer.getTeam() != null ? createdPlayer.getTeam().getTeamId() : null);
        // Verificar que el entrenador no sea nulo antes de obtener su DNI
        createdDTO.setTrainerDni(createdPlayer.getTrainer() != null ? createdPlayer.getTrainer().getDni() : null);
        return ResponseEntity.ok(createdDTO);
    }

    // Actualizar un jugador existente por su ID
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
        // Actualizar el jugador usando el servicio
        Player updatedPlayer = playerService.updatePlayer(playerId, player);

        // Mapear el jugador actualizado a un DTO para la respuesta
        PlayerDTO updatedDTO = new PlayerDTO();
        updatedDTO.setPlayerId(updatedPlayer.getPlayerId());
        updatedDTO.setName(updatedPlayer.getName());
        updatedDTO.setSurname1(updatedPlayer.getSurname1());
        updatedDTO.setSurname2(updatedPlayer.getSurname2());
        updatedDTO.setBirthdate(updatedPlayer.getBirthdate());
        updatedDTO.setEmail(updatedPlayer.getEmail());
        updatedDTO.setTelephone(updatedPlayer.getTelephone());
        updatedDTO.setCategory(updatedPlayer.getCategory());
        // Verificar que el equipo no sea nulo antes de obtener su ID
        updatedDTO.setTeamId(updatedPlayer.getTeam() != null ? updatedPlayer.getTeam().getTeamId() : null);
        // Verificar que el entrenador no sea nulo antes de obtener su DNI
        updatedDTO.setTrainerDni(updatedPlayer.getTrainer() != null ? updatedPlayer.getTrainer().getDni() : null);
        return ResponseEntity.ok(updatedDTO);
    }

    // Eliminar un jugador por su ID
    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
        playerService.deletePlayer(playerId);
        // Devolver una respuesta 204 No Content
        return ResponseEntity.noContent().build();
    }
}