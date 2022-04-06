package com.ryan.tag.gameplay;

import com.ryan.tag.config.TagSettings;
import com.ryan.tag.setup.PostGame;
import com.ryan.tag.setup.PreGame;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.UUID;

public class Game {
    
    public static boolean isPlaying = false;
    public static boolean isSpawnProtected = false;
    // using UUID to prevent issues if players rejoin
    private static UUID itPlayer;
    private static World world;
    
    public static Player getItPlayer() {
        return Bukkit.getPlayer(itPlayer);
    }
    
    public static UUID getItPlayerUUID() {
        return itPlayer;
    }
    
    public static void setItPlayer(UUID itPlayer) {
        Game.itPlayer = itPlayer;
    }
    
    public static void setItPlayer(Player itPlayer) {
        Game.itPlayer = itPlayer.getUniqueId();
    }
    
    public static World getWorld() {
        return world;
    }
    
    private static void setWorld(World world) {
        Game.world = world;
    }
    
    public static void start(Player itPlayer) {
        setWorld(itPlayer.getWorld());
        if (TagSettings.isInfiniteGame()) {
            world.sendMessage(Component.text(ChatColor.GREEN + "Starting an infinite game of tag!"));
        } else {
            world.sendMessage(Component.text(ChatColor.GREEN + "Starting a " + ChatColor.YELLOW + TagSettings.getTimerLengthAsString() + ChatColor.GREEN + " minute game of tag!"));
        }
        world.sendMessage(Component.text(ChatColor.YELLOW + "" + ChatColor.BOLD + itPlayer.getName() + ChatColor.RESET + "" + ChatColor.GREEN + " will start as it!"));
        setItPlayer(itPlayer.getUniqueId());
        PreGame.setup(itPlayer.getWorld());
    }
    
    public static void stop() {
        Player player = Game.getItPlayer();
        
        if (player != null) {
            world.sendMessage(Component.text(ChatColor.YELLOW + "" + ChatColor.BOLD + player.getName() + ChatColor.RESET + "" + ChatColor.GREEN + " was it at the end!"));
        } else {
            world.sendMessage(Component.text(ChatColor.RED + "Error getting player who was it."));
        }
        
        PostGame.cleanup(world);
    }
    
    public static void handlePlayerTag(Player tagged, @Nullable Player attacker) {
        setItPlayer(tagged);
        
        // TODO: experiment with new sounds; different sounds for different players
        for (Player player : world.getPlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
        }
        
        if (attacker != null) {
            attacker.removePotionEffect(PotionEffectType.SPEED);
        }
        
        TeamManager.setPlayerTeams(tagged, attacker);
        world.sendMessage(Component.text(ChatColor.YELLOW + "" + ChatColor.BOLD + tagged.getName() + ChatColor.RESET + "" + ChatColor.GREEN + " is now it!"));
        tagged.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false));
    }
}
