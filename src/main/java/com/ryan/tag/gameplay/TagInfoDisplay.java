package com.ryan.tag.gameplay;

import com.ryan.tag.Tag;
import com.ryan.tag.config.TagSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TagInfoDisplay {
    
    public static void updateXPTimer() {
        float percentage = 1 - getElapsedTimePercentage(System.currentTimeMillis(), Timer.startTime, minutesToMilliseconds(TagSettings.getTimerLength()));
        if (percentage > 0 && percentage < 1) {
            for (Player player : Game.getWorld().getPlayers()) {
                player.setExp(percentage);
            }
        }
    }
    
    public static void updateXPLevel(int level) {
        for (Player player : Game.getWorld().getPlayers()) {
            player.setLevel(level);
        }
    }
    
    public static void sendItPlayerTitle() {
        Bukkit.getScheduler().runTaskTimer(Tag.getPlugin(), () -> {
            if (Game.getItPlayer() != null) {
                Game.getWorld().sendActionBar(Component.text(ChatColor.DARK_RED + "" + ChatColor.BOLD + Game.getItPlayer().getName() + " is it"));
            }
        }, 0, 5);
    }
    
    private static float getElapsedTimePercentage(long currentTime, long startTime, float endTime) {
        float elapsedTime = (float) (currentTime - startTime);
        return elapsedTime / endTime;
    }
    
    private static long minutesToMilliseconds(double minutes) {
        return (long) (minutes * 60000);
    }
}
