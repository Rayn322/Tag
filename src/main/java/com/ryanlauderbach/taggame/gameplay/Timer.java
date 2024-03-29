package com.ryanlauderbach.taggame.gameplay;

import com.ryanlauderbach.taggame.TagGame;
import com.ryanlauderbach.taggame.config.TagSettings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.Random;

public class Timer {
    
    public static long startTime;
    public static int timeLeft;
    
    public static void startTimer(World world) {
        // countdown
        Bukkit.getScheduler().runTaskLater(TagGame.getPlugin(), () -> sendCountdown(world, "3", 0.5f), 40L);
        Bukkit.getScheduler().runTaskLater(TagGame.getPlugin(), () -> sendCountdown(world, "2", 0.5f), 60L);
        Bukkit.getScheduler().runTaskLater(TagGame.getPlugin(), () -> sendCountdown(world, "1", 0.5f), 80L);
        Bukkit.getScheduler().runTaskLater(TagGame.getPlugin(), () -> {
            sendCountdown(world, "Go!", 1f);
            Game.isSpawnProtected = false;
            if (!TagSettings.isInfiniteGame()) {
                // convert minutes to ticks
                int length = (int) (TagSettings.getTimerLength() * 1200);
                startTime = System.currentTimeMillis();
                timeLeft = (int) (TagSettings.getTimerLength() * 60);
                scheduleGameEnd(length);
            }
        }, 100L);
    }
    
    public static void stopTimer() {
        Bukkit.getScheduler().cancelTasks(TagGame.getPlugin());
    }
    
    public static BukkitTask scheduleChangeItPlayer() {
        // TODO: make separate method to get random player
        return Bukkit.getScheduler().runTaskLater(TagGame.getPlugin(), () -> {
            if (!Game.isPlaying) return;
            
            Random random = new Random();
            String[] entries = TeamManager.notItTeam.getEntries().toArray(new String[0]);
            if (entries.length == 0) {
                Game.getWorld().sendMessage(Component.text(ChatColor.RED + "Stopping the game because no players are left."));
                TagGame.getPlugin().getLogger().info(ChatColor.RED + "Stopping the game because no players are left.");
                Game.stop();
                return;
            }
            
            String entry = entries[random.nextInt(entries.length)];
            Player player = Bukkit.getPlayer(entry);
            if (player != null) {
                Game.handlePlayerTag(player, null);
            }
        }, 200L);
    }
    
    private static void scheduleGameEnd(int length) {
        // TODO: send message every minute until this starts
        scheduleCountdownMessage(60);
        scheduleCountdownMessage(30);
        scheduleCountdownMessage(15);
        scheduleCountdownMessage(10);
        scheduleCountdownMessage(5);
        scheduleCountdownMessage(4);
        scheduleCountdownMessage(3);
        scheduleCountdownMessage(2);
        scheduleCountdownMessage(1);
        
        Bukkit.getScheduler().runTaskLater(TagGame.getPlugin(), Game::stop, length);
        Bukkit.getScheduler().runTaskTimer(TagGame.getPlugin(), TagInfoDisplay::updateXPTimer, 0, 10);
        Bukkit.getScheduler().runTaskTimer(TagGame.getPlugin(), () -> {
            TagInfoDisplay.updateXPLevel(timeLeft);
            timeLeft--;
        }, 0, 20);
    }
    
    private static void sendCountdown(World world, String message, float pitch) {
        // in case game is stopped during initial countdown
        if (!Game.isPlaying) return;
        
        Title title = Title.title(Component.text(ChatColor.GREEN + message), Component.empty(), Title.Times.times(Duration.ofMillis(250), Duration.ofSeconds(1), Duration.ofMillis(250)));
        
        for (Player player : world.getPlayers()) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, pitch);
            player.showTitle(title);
        }
    }
    
    private static void scheduleCountdownMessage(int secondsBeforeEnd) {
        Bukkit.getScheduler().runTaskLater(TagGame.getPlugin(), () -> {
            if (secondsBeforeEnd == 1) {
                Bukkit.getServer().broadcast(Component.text(ChatColor.GREEN + "" + secondsBeforeEnd + " second left."));
            } else {
                Bukkit.getServer().broadcast(Component.text(ChatColor.GREEN + "" + secondsBeforeEnd + " seconds left."));
            }
        }, (long) (TagSettings.getTimerLength() * 1200 - (secondsBeforeEnd * 20L)));
    }
}
