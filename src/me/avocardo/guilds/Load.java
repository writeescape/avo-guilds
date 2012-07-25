package me.avocardo.guilds;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Load {

	public Load(Guilds plugin) {
		
		plugin.guilds.clear();		
		plugin.onlinePlayers.clear();
		plugin.offlinePlayers.clear();
		plugin.reloadConfig();
		
		FileConfiguration config = plugin.getConfig();
		
		settings(plugin);
		util(plugin);
		
		if (config.isSet("guilds")) {
			Set<String> guilds = config.getConfigurationSection("guilds").getKeys(false);
			if (guilds.isEmpty()) {
				// No Guilds
			} else {
				for (String g : guilds) {
					Guild guild = new Guild(g, plugin);
					plugin.guilds.add(guild);
					plugin.log("LOADING guilds." + g);
				}
			}
		}
		
		if (config.isSet("players")) {
			
			Guild gld = null;
			String str = "";
			String path = "";
			Player p = null;
			
			Set<String> players = config.getConfigurationSection("players").getKeys(false);
			
			if (players.isEmpty()) {
				// NO PLAYERS
			} else {
				for (String g : players) {
					
					p = null;
					path = "players." + g.toLowerCase();

					for (Player player : Bukkit.getOnlinePlayers()) {
						
						if (player.getName().equalsIgnoreCase(g)) {
							p = player;
						}
						
					}
					
					if (p != null) {
						str = config.getString(path, "");
						gld = plugin.util.getGuild(str);
						if (gld != null) {
							plugin.onlinePlayers.put(p, gld);
							if (gld.getFlight()) {
								p.setAllowFlight(true);
							}
							//new Permissions(plugin).loadPermissions(p);
							plugin.log("LOADING " + path + " " + gld.getName());
						} else {
							plugin.log("FAILED " + path + " NO GUILD");
						}
					} else {
						str = config.getString(path, "");
						gld = plugin.util.getGuild(str);
						if (gld != null) {
							plugin.offlinePlayers.put(g, gld);
							plugin.log("LOADING " + path + " " + gld.getName());
						} else {
							plugin.log("FAILED " + path + " NO GUILD");
						}						
					}
				}
			}
			
		}

	}
	
	private final void settings(Guilds plugin) {
		
		plugin.settings = new Settings(plugin);
		
	}
	
	private final void util(Guilds plugin) {
		
		plugin.util = new Util(plugin);
		
	}
	
}
