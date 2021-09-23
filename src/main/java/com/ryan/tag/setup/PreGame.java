package com.ryan.tag.setup;

import com.ryan.tag.gameplay.Game;
import com.ryan.tag.gameplay.Timer;
import com.ryan.tag.util.LocationUtil;
import com.ryan.tag.util.WorldBorderUtil;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PreGame {
    
    public static void setup(World world, int length) {
        Game.isPlaying = true;
        WorldBorderUtil.setBorder(world, 0, 0, 50);
    
        // TODO: save location and possibly inventory. maybe even potion effects if you want to be fancy.
        for (Player player : world.getPlayers()) {
            player.teleport(LocationUtil.getSpawnpoint(world));
            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);
        }
        
        Timer.startTimer(world, length);
    }
}
