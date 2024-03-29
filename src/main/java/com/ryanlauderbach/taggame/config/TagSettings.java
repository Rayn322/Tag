package com.ryanlauderbach.taggame.config;

import com.ryanlauderbach.taggame.TagGame;

public class TagSettings {
    
    // TODO: choose gamemode, night vision, who starts as it, nametag visibility
    
    private static double timerLength = 1;
    private static int borderSize = 50;
    private static int spawnX = 0;
    private static int spawnY = -1;
    private static int spawnZ = 0;
    private static boolean randomizeLocation = false;
    private static boolean infiniteGame = false;
    
    public static void readFromConfig() {
        TagGame plugin = TagGame.getPlugin();
        timerLength = plugin.getConfig().getDouble("timerLength");
        borderSize = plugin.getConfig().getInt("borderSize");
        spawnX = plugin.getConfig().getInt("spawnX");
        spawnY = plugin.getConfig().getInt("spawnY");
        spawnZ = plugin.getConfig().getInt("spawnZ");
        randomizeLocation = plugin.getConfig().getBoolean("randomizeLocation");
        infiniteGame = plugin.getConfig().getBoolean("infiniteGame");
    }
    
    public static void writeToConfig() {
        TagGame plugin = TagGame.getPlugin();
        plugin.getConfig().set("timerLength", timerLength);
        plugin.getConfig().set("borderSize", borderSize);
        plugin.getConfig().set("spawnX", spawnX);
        plugin.getConfig().set("spawnY", spawnY);
        plugin.getConfig().set("spawnZ", spawnZ);
        plugin.getConfig().set("randomizeLocation", randomizeLocation);
        plugin.getConfig().set("infiniteGame", infiniteGame);
    }
    
    public static double getTimerLength() {
        return timerLength;
    }
    
    public static void setTimerLength(double timerLength) {
        TagSettings.timerLength = timerLength;
        writeToConfig();
    }
    
    public static String getTimerLengthAsString() {
        if (getTimerLength() % 1 == 0) {
            return String.valueOf((int) getTimerLength());
        }
        return String.valueOf(getTimerLength());
    }
    
    public static int getBorderSize() {
        return borderSize;
    }
    
    public static void setBorderSize(int borderSize) {
        TagSettings.borderSize = borderSize;
        writeToConfig();
    }
    
    public static int getSpawnX() {
        return spawnX;
    }
    
    public static void setSpawnX(int spawnX) {
        TagSettings.spawnX = spawnX;
        writeToConfig();
    }
    
    public static int getSpawnY() {
        return spawnY;
    }
    
    public static void setSpawnY(int spawnY) {
        TagSettings.spawnY = spawnY;
        writeToConfig();
    }
    
    public static int getSpawnZ() {
        return spawnZ;
    }
    
    public static void setSpawnZ(int spawnZ) {
        TagSettings.spawnZ = spawnZ;
        writeToConfig();
    }
    
    public static boolean doesRandomizeLocation() {
        return randomizeLocation;
    }
    
    public static void setRandomizeLocation(boolean randomizeLocation) {
        TagSettings.randomizeLocation = randomizeLocation;
        writeToConfig();
    }
    
    public static boolean isInfiniteGame() {
        return infiniteGame;
    }
    
    public static void setInfiniteGame(boolean infiniteGame) {
        TagSettings.infiniteGame = infiniteGame;
        writeToConfig();
    }
}
