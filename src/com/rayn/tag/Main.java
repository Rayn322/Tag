package com.rayn.tag;

import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
        
        getServer().getPluginManager().registerEvents(timer, this);
        
        isPlayingTag = false;
    }
    
    @Override
    public void onDisable() {
    }
    
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
    
    public boolean isPlayingTag;
    public boolean isSpawnProtected = false;
    public BossBar bar = Bukkit.getServer().createBossBar("It player will display here.", BarColor.BLUE, BarStyle.SOLID);
    private Player itPlayer;
    private final String syntaxError = ChatColor.RED + "Syntax: /tag <start/stop> <length in minutes (optional)>";
    private WorldBorder worldBorder;
    
    Timer timer = new Timer(this);
    WorldBorderManager worldBorderManager = new WorldBorderManager(this);
    private double tagDuration = 1.0;
    // so if you are trying to start an infinite game then it doesn't run the timer.
    public boolean usingTimer = false;
    
    // stops tag
    public void stopTag() {
        Bukkit.broadcastMessage(ChatColor.RED + "Tag has been stopped!");
        isPlayingTag = false;
        bar.setVisible(false);
        if (worldBorder != null) {
            worldBorder.reset();
        }
    
        // removes potion effect and helmet, also resets itPlayer
        if (getItPlayer() != null) {
            Player player = getItPlayer();
            player.getInventory().setHelmet(null);
//          player.removePotionEffect(PotionEffectType.SPEED);
            setItPlayer(null);
        }
        
        // cancels the timer
        Bukkit.getServer().getScheduler().cancelTasks(this);
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        // makes sure it doesn't crash if there are no args.
        if (args.length == 0) {
            sender.sendMessage(syntaxError);
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
                        playerI.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0.5f);
    
                        // teleports to the random block
                        Location highestBlock = worldBorderManager.findHighestBlock((int) randomLocation.getX(), (int) randomLocation.getZ(), world);
                        playerI.teleport(highestBlock);
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
                    Timer.spawnProtectionTimer();
        
                    if (args.length >= 2) {
                        usingTimer = true;
                        try {
                            tagDuration = Long.parseLong(args[1]);
                            timer.tagTimer(Long.parseLong(args[1]));
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
                stopTag();
    
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    for (int i = 0; i < player.getWorld().getPlayers().size(); i++) {
                        player.getWorld().getPlayers().get(i);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0.5f);
                    }
                }
                    return true;
        
                } else {
                    sender.sendMessage(syntaxError);
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