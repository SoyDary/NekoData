package com.github.SoyDary.NekoData;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

public class NekoData extends JavaPlugin {
	
	public Essentials essentials;
	private Utils utils;
	private Data data;
	public String prefix = "&#ffbf00[&#ff99e6Neko&#ffcc99Data&#ffbf00]";
	public List<String> numberData;
	
	public void onEnable() {
		this.utils = new Utils(this);
		this.data = new Data(this);
		this.essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");	
		loadOrCreateConfig();
		getServer().getPluginCommand("NekoData").setExecutor(new Commands(this));	
		getServer().getPluginCommand("NekoData").setTabCompleter(new CommandCompleter(this));
	    getServer().getConsoleSender().sendMessage("[NekoData] plugin activado!");
	    new PlaceholderAPI(this).register();
		
	}
	
	public void onDisable() {
		getServer().getConsoleSender().sendMessage("[NekoData] plugin desactivado!");
	}
	
	public static NekoData getInstance() {
	    return JavaPlugin.getPlugin(NekoData.class);
	}
	
	public Utils getUtils() {
		return this.utils;
	}
	
	public Data getData() {
		return this.data;
	}
	
	private void loadOrCreateConfig() {	
		final File config = new File(this.getDataFolder() + File.separator + "config.yml");
		 if (!config.exists()) {
	           this.reloadConfig();
	           this.getConfig().options().copyDefaults(true);
	           this.saveConfig();
	       } else {
	    	   reloadConfig();
	       }
		 numberData = getConfig().getStringList("NumberData");
	}
	
}
