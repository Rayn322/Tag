package com.ryan.tag.listener;

import com.ryan.tag.gameplay.Game;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TagPlayer implements Listener {
    
    @EventHandler
    private void onPlayerHitPlayer(EntityDamageByEntityEvent event) {
        if (!Game.isPlaying) return;
        if (Game.isSpawnProtected) {
            event.setCancelled(true);
        } else if (playerIsTaggingPlayer(event.getEntity(), event.getDamager())) {
            Game.handlePlayerTag((Player) event.getEntity(), (Player) event.getDamager());
        }
        event.setDamage(0);
    }
    
    private boolean playerIsTaggingPlayer(Entity tagged, Entity attacker) {
        return tagged instanceof Player && attacker instanceof Player && attacker.getUniqueId() == Game.getItPlayer();
    }
}
