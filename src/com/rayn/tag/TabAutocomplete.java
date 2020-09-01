package com.rayn.tag;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabAutocomplete implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        
        if (args.length == 1) {
            
            List<String> startOrStop = new ArrayList<>();
            startOrStop.add("start");
            startOrStop.add("stop");
            
            return startOrStop;
            
            }
        
        return null;
        }
}
