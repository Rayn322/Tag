package com.ryan.tag.command;

import com.ryan.tag.gameplay.Game;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TagCommand implements TabExecutor {
    
    private final String[] helpMessage = {
            ChatColor.DARK_BLUE + "-----------------------------------------------------",
            ChatColor.YELLOW + "" + ChatColor.BOLD + "Welcome to Tag! Here are the commands:",
            ChatColor.YELLOW + "/tag help" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Prints this message",
            ChatColor.YELLOW + "/tag <start/stop>" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Starts or stops the game",
            ChatColor.YELLOW + "/tag length <seconds>" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Sets the length of the game in minutes",
            ChatColor.YELLOW + "/tag location <x> <z>" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Sets the location of the game",
            ChatColor.YELLOW + "/tag randomlocation <true/false>" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Sets whether the location of the game is random",
            ChatColor.YELLOW + "/tag bordersize <size>" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Sets the side length of the border in blocks",
            ChatColor.DARK_BLUE + "-----------------------------------------------------",
    };
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (ArrayUtils.isEmpty(args)) {
            printHelpMessage(sender);
            return true;
        }
        
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot do this!");
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
                sender.sendMessage(ChatColor.RED + "Stopping the game!");
                Game.stop();
            } else {
                sender.sendMessage(ChatColor.RED + "No game is being played!");
            }
            
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
        if (args.length == 1) return Arrays.asList("start", "stop", "help", "length", "location", "randomlocation", "bordersize");
        if (args[0].equalsIgnoreCase("randomlocation") && args.length == 2) return Arrays.asList("true", "false");
        
        return null;
    }
}
