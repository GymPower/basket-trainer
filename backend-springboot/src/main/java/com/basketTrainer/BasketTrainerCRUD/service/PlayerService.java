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

    public List<Player> getPlayersByTeam(Long teamId) {
        return this.playerRepository.findByTeamId(teamId);
    }

    public List<Player> getPlayersByTrainer(String trainerDni) {
        return this.playerRepository.findByTrainerDni(trainerDni);
    }

    public Player createPlayer(Player player, Long teamId, String trainerDni) {
        this.teamRepository.findById(teamId).ifPresent(player::setTeam);
        this.trainerRepository.findById(trainerDni).ifPresent(player::setTrainer);
        return this.playerRepository.save(player);
    }

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

    public void deletePlayer(Long playerId) {
        this.playerRepository.deleteById(playerId);
    }
}