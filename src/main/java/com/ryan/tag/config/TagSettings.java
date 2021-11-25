package com.ryan.tag.config;

import com.ryan.tag.Tag;

public class TagSettings {
    
    public static double timerLength = 1;
    public static int borderLength = 50;
    public static int spawnX = 0;
    public static int spawnZ = 0;
    public static boolean randomizeLocation = false;
    public static boolean infiniteGame = false;
    
    public static void readFromConfig() {
        Tag plugin = Tag.getPlugin();
        timerLength = plugin.getConfig().getDouble("timerLength");
        borderLength = plugin.getConfig().getInt("borderLength");
        spawnX = plugin.getConfig().getInt("spawnX");
        spawnZ = plugin.getConfig().getInt("spawnZ");
        randomizeLocation = plugin.getConfig().getBoolean("randomizeLocation");
        infiniteGame = plugin.getConfig().getBoolean("infiniteGame");
    }
    
    public static void writeToConfig() {
        Tag plugin = Tag.getPlugin();
        plugin.getConfig().set("timerLength", timerLength);
        plugin.getConfig().set("borderLength", borderLength);
        plugin.getConfig().set("spawnX", spawnX);
        plugin.getConfig().set("spawnZ", spawnZ);
        plugin.getConfig().set("randomizeLocation", randomizeLocation);
        plugin.getConfig().set("infiniteGame", infiniteGame);
    }
}
