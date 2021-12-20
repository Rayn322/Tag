package com.ryan.tag.command;

import com.ryan.tag.Tag;
import com.ryan.tag.config.SettingsGUI;
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
            ChatColor.YELLOW + "" + ChatColor.BOLD + "Tag v" + Tag.version + " by Ryan",
            ChatColor.YELLOW + "/tag <start/stop>" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Starts or stops the game.",
            ChatColor.YELLOW + "/tag settings" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "View and edit settings.",
            ChatColor.YELLOW + "/tag help" + ChatColor.DARK_GREEN + " -- " + ChatColor.YELLOW + "Prints this message.",
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
            
        } else if (args[0].equalsIgnoreCase("settings")) {
            if (Game.isPlaying) {
                sender.sendMessage(ChatColor.RED + "Cannot change settings during a game!");
            } else {
                SettingsGUI.showGUI((Player) sender);
                
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
        if (args.length == 1) return List.of("start", "stop", "settings", "help");
        
        return null;
    }
}
