package com.ryan.tag.setup;

import com.ryan.tag.config.TagSettings;
import com.ryan.tag.gameplay.Game;
import com.ryan.tag.gameplay.Timer;
import com.ryan.tag.util.LocationUtil;
import com.ryan.tag.util.WorldBorderUtil;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PreGame {
    
    public static void setup(World world) {
        Game.isPlaying = true;
        Game.isSpawnProtected = true;
        WorldBorderUtil.setBorder(world, TagSettings.spawnX, TagSettings.spawnZ, TagSettings.borderLength);
    
        // TODO: save location and possibly inventory. maybe even potion effects if you want to be fancy.
        for (Player player : world.getPlayers()) {
            player.teleport(LocationUtil.getSpawnpoint(world));
            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);
        }
        
        Timer.startTimer(world);
    }
}
