package com.ryan.tag.util;

import com.ryan.tag.Tag;
import com.ryan.tag.config.TagSettings;
import com.ryan.tag.gameplay.Game;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {
    
    public static Location getSpawnpoint() {
        World world = Game.getWorld();
        Tag.getPlugin().getLogger().info("Spawnpoint: " + TagSettings.getSpawnX() + " " + TagSettings.getSpawnZ());
        
        Location location;
        if (TagSettings.getSpawnY() == -1) {
            location = world.getHighestBlockAt(TagSettings.getSpawnX(), TagSettings.getSpawnZ()).getLocation();
            location.add(0.5, 1, 0.5);
        } else {
            location = new Location(world, TagSettings.getSpawnX(), TagSettings.getSpawnY(), TagSettings.getSpawnZ());
            location.add(0.5, 0, 0.5);
        }
        
        Tag.getPlugin().getLogger().info("Teleporting to: " + location.getX() + " " + location.getY() + " " + location.getZ());
        return location;
    }
}
