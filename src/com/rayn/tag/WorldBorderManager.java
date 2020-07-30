package com.rayn.tag;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

public class WorldBorderManager {
    
    static Main plugin;
    public WorldBorderManager(Main instance) {
        plugin = instance;
    }
    
    // finds the highest block with the given x and z coordinates.
    public Location findHighestBlock(int x, int z, World world) {
        boolean lookingForBlock = true;
        int i = 256;
        
        while (lookingForBlock) {
            if (world.getBlockAt(x, i, z).getBlockData().getMaterial().equals(Material.VOID_AIR)
                    || world.getBlockAt(x, i, z).getBlockData().getMaterial().equals(Material.AIR)) {
                i--;
            } else {
                lookingForBlock = false;
                System.out.println("found a " + world.getBlockAt(x, i, z).getBlockData().getAsString() + " block at y = " + i);
                Location top = world.getSpawnLocation();
                top.setX(x + 0.5);
                top.setY(i + 1);
                top.setZ(z + 0.5);
                return top;
            }
        }
        
        return world.getSpawnLocation();
    }
    
    public Location getRandomLocation(World world) {
        Random rand = new Random();
        
        int x = rand.nextInt(5000);
        int z = rand.nextInt(5000);
        
        Location location = world.getSpawnLocation();
        location.setX(x);
        location.setY(0);
        location.setZ(z);
        
        return location;
    }
}