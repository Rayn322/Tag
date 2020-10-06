package com.rayn.tag;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TagPlayerManager implements Listener {
    
    static Main plugin;
    protected List<UUID> spectators = new ArrayList<>();
    // creates lists for the players and their locations
    protected List<UUID> players = new ArrayList<UUID>();
    protected List<Location> locations = new ArrayList<>();
    
    public TagPlayerManager(Main instance) {
        plugin = instance;
    }
    
    public void saveLocation(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (!players.contains(uuid)) {
            players.add(uuid);
            locations.add(player.getLocation());
            System.out.println("saved " + player.getDisplayName());
        } else {
            System.out.println("already saved location");
        }
    }
    
    public Location getLocation(UUID uuid) {
        // makes sure player has a saved location
        Player player = Bukkit.getPlayer(uuid);
        if (players.contains(uuid)) {
            int i = players.indexOf(uuid);
            Location oldLocation = locations.get(i);
            players.remove(i);
            locations.remove(i);
            return oldLocation;
        }
        
        // returning null probably isn't the best way to do it but I'm not sure how else
        return null;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        if (plugin.isPlayingTag) {
            player.setGameMode(GameMode.SPECTATOR);
            System.out.println(player.getDisplayName() + " is spectating.");
            
            TextComponent message = new TextComponent("You have joined during a game of tag. Click here if you wish to join.");
            message.setColor(net.md_5.bungee.api.ChatColor.BLUE);
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tag joingame"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Join the game!")));
            player.spigot().sendMessage(message);
        }
        
        if (!spectators.contains(player.getUniqueId()) && plugin.isPlayingTag) {
            spectators.add(player.getUniqueId());
            saveLocation(player.getUniqueId());
        }
        
        
    }
}
