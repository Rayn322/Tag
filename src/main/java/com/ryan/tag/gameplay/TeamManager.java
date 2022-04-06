package com.ryan.tag.gameplay;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.Nullable;

public class TeamManager {
    
    public static Team itTeam;
    public static Team notItTeam;
    
    public static void setPlayerTeams(Player tagged, @Nullable Player attacker) {
        notItTeam.removeEntry(tagged.getName());
        itTeam.addEntry(tagged.getName());
        
        if (attacker != null) {
            itTeam.removeEntry(attacker.getName());
            notItTeam.addEntry(attacker.getName());
        } else {
            // add all players to notItTeam except for the tagged player
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
        itTeam = scoreboard.getTeam("tag-it");
        notItTeam = scoreboard.getTeam("tag-notIt");
        
        // Delete teams if they already exist to make sure they're cleared
        if (itTeam != null) {
            itTeam.unregister();
        }
        if (notItTeam != null) {
            notItTeam.unregister();
        }
        
        itTeam = scoreboard.registerNewTeam("tag-it");
        notItTeam = scoreboard.registerNewTeam("tag-notIt");
        itTeam.color(NamedTextColor.DARK_RED);
        notItTeam.color(NamedTextColor.DARK_BLUE);
    }
    
    public static void deleteTeams() {
        itTeam.unregister();
        notItTeam.unregister();
    }
    
    /**
     * Checks if a player is on any team.
     * @param player The player to check.
     * @return True if the player is on a team, false otherwise.
     */
    public static boolean isOnTagTeam(Player player) {
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(player.getName());
        return team != null && (team.equals(notItTeam) || team.equals(itTeam));
    }
    
    /**
     * Checks if a player is it.
     * @param player The player to check.
     * @return True if the player is it, false otherwise.
     */
    public static boolean isOnItTeam(Player player) {
        return itTeam.getEntries().contains(player.getName());
    }
    
    /**
     * Checks if a player is not it.
     * @param player The player to check.
     * @return True if the player is not it, false otherwise.
     */
    public static boolean isOnNotItTeam(Player player) {
        return notItTeam.getEntries().contains(player.getName());
    }
}
