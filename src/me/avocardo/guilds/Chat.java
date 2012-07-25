package me.avocardo.guilds;

import java.util.Map;

import org.bukkit.entity.Player;

public class Chat {
	
	private Guilds plugin;
	
	public Chat(final Guilds plugin) {
		
		this.plugin = plugin;

	}
	
	public void chat(Player p, String[] args) {
		
		if (p.hasPermission("guilds.chat")) {
  			
			String msg = null;
  			
  			if (args.length > 0) {
  				
  				msg = "";
  				
  				for (String a : args) {
  					
  					msg = msg + "" + a;
  					
  				}
  				
  			} else {
  				plugin.util.msg(p, "missing parameters...");
  			}
  			
  			Guild g = plugin.util.getGuild(p);
  			
  			msg = g.getChatPrefix() + msg;
  			
  			plugin.log("Chat.java guild chat prefix... " + g.getChatPrefix());
  			
  			msg = msg.replaceAll("&([0-9a-fk-or])", "\u00A7$1"); // ADD COLOR
  			
  			if (!plugin.onlinePlayers.isEmpty()) {
  				for (Map.Entry<Player, Guild> players : plugin.onlinePlayers.entrySet()) {
  					if (players.getValue().equals(g)) {
  						if (!players.getKey().equals(p)) {
  							players.getKey().sendMessage(msg);
  						}
  					}
  				}
  			}

		} else {
			plugin.util.msg(p, "you do not have permissions...");
		}
		
	}
	
}
