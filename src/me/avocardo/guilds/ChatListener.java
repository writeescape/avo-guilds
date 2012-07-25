package me.avocardo.guilds;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {

	private Guilds plugin;
	
	public ChatListener(Guilds plugin) {
		
		this.plugin = plugin;
        
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(final PlayerChatEvent event) {
		
		String format = "";
		
		if (plugin.chatManager == "") {
			plugin.chatManager = event.getFormat();
		}
		
		format = plugin.chatManager;
		
		plugin.pluginInfo(format);
		
		if (plugin.settings.allowGuildPrefix == true) {
			
			Player player = event.getPlayer();
			Guild guild = plugin.util.getGuild(player);

			if (guild != null) {
				
				String prefix = guild.getPrefix();
				plugin.pluginInfo(prefix);
				String suffix = guild.getSuffix();
				plugin.pluginInfo(suffix);
				format = format.replace("<", "<" + prefix + ChatColor.WHITE);
				format = format.replace(">", suffix + ">" + ChatColor.WHITE);
					
			}

		}
		
		if (plugin.settings.allowChatFormat == true) {

			format = format.replace("<", plugin.settings.chatPrefix);
			format = format.replace(">", plugin.settings.chatSuffix);

		}

		if (plugin.settings.allowChatColor == true) {
			
			format = format.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
		
		}
		
		event.setFormat(format);
		
		return;
		
	}
	
}
