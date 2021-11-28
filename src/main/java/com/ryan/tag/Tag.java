package com.ryan.tag;

import com.ryan.tag.bstats.Metrics;
import com.ryan.tag.command.TagCommand;
import com.ryan.tag.config.SettingsGUI;
import com.ryan.tag.config.TagSettings;
import com.ryan.tag.gameplay.Game;
import com.ryan.tag.gameplay.TagInfoDisplay;
import com.ryan.tag.listener.PreventDamage;
import com.ryan.tag.listener.TagPlayer;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tag extends JavaPlugin {
    
    private static Tag plugin;
    
    public static Tag getPlugin() {
        return plugin;
    }
    
    @Override
    public void onEnable() {
        plugin = this;
        new Metrics(this, 8787);
        registerEvents();
        registerCommands();
        
        getConfig().options().copyDefaults(true);
        saveConfig();
        TagSettings.readFromConfig();
        TagInfoDisplay.createTeams();
    }
    
    @Override
    public void onDisable() {
        if (Game.isPlaying) {
            Game.stop();
            getLogger().info(ChatColor.RED + "Stopping game because the server has stopped.");
        }
        TagInfoDisplay.deleteTeams();
        TagSettings.writeToConfig();
        saveConfig();
    }
    
    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PreventDamage(), this);
        pluginManager.registerEvents(new TagPlayer(), this);
        pluginManager.registerEvents(new SettingsGUI(), this);
    }
    
    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        TagCommand tagCommand = new TagCommand();
        getCommand("tag").setExecutor(tagCommand);
    }
}
