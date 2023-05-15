package com.ryanlauderbach.taggame.setup;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class PlayerData {
    
    private final double health;
    private final int food;
    private final int level;
    private final float exp;
    private final Location location;
    private final ItemStack[] inventory;
    private final GameMode gamemode;
    private final Collection<PotionEffect> potionEffects;
    
    public PlayerData(double health, int food, int level, float exp, Location location, ItemStack[] inventory, GameMode gamemode, Collection<PotionEffect> potionEffects) {
        this.health = health;
        this.food = food;
        this.level = level;
        this.exp = exp;
        this.location = location;
        this.inventory = inventory;
        this.gamemode = gamemode;
        this.potionEffects = potionEffects;
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
    
    public Collection<PotionEffect> getPotionEffects() {
        return potionEffects;
    }
}
