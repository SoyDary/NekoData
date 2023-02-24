package com.github.SoyDary.NekoData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.earth2me.essentials.User;

public class CommandCompleter implements TabCompleter {
	
	NekoData plugin;
	
	public CommandCompleter(NekoData plugin) {
		this.plugin = plugin;
		
	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command cmd, String l,  String[] a) {
		if(!s.hasPermission("nekodata.admin")) return null;
		if (a.length == 1) {
	        List<String> commandsList = new ArrayList<>();
	        List<String> preCommands = new ArrayList<>();
	        commandsList.add("get");
        	commandsList.add("set");
        	commandsList.add("increment");
        	commandsList.add("decrement");
        	commandsList.add("remove");
	        for (String text : commandsList) {
	          if (text.toLowerCase().startsWith(a[0].toLowerCase()))
	            preCommands.add(text); 
	        } 
	        return preCommands;
			
		} else {
			if(a[0].equalsIgnoreCase("get") || a[0].equalsIgnoreCase("remove") || a[0].equalsIgnoreCase("increment") || a[0].equalsIgnoreCase("decrement")) {
				if(a.length == 3) {
					User user = plugin.getUtils().findUser(a[1]);
					if(user == null) return null;
					Map<String, String> datas = plugin.getData().getAllData(user.getUUID());
					if(datas.isEmpty()) return null;
			        List<String> commandsList = new ArrayList<>();
			        List<String> preCommands = new ArrayList<>();
			        for(String str : datas.keySet()) commandsList.add(str);	
			        for (String text : commandsList) {
			        	if (text.toLowerCase().startsWith(a[2].toLowerCase()))
			        		preCommands.add(text); 
			        }
			        return preCommands;

				}				
			}
		}
		return null;
	}

}
