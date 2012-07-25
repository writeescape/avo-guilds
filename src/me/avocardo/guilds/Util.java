package me.avocardo.guilds;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util {

	private Guilds plugin;
	
	public Util(final Guilds plugin) {
		
		this.plugin = plugin;
		
		plugin.log("LOADING util");

	}
	
	public boolean isBlade(int i) {
	
		if (i == 267) return true;
		if (i == 268) return true;
		if (i == 272) return true;
		if (i == 276) return true;
		if (i == 283) return true;

		return false;
	
	}
	
	public Guild getGuild(Player p) {
		
		return plugin.onlinePlayers.get(p);
		
	}
	
	public Guild getGuild(String str) {
		
		for (Guild g : plugin.guilds) {
			
			if (g.getName().equalsIgnoreCase(str)) return g;
			
		}
		
		return null;
		
	}
	
	public void msg(Player player, String msg) {
		
		msg = ChatColor.AQUA + "[Guilds] " + ChatColor.YELLOW + msg;
		player.sendMessage(msg);
		
	}
	
	public boolean requireScheduler() {
		
		boolean require = false;
		
		for (Guild g : plugin.guilds) {
			if (g.getMoonlight() || g.getStorm() || g.getSunlight() || g.getWaterDamage()) {
				require = true;
			}
		}
		
		return require;
		
	}
	
	public void permissions(Player p, String[] args) {
		
		if (p.hasPermission("guilds.permissions")) {
			if (args.length > 2) {
				Guild g = getGuild(args[1]);
  				String perm = args[2];
  				if (g != null) {
	  				String value = "" + perm;
	  				if (!g.containsPermissions(value)) g.containsPermissions(value);
	  				msg(p, "permission " + value + " set for " + args[1]);
  				} else {
  					msg(p, args[1] + " is not a recognised guild...");
  				}
			} else {
				msg(p, "missing parameters... /guilds permissions <guild> <permission>");
			}
		} else {
			msg(p, "you do not have permissions...");
		}
		
	}
	
	public void restrict(Player p, String[] args) {
		
		if (p.hasPermission("guilds.restrict")) {
			if (args.length > 2) {
				Guild g = getGuild(args[1]);
  				String restrict = args[2];
  				if (g != null) {
  					if (isNumber(restrict) == true) {
  						if (restrict.length() < 5) {
	  						int value = Integer.parseInt(restrict);
	  						if (!g.containsRestriction(value)) g.addRestriction(value);
	  						msg(p, "restricted item " + value + " for " + args[1]);
  						} else {
  							msg(p, args[2] + " is too big a number...");
  						}
  					} else {
  						msg(p, args[2] + " is not a number...");
  					}
  				} else {
  					msg(p, args[1] + " is not a recognised guild...");
  				}
			} else {
				msg(p, "missing parameters... /guilds restrict <guild> <itemid>");
			}
		} else {
			msg(p, "you do not have permissions...");
		}
		
	}
	
	public void list(Player p, String[] args) {
		
		if (p.hasPermission("guilds.list")) {
			Guild guild;
			if (args.length > 1) {
				if (plugin.guilds.isEmpty()) {
					msg(p, args[1] + " does not exist...");
				} else {
					guild = getGuild(args[1]);
					if (guild != null) {
						msg(p, guildMembers(guild));
					} else {
						msg(p, args[1] + " does not exist...");
					}
				}
			} else {
				msg(p, guildList(plugin.guilds));
			}
		} else {
			msg(p, "you do not have permissions...");
		}
		
	}
	
	public String guildList(List <Guild> guilds) {
		
		String message = "Guilds: ";
		Boolean first = true;
		
		for (Guild g : guilds) {
			if (first == true) {
				message = message + g.getName();
				first = false;
			} else {
				message = message + ", " + g.getName();
			}
		}
		
		message = message + ".";
		
		return message;
		
	}
	
	public String guildMembers(Guild g) {
		
		plugin.reloadConfig();

		String message = "Online Members: ";
		
		if (!plugin.onlinePlayers.isEmpty()) {
			for (Map.Entry<Player, Guild> players : plugin.onlinePlayers.entrySet()) {
				if (players.getValue().equals(g)) message = message + players.getKey().getName() + ", ";
			}
		}
		
		message = message + "*****";
		
		if (message.contains(", *****")) {
			message = message.replace(", *****", ".");
		} else {
			message = "Online Members: none.";
		}
	
		return message;
		
	}
	
	public void join(Player p, String[] args) {

		Guild oldGuild = getGuild(p);

		if (p.hasPermission("guilds.join")) {
  			if (args.length > 1) {
  				plugin.log("Util.java " + p.getName() + " join command " + args[1]);
  				Guild newGuild = getGuild(args[1]);
  				if (newGuild == null) {
  					msg(p, "unsuccessful! does " + args[1] + " exist?");
  				} else if (newGuild == oldGuild) {
  					msg(p, "you already belong to " + args[1]);
  				} else {
	  				if (oldGuild == null) {
	  					if (newGuild.getJoin() == true) {
	  						if (p.hasPermission("guilds.join.open") || p.hasPermission("guilds.join.closed")) {
	  							if (plugin.onlinePlayers.containsKey(p)) {
									plugin.onlinePlayers.remove(p);
									plugin.onlinePlayers.put(p, newGuild);
			 					} else {
			 						plugin.onlinePlayers.put(p, newGuild);
			 					}
								msg(p, "you have been successfully added to " + args[1]);
	  						}
	  					} else {
	  						if (p.hasPermission("guilds.join.closed")) {
	  							if (plugin.onlinePlayers.containsKey(p)) {
			 						plugin.onlinePlayers.remove(p);
			 						plugin.onlinePlayers.put(p, newGuild);
			 					} else {
			 						plugin.onlinePlayers.put(p, newGuild);
			 					}
								msg(p, "you have been successfully added to " + args[1]);
	  						} else {
	  							msg(p, "you do not have permissions to join closed guilds...");
	  						}
	  					}
	  				} else {
	  					if (plugin.settings.allowChangeGuild == true) {
	  						if (newGuild.getJoin() == true) {
		  						if (p.hasPermission("guilds.join.open") || p.hasPermission("guilds.join.closed")) {
		  							if (plugin.onlinePlayers.containsKey(p)) {
			  	  						plugin.onlinePlayers.remove(p);
			  	  	 					plugin.onlinePlayers.put(p, newGuild);
			  	  					} else {
			  	  						plugin.onlinePlayers.put(p, newGuild);
			  	  					}
			  						msg(p, "you have been successfully added to " + args[1]);
		  						}
	  						} else {
		  						if (p.hasPermission("guilds.join.closed")) {
		  							if (plugin.onlinePlayers.containsKey(p)) {
			  	  						plugin.onlinePlayers.remove(p);
			  	  	 					plugin.onlinePlayers.put(p, newGuild);
			  	  					} else {
			  	  						plugin.onlinePlayers.put(p, newGuild);
			  	  					}
			  						msg(p, "you have been successfully added to " + args[1]);
		  						} else {
		  							msg(p, "you do not have permissions to join closed guilds...");
		  						}
	  						}
	  					} else {
	  						msg(p, "you are already in a guild!");
	  					}
	  				}
  				}
  			} else {
  				msg(p, "missing parameters... /guilds join <guild>");
  			}
  		} else {
  			msg(p, "you do not have permissions...");
  		}
		
		hidePlayers();
		
	}
	
	public void add(Player p, String[] args) {

		if (p.hasPermission("guilds.add")) {
  			if (args.length > 2) {
  				plugin.log("Util.java " + p.getName() + " add command " + args[1] + args[2]);
  				boolean online = true;
  				Guild newGuild = getGuild(args[1]);
  				Player player = Bukkit.getPlayer(args[2]);
  				if (player != null) {
  					if (newGuild != null) {
	  					if (online == true) {
		  					if (plugin.onlinePlayers.containsKey(player)) {
		  	  					plugin.onlinePlayers.remove(player);
		  	  	  				plugin.onlinePlayers.put(player, newGuild);
		  	  				} else {
		  	  					plugin.onlinePlayers.put(player, newGuild);
		  	  				}
	  					}
	  					msg(p, player.getName() + " successfully added to " + newGuild.getName());
						if (p != player) msg(player, player.getName() + " successfully added to " + newGuild.getName());
  					} else {
  						msg(p, "unsuccessful! does guild " + args[1] + " exist?");
  					}
  				} else {
  					msg(p, "player " + args[2] + " is not online...");
  				}
  			} else {
  				msg(p, "missing parameters... /guilds join <guild>");
  			}
  		} else {
  			msg(p, "you do not have permissions...");
  		}
		
		hidePlayers();
		
	}
	
	public void create(Player p, String[] args) {
		
		if (p.hasPermission("guilds.create")) {
  			if (args.length > 1) {
  				plugin.log("Util.java " + p.getName() + " create command " + args[1]);
  				Guild guild = new Guild(args[1], plugin, p.getLocation());
  				plugin.guilds.add(guild);
  				msg(p, "successfully created " + args[1]);
  			} else {
  				msg(p, "missing parameters... /guilds create <guild>");
  			}
  		} else {
  			msg(p, "you do not have permissions...");
  		}

	}
	
	public void remove(Player p, String[] args) {
		
		if (p.hasPermission("guilds.remove")) {
  			if (args.length > 1) {
  				String g = args[1];
  				Guild guild = getGuild(g);
  				if (plugin.guilds.contains(guild)) {
  					plugin.guilds.remove(guild);
  					plugin.getConfig().set("guilds." + g.toLowerCase(), null);
  					plugin.saveConfig();
  					msg(p, "successfully removed " + g);
  				} else {
  					msg(p, "unsuccessful! does " + g + " exist?");
  				}
  			} else {
  				msg(p, "missing parameters... /guilds remove <guild>");
  			}				  			
  		} else {
  			msg(p, "you do not have permissions...");
  		}
		
		hidePlayers();

	}
	
	public void kick(Player p, String[] args) {

		if (p.hasPermission("guilds.kick")) {
  			if (args.length > 1) {
  				plugin.log("Util.java " + p.getName() + " kick command " + args[1]);
  				Player player = Bukkit.getPlayer(args[1]);
  				if (player != null) {
  					if (plugin.onlinePlayers.containsKey(player)) {
  						Guild g = getGuild(player);
  						plugin.onlinePlayers.remove(player);
  						msg(p, "successfully kicked " + args[1] + " from " + g.getName() + "...");
  						if (player != p) msg(player, "successfully kicked " + args[1] + " from " + g.getName() + "...");
  					}
  				} else {
  					if (plugin.offlinePlayers.containsKey(args[1].toLowerCase())) {
  						Guild g = plugin.offlinePlayers.get(args[1].toLowerCase());
  						plugin.offlinePlayers.remove(args[1].toLowerCase());
	  					msg(p, "successfully kicked " + args[1] + " from " + g.getName() + "...");
	  					if (player != p) msg(player, "successfully kicked " + args[1] + " from " + g.getName() + "...");
  					} else {
  						msg(p, args[1] + " not found...");
  					}
  				}
  			} else {
  				msg(p, "missing parameters... /guilds kick <player>");
  			}
  		} else {
  			msg(p, "you do not have permissions...");
  		}
		
		hidePlayers();
		
	}
	
	public void hidePlayers() {
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				online.showPlayer(player);
			}
		}
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			Guild g = getGuild(online);
			if (g != null) {
				if (g.getInvisible() == true) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (getGuild(player) != g) {
							player.hidePlayer(online);
						}
					}
				}
			}
		}
	
	}
	
	public boolean isNumber(String string) {
	      char[] c = string.toCharArray();
	      for(int i=0; i < string.length(); i++)
	      {
	          if ( !Character.isDigit(c[i]))
	          {
	             return false;
	          }
	     }
	     return true;
	}
	
	public boolean isBoolean(String s) {
		if (s.equalsIgnoreCase("false") || s.equalsIgnoreCase("true")) return true;
		return false;
	}
	
	public void traits(Player p, String[] args) {
		
		if (p.hasPermission("guilds.traits")) {
  			if (args.length > 3) {
  				Guild g = getGuild(args[1]);
  				String t = args[2];
  				String v = args[3];
  				if (g != null) {
  					if (isNumber(v) == true) {
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
	  							msg(p, args[2] + " is not a recognised trait...");
	  							return;
	  						}
  						} else {
  							msg(p, args[3] + " is too big a number...");
  							return;
  						}
  						msg(p, args[2] + " set to " + args[3] + " for " + args[1]);
  					} else {
  						msg(p, args[3] + " is not a number...");
  					}
  				} else {
  	  				msg(p, args[1] + " is not a recognised guild...");
  	  			}
  			} else {
  				msg(p, "missing parameters... /guilds traits <guild> <trait> <value>");
  			}
		} else {
  			msg(p, "you do not have permissions...");
  		}
		
	}
	
	public void abilities(Player p, String[] args) {
		
		if (p.hasPermission("guilds.abilities")) {
  			if (args.length > 3) {
  				Guild g = getGuild(args[1]);
  				String t = args[2];
  				String v = args[3];
  				if (g != null) {
  					if (isBoolean(v) == true) {
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
  						} else if (t.equalsIgnoreCase("landdamage")) {
  							g.setLandDamage(value);
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
  							msg(p, args[2] + " is not a recognised ability...");
  							return;
  						}
  						msg(p, args[2] + " set to " + args[3] + " for " + args[1]);
  					} else {
  						msg(p, args[3] + " is not set to true or false...");
  					}
  				} else {
  	  				msg(p, args[1] + " is not a recognised guild...");
  	  			}
  			} else {
  				msg(p, "missing parameters... /guilds traits <guild> <ability> <true/false>");
  			}
		} else {
  			msg(p, "you do not have permissions...");
  		}
		
	}
	
}
