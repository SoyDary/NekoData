package com.github.SoyDary.NekoData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Data {
	
	NekoData plugin;
	Map<String, FileConfiguration> configs;
	
	public Data(NekoData plugin) {
		this.plugin = plugin;
		this.configs = new HashMap<String, FileConfiguration>();
	}
	
	public void setData(UUID uuid, String key, String value) {
		FileConfiguration config = getConfiguration(uuid.toString());
		config.set(key, value);
		saveConfig(uuid.toString());
	}
	
	public String getData(UUID uuid, String key) {
		FileConfiguration config = getConfiguration(uuid.toString());
		if(plugin.numberData.contains(key)) {
			if(config.getString(key) == null) return "0";
			try {
				Integer.valueOf(config.getString(key));
			} catch (Exception e) {
				return "0"; 
			}
		}
		return config.getString(key);
	}
	
	public Map<String, String> getAllData(UUID uuid) {
		FileConfiguration config = getConfiguration(uuid.toString());
		Map<String, String> data = new HashMap<String, String>();
		for(String str : config.getConfigurationSection("").getKeys(false)) {
			data.put(str, getData(uuid, str));
		}
		return data;
	}
	
	public void saveConfig(String uuid) {
		File file = new File(plugin.getDataFolder()+"/Players/"+uuid+".yml");
		FileConfiguration config = configs.getOrDefault(uuid, YamlConfiguration.loadConfiguration(file));
		try {
			config.save(file);
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public FileConfiguration getConfiguration(String uuid) {
		File file = new File(plugin.getDataFolder()+"/Players/"+uuid+".yml");
		FileConfiguration config = configs.getOrDefault(uuid, YamlConfiguration.loadConfiguration(file));
		configs.put(uuid, config);
		return config;
	}

}
