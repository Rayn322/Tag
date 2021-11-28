package com.ryan.tag.command;

import com.ryan.tag.config.SettingsGUI;
import com.ryan.tag.config.TagSettings;
import com.ryan.tag.gameplay.Game;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TagCommand implements TabExecutor {
    
    private final String[] helpMessage = {
            ChatColor.DARK_BLUE + "-----------------------------------------------------",
            ChatColor.YELLOW + "" + ChatColor.BOLD + "Welcome to Tag! Here are the commands:",
            ChatColor.YELLOW + "/tag help" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Prints this message.",
            ChatColor.YELLOW + "/tag <start/stop>" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Starts or stops the game.",
            ChatColor.YELLOW + "/tag length <minutes>" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Sets the length of the game in minutes. Set the length to 0 for an infinite game.",
            ChatColor.YELLOW + "/tag location <x> <z>" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Sets the location of the game.",
            ChatColor.YELLOW + "/tag location random" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Makes the location of the game random.",
            ChatColor.YELLOW + "/tag border <size>" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Sets the side length of the border in blocks.",
            ChatColor.YELLOW + "/tag settings" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Views the current settings.",
            ChatColor.DARK_BLUE + "-----------------------------------------------------",
    };
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (ArrayUtils.isEmpty(args)) {
            printHelpMessage(sender);
            return true;
        }
        
        if (!(sender instanceof Player)) {
            if (args[0].equalsIgnoreCase("stop")) {
                if (Game.isPlaying) {
                    Bukkit.broadcast(Component.text(ChatColor.RED + "The game has been stopped by console."));
                    Game.stop();
                } else {
                    sender.sendMessage(ChatColor.RED + "No game is being played!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Console cannot do this!");
            }
            return true;
        }
        
        if (args[0].equalsIgnoreCase("start")) {
            // TODO: allow for custom times
            if (Game.isPlaying) {
                sender.sendMessage(ChatColor.RED + "A game is already being played!");
            } else {
                Game.start((Player) sender);
            }
            
        } else if (args[0].equalsIgnoreCase("stop")) {
            if (Game.isPlaying) {
                Bukkit.broadcast(Component.text(ChatColor.RED + "Game stopped by " + ChatColor.YELLOW + "" + ChatColor.BOLD + sender.getName() + ChatColor.RED + "."));
                Game.stop();
            } else {
                sender.sendMessage(ChatColor.RED + "No game is being played!");
            }
            
        } else if (args[0].equalsIgnoreCase("length")) {
            try {
                double length = Double.parseDouble(args[1]);
                if (length > 0) {
                    TagSettings.setTimerLength(length);
                    sender.sendMessage(ChatColor.DARK_GREEN + "Game length set to " + ChatColor.YELLOW + TagSettings.getTimerLengthAsString() + " minutes" + ChatColor.DARK_GREEN + ".");
                } else if (length == 0) {
                    sender.sendMessage(ChatColor.DARK_GREEN + "The game will now be infinite.");
                    TagSettings.setInfiniteGame(true);
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid length!");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                sender.sendMessage(ChatColor.RED + "No length provided!");
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Could not set the length to " + ChatColor.YELLOW + args[1] + ChatColor.RED + ".");
            }
            
        } else if (args[0].equalsIgnoreCase("location")) {
            try {
                int x;
                int z;
                
                if (args[1].equalsIgnoreCase("~")) {
                    x = ((Player) sender).getLocation().getBlockX();
                } else if (args[1].equalsIgnoreCase("random")) {
                    TagSettings.setRandomizeLocation(true);
                    sender.sendMessage(ChatColor.DARK_GREEN + "Tag will now use a " + ChatColor.YELLOW + "random " + ChatColor.DARK_GREEN + "location!");
                    return true;
                } else {
                    x = Integer.parseInt(args[1]);
                }
                if (args[2].equalsIgnoreCase("~")) {
                    z = ((Player) sender).getLocation().getBlockZ();
                } else {
                    z = Integer.parseInt(args[2]);
                }
                
                TagSettings.setSpawnX(x);
                TagSettings.setSpawnZ(z);
                TagSettings.setRandomizeLocation(false);
                sender.sendMessage(ChatColor.DARK_GREEN + "Location set to " + ChatColor.YELLOW + "x = " + TagSettings.getSpawnX() + ChatColor.DARK_GREEN + " and " + ChatColor.YELLOW + "z = " + TagSettings.getSpawnZ() + ChatColor.DARK_GREEN + ".");
            } catch (ArrayIndexOutOfBoundsException e) {
                sender.sendMessage(ChatColor.RED + "No coordinates provided!");
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Could not set the location to " + ChatColor.YELLOW + "x = " + args[1] + ChatColor.RED + " and " + ChatColor.YELLOW + "z = " + args[2] + ChatColor.RED + ".");
            }
            
        } else if (args[0].equalsIgnoreCase("border")) {
            try {
                int size = Integer.parseInt(args[1]);
                if (size > 0) {
                    TagSettings.setBorderSize(size);
                    sender.sendMessage(ChatColor.DARK_GREEN + "Border size set to " + ChatColor.YELLOW + TagSettings.getBorderSize() + " blocks" + ChatColor.DARK_GREEN + ".");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid size!");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                sender.sendMessage(ChatColor.RED + "No size provided!");
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Could not set the border size to " + ChatColor.YELLOW + args[1] + ChatColor.RED + ".");
            }
    
        } else if (args[0].equalsIgnoreCase("settings")) {
//            sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Tag settings:");
//            if (TagSettings.isInfiniteGame()) {
//                sender.sendMessage(ChatColor.DARK_GREEN + "Game length: " + ChatColor.YELLOW + "infinite");
//            } else {
//                sender.sendMessage(ChatColor.DARK_GREEN + "Game length: " + ChatColor.YELLOW + TagSettings.getTimerLength() + " minutes");
//            }
//
//            if (TagSettings.doesRandomizeLocation()) {
//                sender.sendMessage(ChatColor.DARK_GREEN + "Spawn Location: " + ChatColor.YELLOW + "random");
//            } else {
//                sender.sendMessage(ChatColor.DARK_GREEN + "Spawn location: " + ChatColor.YELLOW + "x = " + TagSettings.getSpawnX() + ChatColor.DARK_GREEN + " and" + ChatColor.YELLOW + " z = " + TagSettings.getSpawnZ());
//            }
//
//            sender.sendMessage(ChatColor.DARK_GREEN + "Border size: " + ChatColor.YELLOW + TagSettings.getBorderSize() + " blocks");
    
            SettingsGUI.showGUI((Player) sender);
            
        } else {
            printHelpMessage(sender);
        }
        
        return true;
    }
    
    private void printHelpMessage(CommandSender sender) {
        for (String line : helpMessage) {
            sender.sendMessage(line);
        }
    }
    
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return List.of("start", "stop", "help", "length", "location", "border", "settings");
        if (args[0].equalsIgnoreCase("randomlocation") && args.length == 2) return List.of("true", "false");
        if (args[0].equalsIgnoreCase("location")) {
            if (args.length == 2) return List.of("~", "random");
            if (args.length == 3) return List.of("~");
        }
        
        return null;
    }
}
