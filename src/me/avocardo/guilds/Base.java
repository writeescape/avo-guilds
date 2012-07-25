package me.avocardo.guilds;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Base {
	
private Guilds plugin;
	
	public Base(final Guilds plugin) {
		
		this.plugin = plugin;

	}
	
	public void base(Player p) {
	
		if (plugin.settings.allowBase == true) {
		
			if (p.hasPermission("guilds.base")) {
				
				Guild guild = plugin.util.getGuild(p);
				
				if (guild != null) {
					p.teleport(guild.getLocation());
				} else {
					plugin.util.msg(p, "you are not in a guild...");
				}
			} else {
				plugin.util.msg(p, "you do not have permissions...");
			}
			
		} else {
			plugin.util.msg(p, "base command is disabled in the config...");
		}
	
	}
	
	public void setbase(Player p, Location loc, String[] args) {
		
		if (p.hasPermission("guilds.setbase")) {
			
			Guild guild = plugin.util.getGuild(p);
			
			if (args.length > 0) {
				guild = plugin.util.getGuild(args[0]);
			}
			
			if (guild == null) {
				plugin.util.msg(p, "missing parameters... /setbase <guild>");
			} else {
				guild.setLocation(p.getLocation());
				plugin.util.msg(p, "base set for " + guild.getName());
			}
			
		}
		
	}

}
