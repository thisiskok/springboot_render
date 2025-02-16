package com.euleague.Champions.League.Zone.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//repository for the player entity
@Repository
// JpaRepository is a Spring Data interface for generic CRUD operations on a
// repository for a specific type
public interface PlayerRepository extends JpaRepository<Player, String> {
    // find the name in repository and delete a player by their name
    void deleteByName(String playerName);

    // find a player by their name
    Optional<Player> findByName(String name);
}
