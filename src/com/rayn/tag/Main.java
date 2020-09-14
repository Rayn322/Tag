package com.rayn.tag;

import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    
    @Override
    public void onEnable() {
        // registers listeners
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(tagTimer, this);
        
        Metrics metrics = new Metrics(this, 8787);
        
        getCommand("tag").setTabCompleter(new TabAutocomplete());
        
        saveDefaultConfig();
        
        isPlayingTag = false;
    }
    
    @Override
    public void onDisable() {
    }
    
    TagTimer tagTimer = new TagTimer(this);
    WorldBorderManager worldBorderManager = new WorldBorderManager(this);
    TagPlayerManager tagPlayerManager = new TagPlayerManager(this);
    FileConfiguration config = this.getConfig();
    
    public boolean isPlayingTag;
    public boolean isSpawnProtected = false;
    public BossBar bar = Bukkit.getServer().createBossBar("It player will display here.", BarColor.BLUE, BarStyle.SOLID);
    private Player itPlayer;
    private final String syntaxError = ChatColor.YELLOW + "Syntax: /tag <start/stop> <length in minutes (optional)>";
    private final String[] commands = {ChatColor.YELLOW + "Tag Commands:",
            ChatColor.YELLOW + "/tag start <length in minutes (optional)>" + ChatColor.BLUE + " - Starts the game of tag.",
            ChatColor.YELLOW + "/tag stop <length in minutes (optional)>" + ChatColor.BLUE + " - Stops the game of tag.",
            ChatColor.YELLOW + "/tag coordinates <custom/random>" + ChatColor.BLUE + " - Selects whether to use custom coordinates or random coordinates.",
            ChatColor.YELLOW + "/tag coordinates <x> <z>" + ChatColor.BLUE + " - Sets coordinates to use if random location is off.",
            ChatColor.YELLOW + "/tag reload" + ChatColor.BLUE + " - Reloads the config."};
    private WorldBorder worldBorder;
    private double tagDuration = 1.0;
    // so if you are trying to start an infinite game then it doesn't run the timer.
    public boolean usingTimer = false;
    
    // Maybe this makes it possible for other plugins to mess with who is it.
    // I'll just leave it. First time making my own getters and setters too while probably unnecessary.
    public Player getItPlayer() {
        return itPlayer;
    }
    
    public void setItPlayer(Player itPlayer) {
        this.itPlayer = itPlayer;
        if (itPlayer != null) {
            System.out.println("Set it player to " + itPlayer.getDisplayName());
        }
    }
    
    // stops tag
    public void stopTag(Player player) {
        Bukkit.broadcastMessage(ChatColor.RED + "Tag has been stopped!");
        isPlayingTag = false;
        bar.setVisible(false);
        if (worldBorder != null) {
            worldBorder.reset();
        }
        
        World world = player.getWorld();
        for (int i = 0; i < world.getPlayers().size(); i++) {
            Player playerI = world.getPlayers().get(i);
            
            Location oldLocation = tagPlayerManager.getLocation(playerI);
            if (oldLocation != null) {
                playerI.teleport(oldLocation);
            }
            
            Bukkit.getScheduler().runTaskLater(this, () -> playerI.playSound(playerI.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0.5f) , 3);
        }
        
        // removes potion effect and helmet, also resets itPlayer
        if (getItPlayer() != null) {
            itPlayer.getInventory().setHelmet(null);
//          player.removePotionEffect(PotionEffectType.SPEED);
            setItPlayer(null);
        }
        
        // cancels the timer
        Bukkit.getServer().getScheduler().cancelTasks(this);
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        // makes sure it doesn't crash if there are no args.
        if (args.length == 0) {
            for (String command : commands) {
                sender.sendMessage(command);
            }
            return false;
        }
        
        if (label.equalsIgnoreCase("tag")) {
            // starts tag game
            if (args[0].equalsIgnoreCase("start")) {
                
                // cancels the timer
                Bukkit.getServer().getScheduler().cancelTasks(this);
                
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (getItPlayer() != null) {
                        getItPlayer().getInventory().setHelmet(null);
                    }
                    setItPlayer(player);
//                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false, false));
                    isPlayingTag = true;
                    bar.setTitle(player.getName() + " is currently it!");
                    bar.setVisible(true);
                    
                    // gives it player the helmet
                    ItemStack itHelmet = new ItemStack(Material.LEATHER_HELMET);
                    LeatherArmorMeta meta = (LeatherArmorMeta) itHelmet.getItemMeta();
                    meta.setColor(Color.RED);
                    itHelmet.setItemMeta(meta);
                    player.getInventory().setHelmet(itHelmet);
                    
                    // gets random coordinates between 0 and 5000
                    Location randomLocation = worldBorderManager.getRandomLocation(player.getWorld());
                    
                    // sets all players hunger to 20, plays sound, and teleports them to 0, 0
                    World world = player.getWorld();
                    for (int i = 0; i < world.getPlayers().size(); i++) {
                        Player playerI = world.getPlayers().get(i);
                        playerI.setFoodLevel(20);
                        playerI.setGameMode(GameMode.SURVIVAL);
                        
                        // saves old location before teleporting
                        tagPlayerManager.saveLocation(playerI);
                        
                        // teleports to the random block
                        Location highestBlock = worldBorderManager.findHighestBlock((int) randomLocation.getX(), (int) randomLocation.getZ(), world);
                        playerI.teleport(highestBlock);
                        
                        Bukkit.getScheduler().runTaskLater(this, () -> playerI.playSound(playerI.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0.5f) , 5);
    
                    }
                    
                    // sets the worldborder to the same random block
                    worldBorder = world.getWorldBorder();
                    worldBorder.setCenter(randomLocation.getX(), randomLocation.getZ());
                    worldBorder.setSize(50);
                    
                    // adds players to boss bar display
                    for (int i = 0; i < world.getPlayers().size(); i++) {
                        bar.addPlayer(world.getPlayers().get(i));
                    }
                    
                    // delay for 3 seconds
                    TagTimer.spawnProtectionTimer();
                    
                    if (args.length >= 2) {
                        usingTimer = true;
                        try {
                            tagDuration = Long.parseLong(args[1]);
                            TagTimer.tagTimer(Long.parseLong(args[1]));
                        } catch (NumberFormatException e) {
                            Bukkit.getServer().broadcastMessage("Time limit is not the correct format. Starting infinite game.");
                            usingTimer = false;
                        }
                    }
                    
                    if (usingTimer) {
                        Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " is starting a " + ChatColor.YELLOW
                                + (int) tagDuration + ChatColor.GREEN + " minute game as it!");
                    } else {
                        Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " is starting an infinite game as it!");
                    }
                    
                } else {
                    sender.sendMessage("Console can't play. Sorry.");
                }
                
                return true;
                
            } else if (args[0].equalsIgnoreCase("stop")) {
                // ends tag game
                
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    stopTag(player);
                }
                
                return true;
                
            } else if (args[0].equalsIgnoreCase("reload")) {
                this.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Reloaded config.");
                System.out.println(config.getBoolean("use-random-location"));
                System.out.println(config.getInt("coordinates.x"));
                System.out.println(config.getInt("coordinates.z"));
                
                return true;
                
            } else if (args[0].equalsIgnoreCase("help")) {
                for (String command : commands) {
                    sender.sendMessage(command);
                }
            } else if (args[0].equalsIgnoreCase("coordinates")) {
                int x = 0;
                int z = 0;
    
                try {
                    x = Integer.parseInt(args[1]);
                    z = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.YELLOW + "Unable to set coordinates to that number.");
                    return true;
                } catch (ArrayIndexOutOfBoundsException e) {
                    sender.sendMessage(ChatColor.YELLOW + "Usage: /tag coordinates <x> <z>");
                    return true;
                }
    
                config.set("coordinates.x", x);
                config.set("coordinates.z", z);
    
                sender.sendMessage(ChatColor.YELLOW + "Set coordinates to " + ChatColor.BLUE +
                        "X = " + x + ChatColor.YELLOW + " and " + ChatColor.BLUE + "Z = " + z + ".");
                
                return true;
                
            } else if (args[0].equalsIgnoreCase("randomlocation")) {
                if (config.getBoolean("use-random-location")) {
                    config.set("use-random-location", false);
                    sender.sendMessage(ChatColor.YELLOW + "Now uses custom coordinates.");
                } else {
                    config.set("use-random-location", true);
                    sender.sendMessage(ChatColor.YELLOW + "Now uses a random location.");
                }
                
                saveConfig();
                return true;
    
            } else {
                sender.sendMessage(syntaxError);
                
                return true;
            }
        }
        
        return false;
    }
    
    // stops hunger loss if they are playing tag
    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            if (isPlayingTag) {
                event.setCancelled(true);
            }
        }
    }
    
    // makes person it if they are being tagged
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {
                if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
                    if (isPlayingTag && !isSpawnProtected) {
                        Player damager = (Player) event.getDamager();
                        Player victim = (Player) event.getEntity();
                        event.setDamage(0);
                        damager.getInventory().setHelmet(null);
                        
                        if (damager == getItPlayer()) {
                            setItPlayer(null);
                            setItPlayer(victim);
                            Bukkit.broadcastMessage(ChatColor.RED + victim.getName() + " is it!");
                            bar.setTitle(victim.getName() + " is currently it!");
//                            victim.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false, false));
//                            damager.removePotionEffect(PotionEffectType.SPEED);
                            
                            ItemStack itHelmet = new ItemStack(Material.LEATHER_HELMET);
                            LeatherArmorMeta meta = (LeatherArmorMeta) itHelmet.getItemMeta();
                            meta.setColor(Color.RED);
                            itHelmet.setItemMeta(meta);
                            victim.getInventory().setHelmet(itHelmet);
                            
                            World world = victim.getWorld();
                            for (int i = 0; i < world.getPlayers().size(); i++) {
                                Player player = world.getPlayers().get(i);
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                            }
                        }
                    }
                }
            }
        }
    }
}