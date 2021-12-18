package com.ryan.tag.listener;

import com.ryan.tag.gameplay.Game;
import com.ryan.tag.setup.PlayerDataSaver;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectAndDisconnect implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Game.isPlaying && Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(event.getPlayer().getName()) == null) {
            Player player = event.getPlayer();
            PlayerDataSaver.saveData(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(Game.getItPlayer().getLocation());
        }
    }
}
