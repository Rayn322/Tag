package com.ryan.tag.gameplay;

import com.ryan.tag.config.TagSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TagInfoDisplay {
    
    public static void updateXPTimer() {
        float percentage = 1 - getElapsedTimePercentage(System.currentTimeMillis(), Timer.startTime, minutesToMilliseconds((float) TagSettings.getTimerLength()));
        if (percentage > 0 && percentage < 1) {
            for (Player player : Game.getWorld().getPlayers()) {
                player.setLevel(0);
                // switch the percentage so the xp bar counts down
                player.setExp(percentage);
            }
        }
    }
    
    public static void sendItPlayerTitle() {;
        Game.getWorld().sendActionBar(Component.text(ChatColor.BLUE + Bukkit.getPlayer(Game.getItPlayer()).getName() + " is it!"));
    }
    
    private static float getElapsedTimePercentage(long currentTime, long startTime, float endTime) {
        float elapsedTime = (float) (currentTime - startTime);
        return elapsedTime / endTime;
    }
    
    private static float minutesToMilliseconds(float minutes) {
        return minutes * 60000;
    }
}
