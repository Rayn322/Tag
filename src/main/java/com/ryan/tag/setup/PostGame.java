package com.ryan.tag.setup;

import com.ryan.tag.gameplay.Game;
import com.ryan.tag.gameplay.TagInfoDisplay;
import com.ryan.tag.gameplay.Timer;
import com.ryan.tag.util.WorldBorderUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PostGame {
    
    public static void cleanup(World world) {
        Game.isPlaying = false;
        WorldBorderUtil.resetBorder(world);
        world.sendActionBar(Component.empty());
        TagInfoDisplay.clearTeam();
        
        for (Player player : world.getPlayers()) {
            player.getInventory().clear();
            
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
        Timer.stopTimer();
        PlayerDataSaver.restoreAllData();
    }
}
