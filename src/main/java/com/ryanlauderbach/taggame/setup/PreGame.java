package com.ryanlauderbach.taggame.setup;

import com.ryanlauderbach.taggame.config.TagSettings;
import com.ryanlauderbach.taggame.gameplay.Game;
import com.ryanlauderbach.taggame.gameplay.TagInfoDisplay;
import com.ryanlauderbach.taggame.gameplay.TeamManager;
import com.ryanlauderbach.taggame.gameplay.Timer;
import com.ryanlauderbach.taggame.util.WorldBorderUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
        int spawnY = TagSettings.getSpawnY();
        int spawnZ;
    
        if (TagSettings.doesRandomizeLocation()) {
            Random random = new Random();
            spawnX = random.nextInt(10000) - 5000;
            spawnZ = random.nextInt(10000) - 5000;
        } else {
            spawnX = TagSettings.getSpawnX();
            spawnZ = TagSettings.getSpawnZ();
        }
        
        if (TagSettings.getBorderSize() != -1) {
            WorldBorderUtil.setBorder(world, spawnX, spawnZ, TagSettings.getBorderSize());
        }
        
        for (Player player : world.getPlayers()) {
            PlayerDataSaver.saveData(player);
            player.teleport(getSpawnpoint(spawnX, spawnY, spawnZ));
            player.getInventory().clear();
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 0, true, false));
            player.setLevel((int) (TagSettings.getTimerLength() * 60));
            player.setExp(1);
        }
        
        Game.getItPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false));
        TagInfoDisplay.sendItPlayerTitle();
        TeamManager.setPlayerTeams(Game.getItPlayer(), null);
        Timer.startTimer(world);
    }
    
    private static Location getSpawnpoint(int spawnX, int spawnY, int spawnZ) {
        World world = Game.getWorld();
        Location location;
        if (spawnY == -1) {
            location = world.getHighestBlockAt(spawnX, spawnZ).getLocation();
            location.add(0.5, 1, 0.5);
        } else {
            location = new Location(world, spawnX, spawnY, spawnZ);
            location.add(0.5, 0, 0.5);
        }
        
        return location;
    }
}
