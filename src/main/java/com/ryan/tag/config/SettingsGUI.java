package com.ryan.tag.config;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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
        
        createButton(inventory, 13, Material.LIME_STAINED_GLASS_PANE, "Enter value");
        createButton(inventory, 14, Material.ORANGE_STAINED_GLASS_PANE, "5 Minutes");
        createButton(inventory, 15, Material.RED_STAINED_GLASS_PANE, "Infinite");
        
        createButton(inventory, 22, Material.LIME_STAINED_GLASS_PANE, "Enter value");
        createButton(inventory, 23, Material.ORANGE_STAINED_GLASS_PANE, "Current Location");
        createButton(inventory, 24, Material.RED_STAINED_GLASS_PANE, "Random");
        
        createButton(inventory, 31, Material.LIME_STAINED_GLASS_PANE, "Enter value");
        createButton(inventory, 32, Material.ORANGE_STAINED_GLASS_PANE, "50 Blocks");
        createButton(inventory, 33, Material.RED_STAINED_GLASS_PANE, "None");
        
        createLengthButton(inventory);
        createLocationButton(inventory);
        createBorderButton(inventory);
        
        settingsGUI = inventory;
    }
    
    private static void createButton(Inventory inventory, int slot, Material item, String text) {
        ItemStack itemStack = new ItemStack(item);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(ChatColor.YELLOW + text));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }
    
    private static void createLengthButton(Inventory inventory) {
        ItemStack itemStack = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (TagSettings.isInfiniteGame()) {
            itemMeta.displayName(Component.text(ChatColor.YELLOW + "Infinite"));
        } else if (TagSettings.getTimerLength() == 1) {
            itemMeta.displayName(Component.text(ChatColor.YELLOW + TagSettings.getTimerLengthAsString() + " Minute"));
        } else {
            itemMeta.displayName(Component.text(ChatColor.YELLOW + TagSettings.getTimerLengthAsString() + " Minutes"));
        }
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(11, itemStack);
    }
    
    private static void createLocationButton(Inventory inventory) {
        ItemStack itemStack = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (TagSettings.doesRandomizeLocation()) {
            itemMeta.displayName(Component.text(ChatColor.YELLOW + "Random"));
        } else if (TagSettings.getSpawnY() == -1) {
            itemMeta.displayName(Component.text(ChatColor.YELLOW + "x = " + TagSettings.getSpawnX() + ", z = " + TagSettings.getSpawnZ()));
        } else {
            itemMeta.displayName(Component.text(ChatColor.YELLOW + "x = " + TagSettings.getSpawnX() + ", y = " + TagSettings.getSpawnY() + ", z = " + TagSettings.getSpawnZ()));
        }
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(20, itemStack);
    }
    
    private static void createBorderButton(Inventory inventory) {
        ItemStack itemStack = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (TagSettings.getBorderSize() == -1) {
            itemMeta.displayName(Component.text(ChatColor.YELLOW + "None"));
        } else if (TagSettings.getBorderSize() == 1) {
            itemMeta.displayName(Component.text(ChatColor.YELLOW + Integer.toString(TagSettings.getBorderSize()) + " Block"));
        } else {
            itemMeta.displayName(Component.text(ChatColor.YELLOW + Integer.toString(TagSettings.getBorderSize()) + " Blocks"));
        }
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(29, itemStack);
    }
    
    private static void updateGUI() {
        createLengthButton(settingsGUI);
        createLocationButton(settingsGUI);
        createBorderButton(settingsGUI);
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(settingsGUI)) {
            event.setCancelled(true);
            ((Player) event.getWhoClicked()).updateInventory();
            
            if (event.getCurrentItem() != null) {
                Player player = (Player) event.getWhoClicked();
                switch (event.getSlot()) {
                    case 13 -> {
                        // custom length
                        PlayerInput.askForLength(player);
                        player.closeInventory();
                    }
                    case 14 -> {
                        // 5 minutes
                        TagSettings.setTimerLength(5);
                        TagSettings.setInfiniteGame(false);
                        updateGUI();
                    }
                    case 15 -> {
                        // infinite
                        TagSettings.setInfiniteGame(true);
                        updateGUI();
                    }
                    case 22 -> {
                        // custom location
                        PlayerInput.askForLocation(player);
                        player.closeInventory();
                    }
                    case 23 -> {
                        // current location
                        TagSettings.setSpawnX(player.getLocation().getBlockX());
                        TagSettings.setSpawnY(player.getLocation().getBlockY());
                        TagSettings.setSpawnZ(player.getLocation().getBlockZ());
                        TagSettings.setRandomizeLocation(false);
                        updateGUI();
                    }
                    case 24 -> {
                        // random location
                        TagSettings.setRandomizeLocation(true);
                        updateGUI();
                    }
                    case 31 -> {
                        // custom border
                        PlayerInput.askForBorderSize(player);
                        player.closeInventory();
                    }
                    case 32 -> {
                        // 50 blocks
                        TagSettings.setBorderSize(50);
                        updateGUI();
                    }
                    case 33 -> {
                        // no border
                        TagSettings.setBorderSize(-1);
                        updateGUI();
                    }
                }
                
                // pain
                if (event.getSlot() == 13 || event.getSlot() == 14 || event.getSlot() == 15 || event.getSlot() == 22 || event.getSlot() == 23 || event.getSlot() == 24 || event.getSlot() == 31 || event.getSlot() == 32 || event.getSlot() == 33) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
                }
            }
        }
    }
}
