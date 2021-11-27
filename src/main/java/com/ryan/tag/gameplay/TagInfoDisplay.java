package com.ryan.tag.gameplay;

import com.ryan.tag.Tag;
import com.ryan.tag.config.TagSettings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TagInfoDisplay {
    
    public static Team team;
    
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
    
    public static void sendItPlayerTitle() {
        Bukkit.getScheduler().runTaskTimer(Tag.getPlugin(), () -> Game.getWorld().sendActionBar(Component.text(ChatColor.DARK_RED + "" + ChatColor.BOLD + Game.getItPlayer().getName() + " is it")), 0, 10);
    }
    
    public static void setItPlayerNametag() {
        clearTeam();
        team.addEntry(Game.getItPlayer().getName());
    }
    
    public static void clearTeam() {
        for (String entry : team.getEntries()) {
            team.removeEntry(entry);
        }
    }
    
    public static void createTeam() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        team = scoreboard.getTeam("it");
        if (team == null) {
            team = scoreboard.registerNewTeam("it");
        }
        team.color(NamedTextColor.DARK_RED);
    }
    
    public static void deleteTeam() {
        team.unregister();
    }
    
    private static float getElapsedTimePercentage(long currentTime, long startTime, float endTime) {
        float elapsedTime = (float) (currentTime - startTime);
        return elapsedTime / endTime;
    }
    
    private static float minutesToMilliseconds(float minutes) {
        return minutes * 60000;
    }
}
