package com.github.SoyDary.NekoData;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.earth2me.essentials.User;
import com.github.SoyDary.NekoData.Utils.SetType;

public class Commands implements CommandExecutor {
	
	NekoData plugin;
	
	public Commands(NekoData plugin) {
		this.plugin = plugin;
		
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l, String[] a) {
		if(!s.hasPermission("nekodata.admin")) {
			s.sendMessage(plugin.getUtils().color("&4Error: &cpermisos insuficientes."));
			return true;
		}
		if(a.length == 0) {		
			s.sendMessage(plugin.getUtils().color(plugin.prefix+" &eComandos: "));
			s.sendMessage(plugin.getUtils().color("&7/"+l+" set &f<player> <data> <value>"));
			s.sendMessage(plugin.getUtils().color("&7/"+l+" get &f<player> [data]"));
			s.sendMessage(plugin.getUtils().color("&7/"+l+" remove &f<player> <data>"));
			return true;
		}
		switch(a[0].toLowerCase()) {		
		case "set": return setData(s, l, a, SetType.Normal);
		case "get": return getData(s, l, a);
		case "increment": return setData(s, l, a, SetType.Increment);	
		case "decrement": return setData(s, l, a, SetType.Decrement);	
		case "remove": return removeData(s, l, a);	
		}
		return true;
		
	}
	
	public boolean removeData(CommandSender s, String label, String[] a) {
		if(a.length < 3) {
			s.sendMessage(plugin.getUtils().color(plugin.prefix+" &e/"+label+" remove <player> <data>"));
			return true;
		}
		User target = plugin.getUtils().findUser(a[1]);
		if(target == null) {
			s.sendMessage(plugin.getUtils().color(plugin.prefix+" &cUsuario desconocido."));
			return true;
		}
	    String key = a[2];
	    String data = plugin.getData().getData(target.getUUID(), key);
	    if(data == null) {
	    	s.sendMessage(plugin.getUtils().color(plugin.prefix+ " &c"+target.getName()+" &fno tiene información en &7"+key));
	    	return true;
	    }
	    s.sendMessage(plugin.getUtils().color(plugin.prefix+ " &c"+target.getName()+" &7-> &feliminada la información en &7"+key));
	    plugin.getData().setData(target.getUUID(), key, null);
		return true;
		
	}	
	
	public boolean setData(CommandSender s, String label, String[] a, SetType type) {
		if(a.length < 4) {
			s.sendMessage(plugin.getUtils().color(plugin.prefix+" &e/"+label+" set <player> <data> <value>"));
			return true;
		}
		User target = plugin.getUtils().findUser(a[1]);
		if(target == null) {
			s.sendMessage(plugin.getUtils().color(plugin.prefix+" &cUsuario desconocido."));
			return true;
		}
	    StringBuffer sb = new StringBuffer();
	    for(int i = 3; i < a.length; i++) {
	    	if (i > 3) sb.append(" "+a[i]); else sb.append(a[i]);
	    }
	    
	    String value = sb.toString();
	    String key = a[2];
		if(value.equals("null")) value = null;
		if(plugin.numberData.contains(key)) {		
			if(value == null) {
				value = "0";
			} else {
				Integer keyNumber;
				try {
					keyNumber = Integer.valueOf(value);
				} catch (Exception e) {
					s.sendMessage(plugin.getUtils().color(plugin.prefix+" &eLa data de tipo &7"+key+" &esolo acepta números."));
					return true;
				}
				if(type == SetType.Normal) {
					value = ""+keyNumber;
				} else {
					Integer data = Integer.valueOf(plugin.getData().getData(target.getUUID(), key));
					if(type == SetType.Increment) {
						data = data+keyNumber;
					}
					if(type == SetType.Decrement) {
						data = data-keyNumber;
					}
					value = ""+data;
				}
				
			}
		}
		s.sendMessage(plugin.getUtils().color(plugin.prefix+" &a"+target.getName()+" &7-> &festablecido el valor &#737373[&7"+key+" &f: &7"+value+"&#737373]"));
	    plugin.getData().setData(target.getUUID(), key, value);
		return true;
		
	}	

	public boolean getData(CommandSender s, String label, String[] a) {
		String key = null;
		if(a.length != 2) {
			if(a.length < 3) {
				s.sendMessage(plugin.getUtils().color(plugin.prefix+" &e/"+label+" get <player> [data]"));
				return true;
			} else {
				key = a[2];
			}
		}
		User target = plugin.getUtils().findUser(a[1]);
		if(target == null) {
			s.sendMessage(plugin.getUtils().color(plugin.prefix+" &cUsuario desconocido."));
			return true;
		}
	    if(key == null) {
	    	Map<String, String> datas = plugin.getData().getAllData(target.getUUID());
	    	if(datas.isEmpty()) {
	    		s.sendMessage(plugin.getUtils().color(plugin.prefix+" &e"+target.getName()+" &fno tiene información guardada."));
	    	    return true;
	    	}
	    	s.sendMessage(plugin.getUtils().color(plugin.prefix+" &fInformación de &e"+target.getName()+"&f:"));    	
	    	for(String str : datas.keySet()) {
	    		s.sendMessage(plugin.getUtils().color("&7"+str+" &8-> &7"+datas.get(str)));
	    	}
	    	return true;
	    }
	    String value = plugin.getData().getData(target.getUUID(), key);
	    
	    if(value == null) {
	    	s.sendMessage(plugin.getUtils().color(plugin.prefix+" &e"+target.getName()+" &fno tiene información en &7"+key));
	    } else {
	    	s.sendMessage(plugin.getUtils().color(plugin.prefix+" &e"+target.getName()+" &7-> &#737373[&7"+key+" &f: &7"+value+"&#737373]"));

	    }
		return true;
		
	}
	
	
	public boolean test(CommandSender s, String label, String[] a) {
		return true;
	}
}