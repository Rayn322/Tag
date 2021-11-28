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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PreGame {
    
    public static void setup(World world) {
        Game.isPlaying = true;
        Game.isSpawnProtected = true;
        int spawnX;
        int spawnY;
        int spawnZ;
        
        if (TagSettings.doesRandomizeLocation()) {
            Random random = new Random();
            spawnX = random.nextInt(10000) - 5000;
            spawnZ = random.nextInt(10000) - 5000;
        } else {
            spawnX = TagSettings.getSpawnX();
            spawnY = TagSettings.getSpawnY();
            spawnZ = TagSettings.getSpawnZ();
        }
        
        WorldBorderUtil.setBorder(world, spawnX, spawnZ, TagSettings.getBorderSize());
        
        for (Player player : world.getPlayers()) {
            PlayerDataSaver.saveData(player);
            player.teleport(LocationUtil.getSpawnpoint());
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 0, true, false));
            player.setLevel((int) (TagSettings.getTimerLength() * 60));
            player.setExp(1);
        }
        
        Game.giveItPlayerHelmet(Game.getItPlayer());
        TagInfoDisplay.sendItPlayerTitle();
        TagInfoDisplay.setPlayerTeams(Game.getItPlayer(), null);
        Timer.startTimer(world);
    }
}
