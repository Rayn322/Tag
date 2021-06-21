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
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (ArrayUtils.isNotEmpty(args)) {
            sender.sendMessage(ChatColor.RED + "No arguments provided!");
        }
        
        if (sender instanceof Player) {
            if (args[0].equalsIgnoreCase("start")) {
                // TODO: allow for custom times
                Game.startGame((Player) sender, 5);
            } else if (args[0].equalsIgnoreCase("stop")) {
                sender.sendMessage(ChatColor.RED + "Force stopping game!");
                Game.stopGame();
            } else {
                sender.sendMessage(ChatColor.RED + "Not a valid argument!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Console cannot do this!");
        }
        
        return true;
    }
    
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return Arrays.asList("start", "stop");
        
        return null;
    }
}
