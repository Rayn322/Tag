package com.ryan.tag.listener;

import com.ryan.tag.gameplay.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PreventDamage implements Listener {
    
    // TODO: prevent damage from blocks ex: dripstone
    
    @EventHandler
    private void onHungerLoss(FoodLevelChangeEvent event) {
        if (Game.isPlaying) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    private void onPlayerFallDamage(EntityDamageEvent event) {
        if (Game.isPlaying && event.getEntity() instanceof Player && event.getCause() != EntityDamageEvent.DamageCause.VOID) {
            event.setCancelled(true);
        }
    }
}
