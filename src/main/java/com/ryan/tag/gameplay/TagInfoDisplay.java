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
import org.jetbrains.annotations.Nullable;

public class TagInfoDisplay {
    
    public static Team itTeam;
    public static Team notItTeam;
    
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
        Bukkit.getScheduler().runTaskTimer(Tag.getPlugin(), () -> Game.getWorld().sendActionBar(Component.text(ChatColor.DARK_RED + "" + ChatColor.BOLD + Game.getItPlayer().getName() + " is it")), 0, 5);
    }
    
    public static void setPlayerTeams(Player tagged, @Nullable Player attacker) {
        notItTeam.removeEntry(tagged.getName());
        itTeam.addEntry(tagged.getName());
        
        if (attacker != null) {
            itTeam.removeEntry(attacker.getName());
            notItTeam.addEntry(attacker.getName());
        } else {
            for (Player player : Game.getWorld().getPlayers()) {
                if (!player.getName().equals(tagged.getName())) {
                    notItTeam.addEntry(player.getName());
                }
            }
        }
    }
    
    public static void clearTeams() {
        for (String entry : itTeam.getEntries()) {
            itTeam.removeEntry(entry);
        }
        for (String entry : notItTeam.getEntries()) {
            notItTeam.removeEntry(entry);
        }
    }
    
    public static void createTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        itTeam = scoreboard.getTeam("it");
        if (itTeam == null) {
            itTeam = scoreboard.registerNewTeam("it");
        }
        itTeam.color(NamedTextColor.DARK_RED);
        
        notItTeam = scoreboard.getTeam("notIt");
        if (notItTeam == null) {
            notItTeam = scoreboard.registerNewTeam("notIt");
        }
        notItTeam.color(NamedTextColor.DARK_BLUE);
    }
    
    public static void deleteTeams() {
        itTeam.unregister();
        notItTeam.unregister();
    }
    
    private static float getElapsedTimePercentage(long currentTime, long startTime, float endTime) {
        float elapsedTime = (float) (currentTime - startTime);
        return elapsedTime / endTime;
    }
    
    private static long minutesToMilliseconds(double minutes) {
        return (long) (minutes * 60000);
    }
}
