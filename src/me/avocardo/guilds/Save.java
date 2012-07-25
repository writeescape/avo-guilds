package me.avocardo.guilds;

import java.util.Map;

import org.bukkit.entity.Player;

public class Save {

	public Save(final Guilds plugin) {
		
		plugin.log("Starting in Save...");
		
		plugin.reloadConfig();
		plugin.getConfig().set("guilds", null);
		plugin.log("CLEAR guilds");
		plugin.getConfig().set("settings", null);
		plugin.log("CLEAR settings");
		plugin.getConfig().set("players", null);
		plugin.log("CLEAR players");
		plugin.saveConfig();
		
		plugin.reloadConfig();
		
		plugin.settings.save();
		
		if (!plugin.guilds.isEmpty()) {
			for (Guild g : plugin.guilds) {
				g.saveGuild();
				plugin.log("SAVING guilds." + g.getName().toLowerCase());
			}
		}
		
		plugin.reloadConfig();
		
		if (!plugin.onlinePlayers.isEmpty()) {
			for (Map.Entry<Player, Guild> players : plugin.onlinePlayers.entrySet()) {
				plugin.getConfig().set("players." + players.getKey().getName().toLowerCase(), players.getValue().getName().toLowerCase());
				players.getKey().setAllowFlight(false);
				plugin.log("SAVING players." + players.getKey().getName().toLowerCase());
			}
		}
		
		if (!plugin.offlinePlayers.isEmpty()) {
			for (Map.Entry<String, Guild> players : plugin.offlinePlayers.entrySet()) {
				plugin.getConfig().set("players." + players.getKey().toLowerCase(), players.getValue().getName().toLowerCase());
				plugin.log("SAVING players." + players.getKey().toLowerCase() + " " + players.getValue().getName().toLowerCase());
			}
		}
		
		plugin.saveConfig();

	}
	
}
