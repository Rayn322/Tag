package com.rayn.tag;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TagPlayerManager {
    
    static Main plugin;
    public TagPlayerManager(Main instance) {
        plugin = instance;
    }
    
    // creates lists for the players and their locations
    public List<Player> players = new ArrayList<>();
    public List<Location> locations = new ArrayList<>();
    
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
    
    
    
    
}
