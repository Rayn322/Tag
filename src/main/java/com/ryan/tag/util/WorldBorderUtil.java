package com.ryan.tag.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class WorldBorderUtil {
    
    /**
     * Sets the center and size of the WorldBorder
     *
     * @param world  The world in question
     * @param center Location of the new center
     * @param size   The new side length
     */
    public static void setBorder(World world, Location center, double size) {
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(center);
        worldBorder.setSize(size);
    }
    
    /**
     * Sets the center and size of the WorldBorder
     *
     * @param world   The world in question
     * @param centerX The x-coordinate of the new center
     * @param centerZ The x-coordinate of the new center
     * @param size    The new side length
     */
    public static void setBorder(World world, int centerX, int centerZ, double size) {
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(centerX, centerZ);
        worldBorder.setSize(size);
    }
    
    /**
     * Resets the WorldBorder
     *
     * @param world The world in question
     */
    public static void resetBorder(World world) {
        world.getWorldBorder().reset();
    }
    
    /**
     * Gets the {@link Location} of the center of the WorldBorder
     *
     * @param world The world in question
     * @return The {@link Location} of the center
     */
    public static Location getCenter(World world) {
        return world.getWorldBorder().getCenter();
    }
    
}