package com.rayn.tag;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class TagPlayerManager implements Listener {
    
    static Main plugin;
    // creates lists for the players and their locations
    private final List<Player> players = new ArrayList<>();
    private final List<Location> locations = new ArrayList<>();
    protected final List<Player> spectators = new ArrayList<>();
    public TagPlayerManager(Main instance) {
        plugin = instance;
    }
    
    public void saveLocation(Player player) {
        players.add(player);
        locations.add(player.getLocation());
    }
    
    public Location getLocation(Player player) {
        // makes sure player has a saved location
        if (players.contains(player)) {
            int i = players.indexOf(player);
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
        if (plugin.isPlayingTag) {
            Player player = (Player) event.getPlayer();
            
            saveLocation(player);
            player.teleport(plugin.highestBlock);
            player.setGameMode(GameMode.SPECTATOR);
            spectators.add(player);
            System.out.println(player.getDisplayName() + " is spectating.");
    
            TextComponent message = new TextComponent("You have joined during a game of tag. Click here if you wish to join.");
            message.setColor(net.md_5.bungee.api.ChatColor.BLUE);
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tag joingame"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Join the game!")));
            player.spigot().sendMessage(message);
        }
    }
    
    
}
