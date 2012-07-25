package me.avocardo.guilds;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	private Guilds plugin;

	public BlockListener(Guilds plugin) {
		
		this.plugin = plugin;
        
    }
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		if (event.isCancelled()) {
			return;
		}
		
		if (plugin.settings.allowGuildProtection) {
		
			Location loc = event.getBlockPlaced().getLocation();
			Player p = event.getPlayer();
			World w = loc.getWorld();
			
			if (plugin.settings.allowMultiWorlds) {
			
				for (Guild g : plugin.guilds) {
					if (loc.distance(g.getLocation()) <= plugin.settings.setGuildProtectionBarrier) {
						if (plugin.util.getGuild(p).equals(g)) {
							plugin.util.msg(p, "block placed in " + g.getName() + " spawn area!");
							return;
						} else {
							event.setCancelled(true);
							plugin.util.msg(p, "too close to " + g.getName() + " spawn area!");
							return;
						}
					}
				}
				
			} else {
				
				if (plugin.defaultWorld.equals(w)) {
					
					for (Guild g : plugin.guilds) {
						if (loc.distance(g.getLocation()) <= plugin.settings.setGuildProtectionBarrier) {
							if (plugin.util.getGuild(p).equals(g)) {
								plugin.util.msg(p, "block placed in " + g.getName() + " spawn area!");
								return;
							} else {
								event.setCancelled(true);
								plugin.util.msg(p, "too close to " + g.getName() + " spawn area!");
								return;
							}
						}
					}
					
				}
				
			}
			
		}
		
		return;
		
	}
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		Guild guild = plugin.util.getGuild(player);
		World w = player.getWorld();
		
		if (plugin.settings.allowGuildProtection) {
			
			Location loc = event.getBlock().getLocation();
			
			if (plugin.settings.allowMultiWorlds) {
			
				for (Guild g : plugin.guilds) {
					if (loc.distance(g.getLocation()) <= plugin.settings.setGuildProtectionBarrier) {
						if (guild.equals(g)) {
							return;
						} else {
							event.setCancelled(true);
							plugin.util.msg(player, "block is within " + g.getName() + " spawn area!");
							return;
						}
					}
				}
				
			} else {
				
				if (plugin.defaultWorld.equals(w)) {
					
					for (Guild g : plugin.guilds) {
						if (loc.distance(g.getLocation()) <= plugin.settings.setGuildProtectionBarrier) {
							if (guild.equals(g)) {
								return;
							} else {
								event.setCancelled(true);
								plugin.util.msg(player, "block is within " + g.getName() + " spawn area!");
								return;
							}
						}
					}
					
				}
				
			}
			
		}

		if (guild.getInstabreak() == true) {
			
			if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(w)) {
			
				if (plugin.settings.setInstabreakItem == event.getItemInHand().getTypeId() && event.getBlock().getTypeId() != 7) {
					
					Biome b = player.getLocation().getBlock().getBiome();
					
					if (guild.allowBiome(b)) {
					
						short before = event.getItemInHand().getDurability();
						
						event.setInstaBreak(true);
						
						Random random = new Random();
						
						int i = random.nextInt(2);
						
			            if (i > 0 && before > 0) {
			            	short value = (short) (before - 1);
			            	event.getItemInHand().setDurability(value);
			            }
					
					}
		            
				}
			
			}
			
		}

		return;
		
	}
	
}
