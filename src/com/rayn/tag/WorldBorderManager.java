package com.rayn.tag;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Random;

public class WorldBorderManager {
    
    static Main plugin;
    public WorldBorderManager(Main instance) {
        plugin = instance;
    }
    
    // finds the highest block with the given x and z coordinates.
    // yeah bukkit already has a method for this but i guess i made my own that's probably worse
    public Location findHighestBlock(int x, int z, World world) {
        boolean lookingForBlock = true;
        int i = 256;
        
        while (lookingForBlock) {
            if (world.getBlockAt(x, i, z).getBlockData().getMaterial().isSolid()) {
                i--;
            } else {
                lookingForBlock = false;
                System.out.println("found a " + world.getBlockAt(x, i, z).getBlockData().getAsString() + " block at y = " + i);
                return new Location(world, x + 0.5, i + 1, z + 0.5);
            }
        }
        
        return world.getSpawnLocation();
    }
    
    public Location getTagLocation(World world) {
        Random rand = new Random();
        int x = plugin.config.getInt("coordinates.x");
        int z = plugin.config.getInt("coordinates.z");
        System.out.println("coordinates are " + x + ", " + z);
        
        if (plugin.getConfig().getBoolean("use-random-location")) {
            x = rand.nextInt(5000);
            z = rand.nextInt(5000);
        }
        
        return new Location(world, x, 0, z);
    }
}