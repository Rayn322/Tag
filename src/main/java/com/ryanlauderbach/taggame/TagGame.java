package com.ryanlauderbach.taggame;

import com.ryanlauderbach.taggame.bstats.Metrics;
import com.ryanlauderbach.taggame.command.TagCommand;
import com.ryanlauderbach.taggame.config.SettingsGUI;
import com.ryanlauderbach.taggame.config.TagSettings;
import com.ryanlauderbach.taggame.gameplay.Game;
import com.ryanlauderbach.taggame.gameplay.TeamManager;
import com.ryanlauderbach.taggame.listener.ConnectAndDisconnect;
import com.ryanlauderbach.taggame.listener.PreventDamage;
import com.ryanlauderbach.taggame.listener.TagPlayer;
import com.ryanlauderbach.taggame.util.CurseAPI;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class TagGame extends JavaPlugin {
    
    public static String version = "1.0.3";
    private static boolean isPaper = true;
    private static TagGame plugin;
    
    public static TagGame getPlugin() {
        return plugin;
    }
    
    @Override
    public void onEnable() {
        try {
            Class.forName("io.papermc.paper.event.player.AsyncChatEvent");
        } catch (ClassNotFoundException e) {
            getLogger().severe("Paper is not installed! Disabling plugin.");
            getLogger().severe("Please download Paper at https://papermc.io/downloads");
            isPaper = false;
            return;
        }
    
        plugin = this;
        new Metrics(this, 8787);
        registerEvents();
        registerCommands();
        
        getConfig().options().copyDefaults(true);
        saveConfig();
        TagSettings.readFromConfig();
        TeamManager.createTeams();
    
        try {
            CurseAPI.checkForUpdates();
        } catch (IOException e) {
            getLogger().warning("Failed to check for updates!");
        }
    }
    
    @Override
    public void onDisable() {
        if (!isPaper) {
            return;
        }
        
        if (Game.isPlaying) {
            Game.stop();
            getLogger().info(ChatColor.RED + "Stopping game because the server has stopped.");
        }
        TeamManager.deleteTeams();
        TagSettings.writeToConfig();
        saveConfig();
    }
    
    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PreventDamage(), this);
        pluginManager.registerEvents(new TagPlayer(), this);
        pluginManager.registerEvents(new SettingsGUI(), this);
        pluginManager.registerEvents(new ConnectAndDisconnect(), this);
    }
    
    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        TagCommand tagCommand = new TagCommand();
        getCommand("tag-game").setExecutor(tagCommand);
    }
}
