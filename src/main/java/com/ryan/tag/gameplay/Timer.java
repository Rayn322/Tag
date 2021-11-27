package com.ryan.tag.gameplay;

import com.ryan.tag.Tag;
import com.ryan.tag.config.TagSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Timer {
    
    private static final ArrayList<Integer> tasks = new ArrayList<>();
    
    public static void startTimer(World world) {
        // countdown
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> sendCountdown(world, "3", 0.5f), 40L);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> sendCountdown(world, "2", 0.5f), 60L);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> sendCountdown(world, "1", 0.5f), 80L);
        Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> {
            sendCountdown(world, "Go!", 1f);
            Game.isSpawnProtected = false;
            if (!TagSettings.isInfiniteGame()) {
                // convert minutes to ticks
                int length = (int) (TagSettings.getTimerLength() * 1200);
                scheduleGameEnd(length);
            }
        }, 100L);
    }
    
    public static void stopTimer() {
        for (int id : tasks) {
            Bukkit.getScheduler().cancelTask(id);
        }
    }
    
    private static void scheduleGameEnd(int length) {
        // countdown
        
        // TODO: simplify this
        tasks.add(Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "30 seconds left."), length - 600).getTaskId());
        tasks.add(Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "15 seconds left."), length - 300).getTaskId());
        tasks.add(Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "10 seconds left."), length - 200).getTaskId());
        tasks.add(Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "5 seconds left."), length - 100).getTaskId());
        tasks.add(Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "4 seconds left."), length - 80).getTaskId());
        tasks.add(Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "3 seconds left."), length - 60).getTaskId());
        tasks.add(Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "2 seconds left."), length - 40).getTaskId());
        tasks.add(Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "1 second left."), length - 20).getTaskId());
        
        // ends tag
        tasks.add(Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), () -> {
            Player player = Bukkit.getPlayer(Game.getItPlayer());
            
            if (player != null) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + player.getName() + ChatColor.RESET + "" + ChatColor.GREEN + " was it at the end!");
            } else {
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "Error getting player who was it.");
            }
            
            Game.stop();
        }, length).getTaskId());
    }
    
    private static void sendCountdown(World world, String message, float pitch) {
        // in case game is stopped during initial countdown
        if (!Game.isPlaying) return;
        
        for (Player player : world.getPlayers()) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, pitch);
            player.sendTitle(ChatColor.GREEN + message, null, 10, 40, 20);
        }
    }
}
