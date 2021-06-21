package com.ryan.tag.setup;

import com.ryan.tag.gameplay.Game;
import com.ryan.tag.gameplay.Timer;
import com.ryan.tag.util.LocationUtil;
import com.ryan.tag.util.WorldBorderUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PreGame {
    
    public static void setup(World world, int length) {
        Game.isPlaying = true;
        WorldBorderUtil.setBorder(world, 0, 0, 50);
    
        for (Player player : world.getPlayers()) {
            player.teleport(LocationUtil.getSpawnpoint(world));
        }
        Timer.startTimer(world, length);
    }
}
