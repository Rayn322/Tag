package com.ryan.tag.config;

import com.ryan.tag.Tag;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsGUI implements Listener {
    
    public static Inventory settingsGUI;
    
    public static void showGUI(Player player) {
        if (settingsGUI == null) {
            createGUI();
        } else {
            updateGUI();
        }
        
        player.openInventory(settingsGUI);
    }
    
    private static void createGUI() {
        Inventory inventory = Bukkit.getServer().createInventory(null, 45, Component.text("Tag Settings"));
        
        ItemStack clock = new ItemStack(Material.CLOCK);
        ItemMeta clockMeta = clock.getItemMeta();
        clockMeta.displayName(Component.text(ChatColor.YELLOW + "" + ChatColor.BOLD + "Game Length"));
        clock.setItemMeta(clockMeta);
        inventory.setItem(10, clock);
        
        ItemStack beacon = new ItemStack(Material.BEACON);
        ItemMeta beaconMeta = beacon.getItemMeta();
        beaconMeta.displayName(Component.text(ChatColor.YELLOW + "" + ChatColor.BOLD + "Location"));
        beacon.setItemMeta(beaconMeta);
        inventory.setItem(19, beacon);
        
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.displayName(Component.text(ChatColor.YELLOW + "" + ChatColor.BOLD + "Border Size"));
        barrier.setItemMeta(barrierMeta);
        inventory.setItem(28, barrier);
        
        createButton(inventory, 13, Material.LIME_STAINED_GLASS_PANE, "Enter value", ChatColor.YELLOW);
        createButton(inventory, 14, Material.ORANGE_STAINED_GLASS_PANE, "5 Minutes", ChatColor.YELLOW);
        createButton(inventory, 15, Material.RED_STAINED_GLASS_PANE, "Infinite", ChatColor.YELLOW);
        
        createButton(inventory, 22, Material.LIME_STAINED_GLASS_PANE, "Enter value", ChatColor.YELLOW);
        createButton(inventory, 23, Material.ORANGE_STAINED_GLASS_PANE, "Current Location", ChatColor.YELLOW);
        createButton(inventory, 24, Material.RED_STAINED_GLASS_PANE, "Random", ChatColor.YELLOW);
        
        createButton(inventory, 31, Material.LIME_STAINED_GLASS_PANE, "Enter value", ChatColor.YELLOW);
        createButton(inventory, 32, Material.ORANGE_STAINED_GLASS_PANE, "50 Blocks", ChatColor.YELLOW);
        createButton(inventory, 33, Material.RED_STAINED_GLASS_PANE, "None", ChatColor.YELLOW);
        
        createLengthButton(inventory);
        createLocationButton(inventory);
        createBorderButton(inventory);
        
        settingsGUI = inventory;
    }
    
    private static void createButton(Inventory inventory, int slot, Material item, String text, ChatColor chatColor) {
        ItemStack itemStack = new ItemStack(item);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(chatColor + text));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }
    
    private static void createLengthButton(Inventory inventory) {
        ItemStack itemStack = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(ChatColor.YELLOW + TagSettings.getTimerLengthAsString() + " Minutes"));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(11, itemStack);
    }
    
    private static void createLocationButton(Inventory inventory) {
        ItemStack itemStack = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(ChatColor.YELLOW + "x = " + TagSettings.getSpawnX() + ", z = " + TagSettings.getSpawnZ()));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(20, itemStack);
    }
    
    private static void createBorderButton(Inventory inventory) {
        ItemStack itemStack = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(ChatColor.YELLOW + Integer.toString(TagSettings.getBorderSize()) + " Blocks"));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(29, itemStack);
    }
    
    private static void updateGUI() {
        // TODO: account for random location and infinite timer
        createLengthButton(settingsGUI);
        createLocationButton(settingsGUI);
        createBorderButton(settingsGUI);
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(settingsGUI)) {
            event.setCancelled(true);
            ((Player) event.getWhoClicked()).updateInventory();
            
            if (event.getCurrentItem() == null) {
                Tag.getPlugin().getLogger().info("Clicked on empty slot");
            } else {
                Tag.getPlugin().getLogger().info("Clicked on " + event.getCurrentItem().getItemMeta().displayName());
            }
        }
    }
}
