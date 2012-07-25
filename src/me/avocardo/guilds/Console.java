package me.avocardo.guilds;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Console {
	
	private Guilds plugin;
	
	public Console(final Guilds plugin) {
		
		this.plugin = plugin;

	}
	
	public void add(String[] args) {

  			if (args.length > 2) {
  				boolean online = true;
  				Guild newGuild = plugin.util.getGuild(args[1]);
  				Player p = Bukkit.getPlayer(args[2]);
  				if (p != null) {
  					if (newGuild != null) {
	  					if (online == true) {
		  					if (plugin.onlinePlayers.containsKey(p)) {
		  	  					plugin.onlinePlayers.remove(p);
		  	  	  				plugin.onlinePlayers.put(p, newGuild);
		  	  				} else {
		  	  					plugin.onlinePlayers.put(p, newGuild);
		  	  				}
	  					}
	  					plugin.pluginInfo(p.getName() + " successfully added to " + newGuild.getName());
					} else {
  						plugin.pluginInfo("unsuccessful! does guild " + args[1] + " exist?");
  					}
  				} else {
  					plugin.pluginInfo("player " + args[2] + " is not online...");
  				}
  			} else {
  				plugin.pluginInfo("missing parameters... /guilds add <guild> <player>");
  			}
		
  			plugin.util.hidePlayers();
		
	}
	
	public void permissions(String[] args) {

			if (args.length > 2) {
				Guild g = plugin.util.getGuild(args[1]);
  				String perm = args[2];
  				if (g != null) {
	  				String value = "" + perm;
	  				if (!g.containsPermissions(value)) g.containsPermissions(value);
	  				plugin.pluginInfo("permission " + value + " set for " + args[1]);
  				} else {
  					plugin.pluginInfo(args[1] + " is not a recognised guild...");
  				}
			} else {
				plugin.pluginInfo("missing parameters... /guilds perms <guild> <permission>");
			}
	}
	
	public void restrict(String[] args) {

			if (args.length > 2) {
				Guild g = plugin.util.getGuild(args[1]);
  				String restrict = args[2];
  				if (g != null) {
  					if (plugin.util.isNumber(restrict) == true) {
  						if (restrict.length() < 5) {
	  						int value = Integer.parseInt(restrict);
	  						if (!g.containsRestriction(value)) g.addRestriction(value);
	  						plugin.pluginInfo("restricted item " + value + " for " + args[1]);
  						} else {
  							plugin.pluginInfo(args[2] + " is too big a number...");
  						}
  					} else {
  						plugin.pluginInfo(args[2] + " is not a number...");
  					}
  				} else {
  					plugin.pluginInfo(args[1] + " is not a recognised guild...");
  				}
			} else {
				plugin.pluginInfo("missing parameters... /guilds restrict <guild> <itemid>");
			}
		
	}

	public void list(String[] args) {

			Guild guild;
			if (args.length > 1) {
				if (plugin.guilds.isEmpty()) {
					plugin.pluginInfo("No Guilds Found...");
				} else {
					guild = plugin.util.getGuild(args[1]);
					if (guild != null) {
						if (!plugin.onlinePlayers.isEmpty()) {
							plugin.pluginInfo(guild.getName() + " MEMBERS: ");
							for (Map.Entry<Player, Guild> players : plugin.onlinePlayers.entrySet()) {
								if (players.getValue().equals(guild)) plugin.pluginInfo(players.getKey().getName());
							}
						}
					} else {
						plugin.pluginInfo(args[1] + " does not exist...");
					}
				}
			} else {
				plugin.pluginInfo("GUILDS:");
				for (Guild g : plugin.guilds) {
					plugin.pluginInfo(g.getName());
				}
			}
		
	}
	
	public void create(String[] args) {

  			if (args.length > 1) {
  				Guild guild = new Guild(args[1], plugin, new Location(Bukkit.getWorlds().get(0), 0, 0, 0));
  				plugin.guilds.add(guild);
  				plugin.pluginInfo("successfully created " + args[1]);
  			} else {
  				plugin.pluginInfo("missing parameters... /guilds create <guild>");
  			}

	}
	
	public void traits(String[] args) {

  			if (args.length > 3) {
  				Guild g = plugin.util.getGuild(args[1]);
  				String t = args[2];
  				String v = args[3];
  				if (g != null) {
  					if (plugin.util.isNumber(v) == true) {
  						if (v.length() < 6) {
	  						int value = Integer.parseInt(v);
	  						if (t.equalsIgnoreCase("melee")) {
	  							g.setMelee(value);
	  						} else if (t.equalsIgnoreCase("archer")) {
	  							g.setArcher(value);
	  						} else if (t.equalsIgnoreCase("fall")) {
	  							g.setFall(value);
	  						} else if (t.equalsIgnoreCase("contact")) {
	  							g.setContact(value);
	  						} else if (t.equalsIgnoreCase("player")) {
	  							g.setPlayer(value);
	  						} else if (t.equalsIgnoreCase("mob")) {
	  							g.setMob(value);
	  						} else if (t.equalsIgnoreCase("projectile")) {
	  							g.setProjectile(value);
	  						} else if (t.equalsIgnoreCase("suffocation")) {
	  							g.setSuffocation(value);
	  						} else if (t.equalsIgnoreCase("fire")) {
	  							g.setFire(value);
	  						} else if (t.equalsIgnoreCase("food")) {
	  							g.setFood(value);
	  						} else if (t.equalsIgnoreCase("drowning")) {
	  							g.setDrowning(value);
	  						} else if (t.equalsIgnoreCase("explosion")) {
	  							g.setExplosion(value);
	  						} else if (t.equalsIgnoreCase("lightning")) {
	  							g.setLightning(value);
	  						} else if (t.equalsIgnoreCase("starvation")) {
	  							g.setStarvation(value);
	  						} else if (t.equalsIgnoreCase("poison")) {
	  							g.setPoison(value);
	  						} else if (t.equalsIgnoreCase("magic")) {
	  							g.setMagic(value);
	  						} else if (t.equalsIgnoreCase("xp")) {
	  							g.setXP(value);
	  						} else {
	  							plugin.pluginInfo(args[2] + " is not a recognised trait...");
	  							return;
	  						}
  						} else {
  							plugin.pluginInfo(args[3] + " is too big a number...");
  							return;
  						}
  						plugin.pluginInfo(args[2] + " set to " + args[3] + " for " + args[1]);
  					} else {
  						plugin.pluginInfo(args[3] + " is not a number...");
  					}
  				} else {
  					plugin.pluginInfo(args[1] + " is not a recognised guild...");
  	  			}
  			} else {
  				plugin.pluginInfo("missing parameters... /guilds traits <guild> <trait> <value>");
  			}
		
	}
	
	public void abilities(String[] args) {

  			if (args.length > 3) {
  				Guild g = plugin.util.getGuild(args[1]);
  				String t = args[2];
  				String v = args[3];
  				if (g != null) {
  					if (plugin.util.isBoolean(v) == true) {
  						boolean value = Boolean.parseBoolean(v);
  						if (t.equalsIgnoreCase("peacekeeper")) {
  							g.setPeacekeeper(value);
  						} else if (t.equalsIgnoreCase("reflect")) {
  							g.setReflect(value);
  						} else if (t.equalsIgnoreCase("instabreak")) {
  							g.setInstabreak(value);
  						} else if (t.equalsIgnoreCase("knockback")) {
  							g.setKnockback(value);
  						} else if (t.equalsIgnoreCase("mobtarget")) {
  							g.setMobTarget(value);
  						} else if (t.equalsIgnoreCase("poisonblade")) {
  							g.setPoisonBlade(value);
  						} else if (t.equalsIgnoreCase("fireblade")) {
  							g.setFireBlade(value);
  						} else if (t.equalsIgnoreCase("firepunch")) {
  							g.setFirePunch(value);
  						} else if (t.equalsIgnoreCase("firearrow")) {
  							g.setFireArrow(value);
  						} else if (t.equalsIgnoreCase("lightningarrow")) {
  							g.setLightningArrow(value);
  						} else if (t.equalsIgnoreCase("explosivearrow")) {
  							g.setExplosiveArrow(value);
  						} else if (t.equalsIgnoreCase("poisonarrow")) {
  							g.setPoisonArrow(value);
  						} else if (t.equalsIgnoreCase("tparrow")) {
  							g.setTpArrow(value);
  						} else if (t.equalsIgnoreCase("blindnessarrow")) {
  							g.setBlindnessArrow(value);
  						} else if (t.equalsIgnoreCase("confusionarrow")) {
  							g.setConfusionArrow(value);
  						} else if (t.equalsIgnoreCase("straightarrow")) {
  							g.setStraightArrow(value);
  						} else if (t.equalsIgnoreCase("zombiearrow")) {
  							g.setZombieArrow(value);
  						} else if (t.equalsIgnoreCase("mobarrow")) {
  							g.setMobArrow(value);
  						} else if (t.equalsIgnoreCase("flight")) {
  							g.setFlight(value);
  						} else if (t.equalsIgnoreCase("highjump")) {
  							g.setHighJump(value);
  						} else if (t.equalsIgnoreCase("sunlight")) {
  							g.setSunlight(value);
  						} else if (t.equalsIgnoreCase("moonlight")) {
  							g.setMoonlight(value);
  						} else if (t.equalsIgnoreCase("storm")) {
  							g.setStorm(value);
  						} else if (t.equalsIgnoreCase("waterdamage")) {
  							g.setWaterDamage(value);
  						} else if (t.equalsIgnoreCase("invisible")) {
  							g.setInvisible(value);
  						} else if (t.equalsIgnoreCase("recoverhealth")) {
  							g.setRecoverHealth(value);
  						} else if (t.equalsIgnoreCase("recoveritems")) {
  							g.setRecoverItems(value);
  						} else if (t.equalsIgnoreCase("recoverexp")) {
  							g.setRecoverExp(value);
  						} else if (t.equalsIgnoreCase("explodedeath")) {
  							g.setExplodeDeath(value);
  						} else {
  							plugin.pluginInfo(args[2] + " is not a recognised ability...");
  							return;
  						}
  						plugin.pluginInfo(args[2] + " set to " + args[3] + " for " + args[1]);
  					} else {
  						plugin.pluginInfo(args[3] + " is not set to true or false...");
  					}
  				} else {
  					plugin.pluginInfo(args[1] + " is not a recognised guild...");
  	  			}
  			} else {
  				plugin.pluginInfo("missing parameters... /guilds traits <guild> <ability> <true/false>");
  			}
	}
	
	public void remove(String[] args) {

  			if (args.length > 1) {
  				String g = args[1];
  				Guild guild = plugin.util.getGuild(g);
  				if (plugin.guilds.contains(guild)) {
  					plugin.guilds.remove(guild);
  					plugin.getConfig().set("guilds." + g.toLowerCase(), null);
  					plugin.saveConfig();
  					plugin.pluginInfo("successfully removed " + g);
  				} else {
  					plugin.pluginInfo("unsuccessful! does " + g + " exist?");
  				}
  			} else {
  				plugin.pluginInfo("missing parameters... /guilds remove <guild>");
  			}
		
		plugin.util.hidePlayers();

	}
	
	public void kick(String[] args) {

  			if (args.length > 1) {
  				Player player = Bukkit.getPlayer(args[1]);
  				if (player != null) {
  					if (plugin.onlinePlayers.containsKey(player)) {
  						Guild g = plugin.util.getGuild(player);
  						plugin.onlinePlayers.remove(player);
  						plugin.pluginInfo("successfully kicked " + args[1] + " from " + g.getName() + "...");
  					}
  				} else {
  					if (plugin.offlinePlayers.containsKey(args[1].toLowerCase())) {
  						Guild g = plugin.offlinePlayers.get(args[1].toLowerCase());
  						plugin.offlinePlayers.remove(args[1].toLowerCase());
  						plugin.pluginInfo("successfully kicked " + args[1] + " from " + g.getName() + "...");
	  				} else {
  						plugin.pluginInfo(args[1] + " not found...");
  					}
  				}
  			} else {
  				plugin.pluginInfo("missing parameters... /guilds kick <player>");
  			}
		
		plugin.util.hidePlayers();
		
	}
	
}
