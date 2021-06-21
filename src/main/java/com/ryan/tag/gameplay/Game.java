package com.ryan.tag.gameplay;

import com.ryan.tag.setup.PreGame;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.UUID;

public class Game {
    
    public static boolean isPlaying = false;
    public static boolean isSpawnProtected = false;
    // using UUID to prevent issues if players rejoin
    private static UUID itPlayer;
    
    public static UUID getItPlayer() {
        return itPlayer;
    }
    
    public static void setItPlayer(UUID itPlayer) {
        Game.itPlayer = itPlayer;
    }
    
    /**
     * Starts the game of tag.
     *
     * @param itPlayer The player to start as it.
     * @param length   Length in minutes.
     */
    public static void startGame(Player itPlayer, int length) {
        Bukkit.broadcastMessage(ChatColor.GREEN + "Starting a " + ChatColor.YELLOW + length + ChatColor.GREEN + " minute game of tag!");
        setItPlayer(itPlayer.getUniqueId());
        PreGame.setup(itPlayer.getWorld(), length);
    }
    
    public static void stopGame() {
    
    }
    
    // TODO: send chat message and change boss bar
    @SuppressWarnings("ConstantConditions")
    public static void handlePlayerTag(Player tagged, Player attacker) {
        ItemStack itHelmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) itHelmet.getItemMeta();
        meta.setColor(Color.RED);
        itHelmet.setItemMeta(meta);
        
        tagged.getInventory().setHelmet(itHelmet);
        attacker.getInventory().setHelmet(null);
        
        for (Player player : tagged.getWorld().getPlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
        }
    }
}
