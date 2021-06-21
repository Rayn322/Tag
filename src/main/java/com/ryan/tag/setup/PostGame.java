package com.ryan.tag.setup;

import com.ryan.tag.gameplay.Game;
import com.ryan.tag.util.WorldBorderUtil;
import org.bukkit.World;

public class PostGame {
    
    public static void cleanup(World world) {
        Game.isPlaying = false;
        WorldBorderUtil.resetBorder(world);
    }
}
