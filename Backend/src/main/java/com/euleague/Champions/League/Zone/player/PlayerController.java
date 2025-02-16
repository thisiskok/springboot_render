package com.euleague.Champions.League.Zone.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//客户端请求 → Controller → Service → Repository → 数据库
//响应      ← Controller ← Service ← Repository ← 数据库
//rest controller is a controller that handles the requests and responses
@RestController
// mapping the request to the player controller
@RequestMapping(path = "api/v1/player")
// public class PlayerController is the controller for the player entity
public class PlayerController {
    // inject the player service into the controller
    private final PlayerService playerService;
    // logger is used to log the messages. means that we are going to log the
    // messages in the console
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    // constructor for the player controller
    @Autowired // autowired is used to inject the player service into the controller
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // get all players
    @GetMapping
    public List<Player> getPlayers(
            // request param is used to get the parameters from the request
            @RequestParam(required = false) String team,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String nation) {

        // log the request in the console
        logger.info("Received request for players");
        if (team != null && position != null) {
            return playerService.getPlayersByTeamAndPosition(team, position);// return the players by team and position
        } else if (team != null) {
            return playerService.getPlayersFromTeam(team);
        } else if (name != null) {
            return playerService.getPlayersByName(name);
        } else if (position != null) {
            return playerService.getPlayersByPos(position);
        } else if (nation != null) {
            return playerService.getPlayersByNation(nation);
        } else {
            return playerService.getPlayers();
        }
    }

    // add a player
    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.addPlayer(player);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    // update a player
    @PutMapping
    public ResponseEntity<Player> updatePlayer(@RequestBody Player updatedPlayer) {
        Player resultPlayer = playerService.updatePlayer(updatedPlayer);
        if (resultPlayer != null) {
            return new ResponseEntity<>(resultPlayer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete a player
    @DeleteMapping("/{playerName}")
    // path variable is used to get the player name from the request
    public ResponseEntity<String> deletePlayer(@PathVariable String playerName) {
        playerService.deletePlayer(playerName);
        return new ResponseEntity<>("Player deleted successfully", HttpStatus.OK);
    }

    // test the api
    @GetMapping("/test")
    public String test() {
        return "API is working";
    }
}
