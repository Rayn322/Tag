package com.ryan.tag.util;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {
    
    public static Location getSpawnpoint(World world, int spawnX, int spawnZ) {
        Location location = world.getHighestBlockAt(spawnX, spawnZ).getLocation();
        location.setY(location.getY() + 1);
        location.setX(location.getX() + 0.5);
        location.setZ(location.getZ() + 0.5);
        System.out.println("Spawnpoint: " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
        return location;
    }
}
