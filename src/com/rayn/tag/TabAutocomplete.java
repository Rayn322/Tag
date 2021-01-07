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
            
            List<String> parameters = new ArrayList<>();
            parameters.add("start");
            parameters.add("stop");
            parameters.add("reload");
            parameters.add("coordinates");
            
            return parameters;
        }
        
        return null;
    }
}
