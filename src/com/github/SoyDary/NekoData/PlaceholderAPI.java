package com.github.SoyDary.NekoData;

import org.bukkit.entity.Player;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderAPI extends PlaceholderExpansion {

    private NekoData plugin;

    public PlaceholderAPI(NekoData plugin) {
    	this.plugin = plugin;
    }
 
    @Override
    public boolean persist(){
        return true;
    }
   
    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().get(0);
    }
   
    @Override
    public String getIdentifier(){
        return "nekodata";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }
    
	@Override
    public String onPlaceholderRequest(Player p, String id) {
		if(p == null) return null;
		if(id.startsWith("value;")) {
			String[] a = id.split("value;");
			if(a.length == 0) return null;
			String value = a[1];
			return ""+plugin.getData().getData(p.getUniqueId(), value);
		}
		return id;
    }
}