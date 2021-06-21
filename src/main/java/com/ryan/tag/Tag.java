package com.ryan.tag;

import com.ryan.tag.bstats.Metrics;
import com.ryan.tag.listener.PreventDamage;
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
    }
    
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PreventDamage(), this);
    }
}
