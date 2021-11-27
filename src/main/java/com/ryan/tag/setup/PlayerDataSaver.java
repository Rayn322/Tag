package com.ryan.tag.setup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataSaver {
    
    private static final HashMap<UUID, PlayerData> playerLocations = new HashMap<>();
    
    public static void saveData(Player player) {
        PlayerData playerData = new PlayerData(player.getHealth(), player.getFoodLevel(), player.getLevel(), player.getExp(), player.getLocation(), player.getInventory().getContents());
        playerLocations.put(player.getUniqueId(), playerData);
    }
    
    public static PlayerData getData(UUID uuid) {
        return playerLocations.get(uuid);
    }
    
    // TODO: deal with players logging out or logging in during the game
    private static void restoreData(UUID uuid) {
        PlayerData playerData = getData(uuid);
        Player player = Bukkit.getPlayer(uuid);
        
        if (player != null) {
            player.setHealth(playerData.getHealth());
            player.setFoodLevel(playerData.getFood());
            player.setLevel(playerData.getLevel());
            player.setExp(playerData.getExp());
            player.teleport(playerData.getLocation());
            player.getInventory().setContents(playerData.getInventory());
        }
    }
    
    public static void restoreAllData() {
        for (UUID uuid : playerLocations.keySet()) {
            restoreData(uuid);
        }
        playerLocations.clear();
    }
}
