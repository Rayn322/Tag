package com.ryanlauderbach.taggame.listener;

import com.ryanlauderbach.taggame.gameplay.Game;
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
		if (Game.isPlaying && event.getEntity() instanceof Player && shouldCancel(event.getCause())) {
			event.setCancelled(true);
		}
	}

	private boolean shouldCancel(EntityDamageEvent.DamageCause cause) {
		return cause != EntityDamageEvent.DamageCause.ENTITY_ATTACK &&
				cause != EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK &&
				cause != EntityDamageEvent.DamageCause.VOID;
	}
}
