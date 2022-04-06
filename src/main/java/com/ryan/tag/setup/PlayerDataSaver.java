package com.ryan.tag.setup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataSaver {
    
    private static final HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();
    
    public static void saveData(Player player) {
        PlayerData playerData = new PlayerData(player.getHealth(), player.getFoodLevel(), player.getLevel(), player.getExp(), player.getLocation(), player.getInventory().getContents(), player.getGameMode(), player.getActivePotionEffects());
        playerDataMap.put(player.getUniqueId(), playerData);
    }
    
    public static PlayerData getData(UUID uuid) {
        return playerDataMap.get(uuid);
    }
    
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
            player.setGameMode(playerData.getGameMode());
            player.addPotionEffects(playerData.getPotionEffects());
        }
    }
    
    public static void restoreAllData() {
        for (UUID uuid : playerDataMap.keySet()) {
            restoreData(uuid);
        }
        playerDataMap.clear();
    }
}
