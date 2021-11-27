package com.ryan.tag.setup;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class PlayerData {
    
    private final double health;
    private final int food;
    private final int level;
    private final float exp;
    private final Location location;
    private final ItemStack[] inventory;
    private final GameMode gamemode;
    
    public PlayerData(double health, int food, int level, float exp, Location location, ItemStack[] inventory, GameMode gamemode) {
        this.health = health;
        this.food = food;
        this.level = level;
        this.exp = exp;
        this.location = location;
        this.inventory = inventory;
        this.gamemode = gamemode;
    }
    
    public double getHealth() {
        return health;
    }
    
    public int getFood() {
        return food;
    }
    
    public int getLevel() {
        return level;
    }
    
    public float getExp() {
        return exp;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public ItemStack[] getInventory() {
        return inventory;
    }
    
    public GameMode getGameMode() {
        return gamemode;
    }
}
