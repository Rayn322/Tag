package com.rayn.tag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class Timer implements Listener {
    
    static Main plugin;
    public Timer(Main instance) {
        plugin = instance;
    }
    
    public static void tagTimer(Long length) {
        
        // converts minutes to ticks
        length = length * 1200;
        
        // countdown
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "30 seconds left."), length - 600);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "15 seconds left."), length - 300);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "10 seconds left."), length - 200);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "5 seconds left."), length - 100);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "4 seconds left."), length - 80);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "3 seconds left."), length - 60);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "2 seconds left."), length - 40);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "1 seconds left."), length - 20);
    
        // ends tag
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + plugin.getItPlayer().getDisplayName()
                + ChatColor.GREEN + " was it at the end!");
            plugin.stopTag();
        }, length);
    }
    
    // turns on spawn protection for 3 seconds
    public static void spawnProtectionTimer() {
        plugin.isSpawnProtected = true;
        // wait 3 seconds and then turn it off
        Bukkit.getScheduler().runTaskLater(plugin, () -> plugin.isSpawnProtected = false, 100L);
        
        // countdown
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "5"), 0L);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "4"), 20L);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "3"), 40L);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "2"), 60L);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "1"), 80L);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "Go!"), 100L);
    }
    
}
