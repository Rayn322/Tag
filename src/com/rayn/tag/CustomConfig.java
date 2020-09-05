package com.rayn.tag;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    private static File file;
    private static FileConfiguration customFile;
    
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Tag").getDataFolder(), "config.yml");
        
        // creates file
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("[Tag] Unable to create config.yml");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
        
    }
    
    public static FileConfiguration getConfig() {
        return customFile;
    }
    
    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("[Tag] Could not save file");
        }
    }
    
    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
