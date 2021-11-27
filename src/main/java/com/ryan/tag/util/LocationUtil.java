package com.ryan.tag.util;

import com.ryan.tag.Tag;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {
    
    public static Location getSpawnpoint(World world, int spawnX, int spawnZ) {
        Tag.getPlugin().getLogger().info("Spawnpoint: " + spawnX + " " + spawnZ);
        Location location = world.getHighestBlockAt(spawnX, spawnZ).getLocation();
        location.setY(location.getY() + 1);
        location.setX(location.getX() + 0.5);
        location.setZ(location.getZ() + 0.5);
        Tag.getPlugin().getLogger().info("Highest block: " + location.getY());
        return location;
    }
}
