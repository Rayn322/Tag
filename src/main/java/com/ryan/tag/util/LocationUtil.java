package com.ryan.tag.util;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {
    
    public static Location getSpawnpoint(World world) {
        Location location = world.getHighestBlockAt(WorldBorderUtil.getCenter(world)).getLocation();
        return new Location(location.getWorld(), location.getX() + 0.5, location.getY() + 1, location.getZ() + 0.5);
    }
}
