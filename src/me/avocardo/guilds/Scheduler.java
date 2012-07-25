package me.avocardo.guilds;

import org.bukkit.entity.Player;

public class Scheduler {

	private Guilds plugin;
	
	public Scheduler(Guilds plugin) {
	
		this.plugin = plugin;
		
	}
	
	public int damage(final Player p) {
		int i = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				p.damage(1);
			}
		}, 0L, 50L);
		return i;
	}
	
	public void save() {
		plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				new Save(plugin);
				plugin.log("SAVING TO FILE...");
			}
		}, 6000L, 6000L);
	}

}
