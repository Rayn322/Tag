package com.ryan.tag.gameplay;

import com.ryan.tag.Tag;
import com.ryan.tag.config.TagSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Timer {
    
    public static void startTimer(World world) {
        // convert minutes to ticks
        int length = TagSettings.timerLength * 1200;
        
        // countdown
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> sendCountdown(world, "3", 0.5f), 40L);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> sendCountdown(world, "2", 0.5f), 60L);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> sendCountdown(world, "1", 0.5f), 80L);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> {
            sendCountdown(world, "Go!", 1f);
            Game.isSpawnProtected = false;
            scheduleGameEnd(length);
        }, 100L);
    }
    
    private static void scheduleGameEnd(int length) {
        // countdown
        
        // TODO: simplify this
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "30 seconds left."), length - 600);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "15 seconds left."), length - 300);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "10 seconds left."), length - 200);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "5 seconds left."), length - 100);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "4 seconds left."), length - 80);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "3 seconds left."), length - 60);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "2 seconds left."), length - 40);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "1 second left."), length - 20);
        
        // ends tag
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> {
            Player player = Bukkit.getPlayer(Game.getItPlayer());
            
            if (player != null) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + player.getName() + ChatColor.RESET + "" + ChatColor.GREEN + " was it at the end!");
            } else {
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "Error getting player who was it.");
            }
            
            Game.stop();
        }, length);
    }
    
    private static void sendCountdown(World world, String message, float pitch) {
        for (Player player : world.getPlayers()) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, pitch);
            player.sendTitle(ChatColor.GREEN + message, null, 10, 40, 20);
        }
    }
}
