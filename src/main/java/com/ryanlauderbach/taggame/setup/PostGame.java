package com.ryanlauderbach.taggame.setup;

import com.ryanlauderbach.taggame.TagGame;
import com.ryanlauderbach.taggame.gameplay.Game;
import com.ryanlauderbach.taggame.gameplay.TeamManager;
import com.ryanlauderbach.taggame.gameplay.Timer;
import com.ryanlauderbach.taggame.listener.ConnectAndDisconnect;
import com.ryanlauderbach.taggame.util.WorldBorderUtil;
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
        if (TagGame.getPlugin().isEnabled()) {
            Bukkit.getScheduler().runTaskLater(TagGame.getPlugin(), PlayerDataSaver::restoreAllData, 40L);
        } else {
            PlayerDataSaver.restoreAllData();
        }
    }
}
