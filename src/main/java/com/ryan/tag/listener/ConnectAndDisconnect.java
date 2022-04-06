package com.ryan.tag.listener;

import com.ryan.tag.gameplay.Game;
import com.ryan.tag.gameplay.TeamManager;
import com.ryan.tag.gameplay.Timer;
import com.ryan.tag.setup.PlayerDataSaver;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.UUID;

public class ConnectAndDisconnect implements Listener {
    
    public static Player disconnectedAsIt;
    public static BukkitTask task;
    public static ArrayList<UUID> wasNotIt = new ArrayList<>();
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!Game.isPlaying) {
            return;
        }
        
        if (disconnectedAsIt != null && disconnectedAsIt.getName().equals(event.getPlayer().getName())) {
            if (event.getPlayer().getName().equals(Game.getItPlayer().getName())) {
                // The player who was it reconnected before the countdown ended (same player as Game.getItPlayer())
                task.cancel();
                disconnectedAsIt = null;
            } else {
                // The player who was it reconnected after the countdown ended (different player than Game.getItPlayer())
                Game.handlePlayerTag(event.getPlayer(), Game.getItPlayer());
                disconnectedAsIt = null;
                Game.getWorld().sendMessage(Component.text(ChatColor.YELLOW + "" + ChatColor.BOLD + event.getPlayer().getName() + ChatColor.RESET + "" + ChatColor.GREEN + " has reconnected and is now it."));
            }
            
        } else if (!TeamManager.isOnTagTeam(event.getPlayer())) {
            Player player = event.getPlayer();
            PlayerDataSaver.saveData(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(Game.getItPlayer().getLocation());
            
        } else if (wasNotIt.contains(event.getPlayer().getUniqueId())) {
            TeamManager.notItTeam.addEntry(event.getPlayer().getName());
            wasNotIt.remove(event.getPlayer().getUniqueId());
        }
    }
    
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if (!Game.isPlaying) {
            return;
        }
        
        if (Game.getItPlayer() != null && event.getPlayer().getName().equals(Game.getItPlayer().getName())) {
            task = Timer.scheduleChangeItPlayer();
            disconnectedAsIt = event.getPlayer();
            event.getPlayer().getWorld().sendMessage(Component.text(ChatColor.RED + "The player who was it disconnected. If they don't reconnect, a random player will become it in 10 seconds."));
        } else if (TeamManager.isOnNotItTeam(event.getPlayer())) {
            TeamManager.notItTeam.removeEntry(event.getPlayer().getName());
            wasNotIt.add(event.getPlayer().getUniqueId());
            System.out.println("Removing " + event.getPlayer().getName() + " from not it team");
        }
    }
}
