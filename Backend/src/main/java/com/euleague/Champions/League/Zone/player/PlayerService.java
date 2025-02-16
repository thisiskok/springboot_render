package com.euleague.Champions.League.Zone.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

//service for the player entity. use the repository to perform the CRUD operations
@Service

// @Component is used to mark the class as a Spring-managed component
// @Component
public class PlayerService {
    // inject the player repository into the service
    private final PlayerRepository playerRepository;

    // constructor for the player service
    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();// find all players from the database
    }

    // get all players from a team
    public List<Player> getPlayersFromTeam(String teamName) {
        return playerRepository.findAll().stream()
                .filter(player -> teamName.equals(player.getTeam()))
                .collect(Collectors.toList());// collect the players from the team
    }

    // get all players by name, by first name or last name
    public List<Player> getPlayersByName(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    // get all players by pos
    public List<Player> getPlayersByPos(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getPos() != null &&
                        player.getPos().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    // get all players by nation
    public List<Player> getPlayersByNation(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getNation() != null
                        && player.getNation().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    // get all players by team and position
    public List<Player> getPlayersByTeamAndPosition(String team, String position) {
        return playerRepository.findAll().stream()// stream means that we are going to iterate over the list of players
                .filter(player -> team.equals(player.getTeam()) && position.equals(player.getPos()))
                .collect(Collectors.toList()); // collectors is a class that contains the collect method
    }

    public Player addPlayer(Player player) {
        playerRepository.save(player);
        return player;
    }

    // updatePlayer is a method that updates a player
    // updatedPlayer is a parameter that is passed in the method
    public Player updatePlayer(Player updatedPlayer) {
        // optional forces us to check if the player exists
        // updatedPlayer (新数据) → getName() (获取名字)→ findByName() (用这个名字在数据库查找)→
        // Optional<Player> (包装查询结果)
        Optional<Player> existingPlayer = playerRepository.findByName(updatedPlayer.getName());

        if (existingPlayer.isPresent()) {
            Player playerToUpdate = existingPlayer.get();
            // update the player to the updated player
            playerToUpdate.setName(updatedPlayer.getName());
            playerToUpdate.setTeam(updatedPlayer.getTeam());
            playerToUpdate.setPos(updatedPlayer.getPos());
            playerToUpdate.setNation(updatedPlayer.getNation());
            playerToUpdate.setMp(updatedPlayer.getMp());
            playerToUpdate.setMin(updatedPlayer.getMin());
            playerToUpdate.setGls(updatedPlayer.getGls());
            playerToUpdate.setAst(updatedPlayer.getAst());
            playerToUpdate.setPk(updatedPlayer.getPk());
            playerToUpdate.setCrdy(updatedPlayer.getCrdy());
            playerToUpdate.setCrdr(updatedPlayer.getCrdr());
            playerToUpdate.setXg(updatedPlayer.getXg());
            playerToUpdate.setXag(updatedPlayer.getXag());
            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }
        return null; // if the player does not exist, return null
    }

    // delete a player by their name
    @Transactional
    // void means that the method does not return anything
    public void deletePlayer(String playerName) {
        playerRepository.deleteByName(playerName);
    }
}
