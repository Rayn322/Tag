package com.ryan.tag.setup;

import com.ryan.tag.Tag;
import com.ryan.tag.gameplay.Game;
import com.ryan.tag.gameplay.TeamManager;
import com.ryan.tag.gameplay.Timer;
import com.ryan.tag.listener.ConnectAndDisconnect;
import com.ryan.tag.util.WorldBorderUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PostGame {
    
    public static void cleanup(World world) {
        Game.isPlaying = false;
        WorldBorderUtil.resetBorder(world);
        world.sendActionBar(Component.empty());
        TeamManager.clearTeams();
        ConnectAndDisconnect.disconnectedAsIt = null;
        ConnectAndDisconnect.wasNotIt.clear();
        
        for (Player player : world.getPlayers()) {
            player.getInventory().clear();
            
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
        
        Timer.stopTimer();
        // don't register task when disabling plugin
        if (Tag.getPlugin().isEnabled()) {
            Bukkit.getScheduler().runTaskLater(Tag.getPlugin(), PlayerDataSaver::restoreAllData, 40L);
        } else {
            PlayerDataSaver.restoreAllData();
        }
    }
}
