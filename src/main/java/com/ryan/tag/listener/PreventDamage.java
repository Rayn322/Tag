package com.ryan.tag.listener;

import com.ryan.tag.gameplay.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PreventDamage implements Listener {
    
    @EventHandler
    private void onHungerLoss(FoodLevelChangeEvent event) {
        if (Game.isPlaying) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    private void onPlayerFallDamage(EntityDamageEvent event) {
        if (Game.isPlaying && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }
}
