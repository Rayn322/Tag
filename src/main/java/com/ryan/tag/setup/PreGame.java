package com.ryan.tag.setup;

import com.ryan.tag.config.TagSettings;
import com.ryan.tag.gameplay.Game;
import com.ryan.tag.gameplay.TagInfoDisplay;
import com.ryan.tag.gameplay.Timer;
import com.ryan.tag.util.LocationUtil;
import com.ryan.tag.util.WorldBorderUtil;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Random;

public class PreGame {
    
    public static void setup(World world) {
        Game.isPlaying = true;
        Game.isSpawnProtected = true;
        int spawnX;
        int spawnZ;
        
        if (TagSettings.doesRandomizeLocation()) {
            Random random = new Random();
            spawnX = random.nextInt(10000) - 5000;
            spawnZ = random.nextInt(10000) - 5000;
        } else {
            spawnX = TagSettings.getSpawnX();
            spawnZ = TagSettings.getSpawnZ();
        }
    
        WorldBorderUtil.setBorder(world, spawnX, spawnZ, TagSettings.getBorderSize());
    
        for (Player player : world.getPlayers()) {
            PlayerDataSaver.saveData(player);
            player.teleport(LocationUtil.getSpawnpoint(world, spawnX, spawnZ));
            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(20);
        }
        
        Game.giveItPlayerHelmet(Game.getItPlayer());
        TagInfoDisplay.sendItPlayerTitle();
        TagInfoDisplay.setItPlayerNametag();
        Timer.startTimer(world);
    }
}
