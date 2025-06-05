package com.basketTrainer.BasketTrainerCRUD.service;

import com.basketTrainer.BasketTrainerCRUD.model.Player;
import com.basketTrainer.BasketTrainerCRUD.repository.IPlayerRepository;
import com.basketTrainer.BasketTrainerCRUD.repository.ITeamRepository;
import com.basketTrainer.BasketTrainerCRUD.repository.ITrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private IPlayerRepository playerRepository;

    @Autowired
    private ITeamRepository teamRepository;
    @Autowired
    private ITrainerRepository trainerRepository;

    // Obtener todos los jugadores de un equipo por su ID
    public List<Player> getPlayersByTeam(Long teamId) {
        return this.playerRepository.findByTeamTeamId(teamId);
    }

    // Obtener todos los jugadores asociados a un entrenador por su DNI
    public List<Player> getPlayersByTrainer(String trainerDni) {
        return this.playerRepository.findByTrainerDni(trainerDni);
    }

    // Crear un nuevo jugador asignándolo a un equipo y un entrenador
    public Player createPlayer(Player player, Long teamId, String trainerDni) {
        this.teamRepository.findById(teamId).ifPresent(player::setTeam);
        this.trainerRepository.findById(trainerDni).ifPresent(player::setTrainer);
        return this.playerRepository.save(player);
    }

    // Actualizar un jugador existente por su ID
    public Player updatePlayer(Long playerId, Player updatedPlayer) {
        Player player = this.playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        player.setName(updatedPlayer.getName());
        player.setSurname1(updatedPlayer.getSurname1());
        player.setSurname2(updatedPlayer.getSurname2());
        player.setBirthdate(updatedPlayer.getBirthdate());
        player.setEmail(updatedPlayer.getEmail());
        player.setTelephone(updatedPlayer.getTelephone());
        player.setCategory(updatedPlayer.getCategory());
        return this.playerRepository.save(player);
    }

    // Eliminar un jugador por su ID
    public void deletePlayer(Long playerId) {
        if (!this.playerRepository.existsById(playerId)) {
            throw new RuntimeException("Jugador no encontrado");
        }
        this.playerRepository.deleteById(playerId);
    }
}