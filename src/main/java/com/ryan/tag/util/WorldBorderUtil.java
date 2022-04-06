package com.ryan.tag.util;

import org.bukkit.World;
import org.bukkit.WorldBorder;

public class WorldBorderUtil {
    
    /**
     * Sets the center and size of the WorldBorder.
     *
     * @param world   The world in question.
     * @param centerX The x-coordinate of the new center.
     * @param centerZ The x-coordinate of the new center.
     * @param size    The new side length.
     */
    public static void setBorder(World world, int centerX, int centerZ, double size) {
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(centerX + 0.5, centerZ + 0.5);
        worldBorder.setSize(size);
    }
    
    /**
     * Resets the WorldBorder.
     *
     * @param world The world in question.
     */
    public static void resetBorder(World world) {
        world.getWorldBorder().reset();
    }
}
