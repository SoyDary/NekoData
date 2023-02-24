package com.github.SoyDary.NekoData;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.earth2me.essentials.User;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class Utils {
	NekoData plugin;
	LegacyComponentSerializer lcs;
	
	public Utils(NekoData plugin) {
		this.plugin = plugin;
		lcs = LegacyComponentSerializer.builder().character('&').hexCharacter('#').extractUrls().build();
	}
	public TextComponent color(String str) {
		return lcs.deserialize(str);

	}
	
	public enum SetType {
		Increment,
		Decrement,
		Normal
	}
	public User findUser(String str) {		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!p.getName().equalsIgnoreCase(str)) continue;
			return plugin.essentials.getUser(p);
		}
		if(str.length() == 36) {
			User user = plugin.essentials.getUser(UUID.fromString(str));
			if(user != null) {
				return user;
			}
		}
		User user = plugin.essentials.getUser(str);
		if(user != null) {
			return user;
		}
		return null;	
	}

}
