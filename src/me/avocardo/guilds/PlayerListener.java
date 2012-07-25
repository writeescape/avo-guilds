package me.avocardo.guilds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

	private Guilds plugin;

	public PlayerListener(Guilds plugin) {
		
		this.plugin = plugin;
        
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player p = event.getPlayer();
		
		String name = p.getName().toLowerCase();
		
		if (plugin.offlinePlayers.containsKey(name)) {
			
			Guild g = plugin.offlinePlayers.get(name);
			
			plugin.onlinePlayers.put(p, g);
			plugin.log("OFFLINE TO ONLINE " + name);
			plugin.offlinePlayers.remove(name);
			
			if (g.getFlight()) {
				p.setAllowFlight(true);
			}
			
			plugin.log("LOADING " + name + " " + g.getName());
			
		} else {
			
			plugin.log("LOGIN NEW PLAYER " + p.getName());
			
			// No Guild Default Setting?
			
		}
		
		plugin.util.hidePlayers();
		
		return;
		
	}
	
	@EventHandler
	public void onExpChange(PlayerExpChangeEvent event) {
		
		Player p = event.getPlayer();
		
		if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(p.getWorld())) {
			Guild g = plugin.util.getGuild(p);
			if (g != null) {
				if (g.allowBiome(p.getLocation().getBlock().getBiome())) {
					if (g.getXP() == 0) {
						event.setAmount(0);
						return;
					} else if (g.getXP() != 100) {
						event.setAmount(Math.round(event.getAmount() * (g.getXP() / 100)));
						return;
					}
				}
			}
		}
		
	}
	
	@EventHandler
	public void onMobTarget(EntityTargetEvent event) {

		if (event.getTarget() instanceof Player) {
			if (event.getEntity() instanceof LivingEntity) {
				Player p = (Player) event.getTarget();
				if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(p.getWorld())) {
					Guild g = plugin.util.getGuild(p);
					if (g != null) {
						if (g.allowBiome(p.getLocation().getBlock().getBiome())) {
							if (g.getMobTarget()) {
								if (event.getReason().equals(TargetReason.CLOSEST_PLAYER)) {
									event.setCancelled(true);
									return;
								}
							}
						}
					}
				}
			}
		}

		return;
		
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {

		if (event.getView().getType().equals(InventoryType.CRAFTING)) {
		
			Player p = (Player) event.getPlayer();
			
			if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(p.getWorld())) {
				Guild g = plugin.util.getGuild(p);
				if (g != null) {
					PlayerInventory inv = p.getInventory();
					if (g.getRestrictions().size() > 0) {
						for (Integer integer : g.getRestrictions()) {
							ItemStack i = new ItemStack(integer, 1);
							if (inv.getHelmet() != null && inv.getHelmet().getTypeId() == i.getTypeId()) {
								inv.setHelmet(null);
								if (inv.firstEmpty() >= 0) inv.addItem(i);	
								else p.getWorld().dropItemNaturally(p.getLocation(), i);
								plugin.util.msg(p, "This armour is restricted for your guild...");
							}
							if (inv.getChestplate() != null && inv.getChestplate().getTypeId() == i.getTypeId()) {
								inv.setChestplate(null);
								if (inv.firstEmpty() >= 0) inv.addItem(i);	
								else p.getWorld().dropItemNaturally(p.getLocation(), i);
								plugin.util.msg(p, "This armour is restricted for your guild...");
							}
							if (inv.getLeggings() != null && inv.getLeggings().getTypeId() == i.getTypeId()) {
								inv.setLeggings(null);
								if (inv.firstEmpty() >= 0) inv.addItem(i);	
								else p.getWorld().dropItemNaturally(p.getLocation(), i);
								plugin.util.msg(p, "This armour is restricted for your guild...");
							}
							if (inv.getBoots() != null && inv.getBoots().getTypeId() == i.getTypeId()) {
								inv.setBoots(null);
								if (inv.firstEmpty() >= 0) inv.addItem(i);	
								else p.getWorld().dropItemNaturally(p.getLocation(), i);
								plugin.util.msg(p, "This armour is restricted for your guild...");
							}
						}
					}
				}
			}
			
		}

		return;
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		Player p = event.getPlayer();
		
		p.setAllowFlight(false);
		
		if (plugin.onlinePlayers.containsKey(p)) {
			
			plugin.offlinePlayers.put(p.getName().toLowerCase(), plugin.onlinePlayers.get(p));
			plugin.onlinePlayers.remove(p);
			
			plugin.log("ONLINE TO OFFLINE " + p.getName());
			
		}
		
		return;
		
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		
		Player p = event.getPlayer();
		
		p.setAllowFlight(false);
		
		if (plugin.onlinePlayers.containsKey(p)) {
			
			plugin.offlinePlayers.put(p.getName().toLowerCase(), plugin.onlinePlayers.get(p));
			plugin.onlinePlayers.remove(p);
			
			plugin.log("ONLINE TO OFFLINE " + p.getName());
			
		}
		
		return;
		
	}
	
	@EventHandler
	public void onPlayerHealth(EntityRegainHealthEvent event) {
		
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(p.getWorld())) {
				Guild g = plugin.util.getGuild(p);
				if (g != null) {
					if (g.allowBiome(p.getLocation().getBlock().getBiome())) {
						if (g.getRecoverHealth()) {
							return;
						} else {
							event.setCancelled(true);
							return;
						}
					}
				}
			}
		}
		
		return;
		
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		
		Player p = event.getPlayer();
		
		if (plugin.inventory.containsKey(p)) {
			for (ItemStack i : plugin.inventory.get(p)) {
				p.getInventory().addItem(i);
			}
			plugin.inventory.remove(p);
		}
		
		if (plugin.settings.allowBase) {
			if (plugin.settings.allowBaseOnDeath) {
				Guild g = plugin.util.getGuild(p);
				if (g != null) {
					event.setRespawnLocation(g.getLocation());
				}
			}
		}

	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		if (event.getEntity() instanceof Player) {
			Player p = event.getEntity();
			Guild g = plugin.util.getGuild(p);
			if (g != null) {
				if (g.getExplodeDeath()) {
					p.getWorld().createExplosion(p.getLocation(), plugin.settings.setExplodeOnDeathPower);
				}
				if (g.getRecoverExp()) {
					event.setNewExp(p.getTotalExperience());
				}
				if (g.getRecoverItems()) {
					plugin.inventory.put(p, event.getDrops());
					for (Entity e : p.getNearbyEntities(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ())) {
						if (e instanceof ItemStack) {
							e.remove();
						}
					}
				}
			}
			if (plugin.tasks.containsKey(p)) {
				Bukkit.getScheduler().cancelTask(plugin.tasks.get(p));
				plugin.tasks.remove(p);
			}
		}
		
		return;
		
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
				
		if (event.isCancelled()) {
            return;
        }
		
		Player p = event.getPlayer();
		
		if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(p.getWorld())) {
			Guild g = plugin.util.getGuild(p);
			if (g != null) {
				if (g.allowBiome(p.getLocation().getBlock().getBiome())) {
					Long time = p.getWorld().getTime();
					if (g.getFlight()) {
						if (p.isSneaking()) p.setVelocity(p.getLocation().getDirection().multiply(plugin.settings.setFlightSpeed));
					}
					if (g.getHighJump()) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10, plugin.settings.setJumpMultiplier));
					}
					if (g.getSunlight()) {
						if (time > 0 && time < 13000) {
							if ((p.getWorld().getBlockAt(p.getLocation()).getLightFromSky()) > 8) {
								if (!plugin.tasks.containsKey(p)) {
									plugin.tasks.put(p, new Scheduler(plugin).damage(p));
									plugin.util.msg(p, "SUNLIGHT!");
								}
							} else {
								if (plugin.tasks.containsKey(p)) {
									Bukkit.getScheduler().cancelTask(plugin.tasks.get(p));
									plugin.tasks.remove(p);
								}
							}
						} else {
							if (plugin.tasks.containsKey(p)) {
								Bukkit.getScheduler().cancelTask(plugin.tasks.get(p));
								plugin.tasks.remove(p);
							}
						}
					}
					if (g.getMoonlight()) {
						if (time > 13000 && time < 23000) {
							if ((p.getWorld().getBlockAt(p.getLocation()).getLightFromSky()) > 8) {
								if (!plugin.tasks.containsKey(p)) {
									plugin.tasks.put(p, new Scheduler(plugin).damage(p));
									plugin.util.msg(p, "MOONLIGHT!");
								}
							} else {
								if (plugin.tasks.containsKey(p)) {
									Bukkit.getScheduler().cancelTask(plugin.tasks.get(p));
									plugin.tasks.remove(p);
								}
							}
						} else {
							if (plugin.tasks.containsKey(p)) {
								Bukkit.getScheduler().cancelTask(plugin.tasks.get(p));
								plugin.tasks.remove(p);
							}
						}
					}
					if (g.getStorm()) {
						if ((p.getWorld().getBlockAt(p.getLocation()).getLightFromSky()) > 8) {
							if (p.getWorld().hasStorm() == true) {
								if (!plugin.tasks.containsKey(p)) {
									plugin.tasks.put(p, new Scheduler(plugin).damage(p));
									plugin.util.msg(p, "RAIN!");
								}
							} else {
								if (plugin.tasks.containsKey(p)) {
									Bukkit.getScheduler().cancelTask(plugin.tasks.get(p));
									plugin.tasks.remove(p);
								}
							}
						} else {
							if (plugin.tasks.containsKey(p)) {
								Bukkit.getScheduler().cancelTask(plugin.tasks.get(p));
								plugin.tasks.remove(p);
							}
						}
					}
					if (g.getWaterDamage()) {
						if (p.getLocation().getBlock().getTypeId() == 8 || p.getLocation().getBlock().getTypeId() == 9) {
							if (!plugin.tasks.containsKey(p)) {
								plugin.tasks.put(p, new Scheduler(plugin).damage(p));
								plugin.util.msg(p, "WATER!");
							}
						} else {
							if (plugin.tasks.containsKey(p)) {
								Bukkit.getScheduler().cancelTask(plugin.tasks.get(p));
								plugin.tasks.remove(p);
							}
						}
					}
					if (g.getLandDamage()) {
						
						List<Integer> blocks = new ArrayList<Integer>();
						
						blocks.add(p.getLocation().getBlock().getTypeId());
						blocks.add(p.getLocation().add(0, 1, 0).getBlock().getTypeId());
						blocks.add(p.getLocation().subtract(0, 1, 0).getBlock().getTypeId());
						
						if (blocks.contains(8) == false && blocks.contains(9) == false) {
							if (!plugin.tasks.containsKey(p)) {
								plugin.tasks.put(p, new Scheduler(plugin).damage(p));
								plugin.util.msg(p, "LAND!");
							}
						} else {
							if (plugin.tasks.containsKey(p)) {
								Bukkit.getScheduler().cancelTask(plugin.tasks.get(p));
								plugin.tasks.remove(p);
							}
						}
					}
				}
			} else {
				if (!plugin.settings.allowNoGuild) {
					if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
						event.setTo(event.getFrom());
						plugin.util.msg(p, "you need to join a guild to play on this server!");
						plugin.util.msg(p, "/guilds list");
						plugin.util.msg(p, "/guilds join <guild>");
						return;
					}
				}
			}
		}
		
		return;
		
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {

		Entity entity = event.getEntity();
			
		if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(entity.getWorld())) {
			if (entity instanceof Arrow) {
				Arrow arrow = (Arrow) entity;
				Entity shooter = arrow.getShooter();
				if (shooter instanceof Player) {
			        Player p = (Player) shooter;
			       	Guild g = plugin.util.getGuild(p);
			        if (g != null) {
			        	if (g.allowBiome(p.getLocation().getBlock().getBiome())) {
			        		Location loc = arrow.getLocation();
			        		World w = arrow.getWorld();
			        		if (g.getExplosiveArrow()) {
		                		w.createExplosion(loc, plugin.settings.setExplosiveArrowPower);                		
		                	}
			        		if (g.getZombieArrow()) {
		                		w.spawnCreature(loc, EntityType.ZOMBIE);
		                	}
		                	if (g.getLightningArrow()) {
		                		w.strikeLightning(loc);
		                	}
		                	if (g.getTpArrow()) {
		                		p.teleport(loc);
		                	}
			        	}
			        }
				}
			}
		}
		
		return;
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player p = event.getPlayer();
		
		if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(p.getWorld())) {
			Guild g = plugin.util.getGuild(p);
			if (g != null) {
				if (g.getRestrictions().size() > 0) {
					ItemStack inHand = p.getItemInHand();
					if (!inHand.equals(null)) {
						if (g.getRestrictions().contains(inHand.getTypeId())) {
							plugin.util.msg(p, "This item is restricted for your guild...");
							event.setCancelled(true);
						}
					}
				}
			}
		}
		
		return;
		
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

		if (event.getRightClicked() instanceof LivingEntity) {
			Player p = event.getPlayer();
			if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(p.getWorld())) {
				Guild g = plugin.util.getGuild(p);
				if (g != null) {
					if (g.allowBiome(p.getLocation().getBlock().getBiome())) {
						if (g.getKockback()) {
							event.getRightClicked().setVelocity(event.getRightClicked().getVelocity().add(event.getRightClicked().getLocation().toVector().subtract(event.getPlayer().getLocation().toVector()).normalize().multiply(plugin.settings.setKnockback)));
							event.getRightClicked().getWorld().playEffect(event.getRightClicked().getLocation(), Effect.SMOKE, 25);
						}
					}
				}
			}
		}
		
		return;
		
	}
	
	@EventHandler
	public void onFoodLevel(FoodLevelChangeEvent event) {
		
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(p.getWorld())) {
				Guild g = plugin.util.getGuild(p);
				if (g != null) {
					if (g.allowBiome(p.getLocation().getBlock().getBiome())) {
						if (g.getFood() == 0) {
							event.setCancelled(true);
						} else {
							int value = Math.round(g.getFood() / 100);
							if (value > 0) {
								int i = new Random().nextInt(value);
								if (i == 0) {
									event.setCancelled(true);
								}
							}
						}
					}
				}
			}
		}
		
		return;

	}
	
	@EventHandler
	public void onEntityShootBowEvent(EntityShootBowEvent event) {
		
		if (event.getEntity() instanceof Player) { 	
			Player p = (Player) event.getEntity();
			if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(p.getWorld())) {
				Guild g = plugin.util.getGuild(p);
				if (g != null) {
					if (g.allowBiome(p.getLocation().getBlock().getBiome())) {
						Arrow arrow = (Arrow) event.getProjectile();
						if (g.getFireArrow()) {
							arrow.setFireTicks(1500);
						}
						if (g.getStraightArrow()) {
							double yaw = Math.toRadians(p.getLocation().getYaw() + 90);
							double pitch = Math.toRadians(-p.getLocation().getPitch());
							double v = 8;
							double x = v * Math.cos(yaw) * Math.cos(pitch);
							double y = v * Math.sin(pitch);
							double z = v * Math.sin(yaw) * Math.cos(pitch);
							arrow.setVelocity(new Vector(x,y,z));
						}
					}
				}
			}
		}
		
		return;
		
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {

		if (event instanceof EntityDamageByEntityEvent) {
			return;
		}
		
		if (event.isCancelled()) {
			event.setCancelled(false);
		}
		
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			Guild g = plugin.util.getGuild(p);
			double damage = (double) event.getDamage();
			if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(p.getWorld())) {
				if (g != null) {
					if (g.allowBiome(p.getLocation().getBlock().getBiome())) {
						if (event.getCause().equals(DamageCause.FALL)) {
							if (g.getFlight()) {
								if (p.isSneaking()) {
									if (p.isFlying()) {
										event.setCancelled(true);
										return;
									}
								}
							} else if (g.getHighJump()) {
								if (!p.isSneaking()) {
									if (p.hasPotionEffect(PotionEffectType.JUMP)) {
										event.setCancelled(true);
										return;
									}
								}
							}
						}
						damage = traitDamage(damage, event.getCause(), g, false);
					}
				}
			}
			damage = Math.ceil(damage);
			event.setDamage((int) damage);
			return;
		} else {
			return;
		}
		
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
				
		if (event.isCancelled()) {
			event.setCancelled(false);
		}
		
		double damage = (double) event.getDamage();
		
		if (event.getDamager() instanceof Arrow) {
			Player defender = null;
			Guild defenderGuild = null;
			if (event.getEntity() instanceof Player) {
				defender = (Player) event.getEntity();
				defenderGuild = plugin.util.getGuild(defender);
			}
			Arrow arrow = (Arrow) event.getDamager();
			if (arrow.getShooter() instanceof Player) {
				Player damager = (Player) arrow.getShooter();
				Guild damagerGuild = plugin.util.getGuild(damager);
				if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(arrow.getShooter().getWorld())) {
					if (damagerGuild != null) {
						if (damagerGuild.allowBiome(event.getEntity().getLocation().getBlock().getBiome())) {
							if (damagerGuild.getArcher() == 0) {
								event.setCancelled(true);
								return;
							} else if (damagerGuild.getArcher() != 100) {
								damage = damage * (damagerGuild.getArcher() / (double) 100);
							}
							if (defender != null) {
								if (defenderGuild != null) {
									if (plugin.settings.allowGuildPVP == false) {
										if (damagerGuild.equals(defenderGuild)) {
											event.setCancelled(true);
											plugin.util.msg(damager, "same guild PVP is not allowed!");
											return;
										}
									}
									damage = traitDamage(damage, event.getCause(), defenderGuild, true);
								}
								if (damagerGuild.getPeacekeeper()) {
									plugin.util.msg(damager, "you are a peacekeeper!");
									event.setCancelled(true);
									return;
								}
								if (damagerGuild.getPoisonArrow()) {
									defender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, plugin.settings.setPoisonArrowTicks, 1));
								}
								
								if (damagerGuild.getBlindnessArrow()) {
									defender.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, plugin.settings.setBlindnessArrowTicks, 1));
								}
								
								if (damagerGuild.getConfusionArrow()) {
									defender.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, plugin.settings.setConfusionArrowTicks, 1));
								}
								if (damagerGuild.getMobArrow()) {
									for (Entity e : event.getEntity().getNearbyEntities(20.0d, 20.0d, 20.0d)) {
										if (e instanceof Creature) ((Creature) e).setTarget((LivingEntity) event.getEntity());
									}
								}
							}
							if (damagerGuild.getFireArrow()) {
								event.getEntity().setFireTicks(plugin.settings.setFireArrowTicks);
							}
						}
					}
				}
			} else {
				if (defenderGuild != null) {
					damage = traitDamage(damage, event.getCause(), defenderGuild, false);
				}
			}
		} else {
			Player defender = null;
			Guild defenderGuild = null;
			Player damager = null;
			Guild damagerGuild = null;
			if (event.getEntity() instanceof Player) {
				defender = (Player) event.getEntity();
				defenderGuild = plugin.util.getGuild(defender);
			}
			if (event.getDamager() instanceof Player) {
				damager = (Player) event.getDamager();
				damagerGuild = plugin.util.getGuild(damager);
			}
			if (defender == null && damager == null) {
				return;
			}
			if (defenderGuild != null) {
				if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(defender.getWorld())) {
					if (defenderGuild.allowBiome(defender.getLocation().getBlock().getBiome())) {
						if (damagerGuild != null) {
							if (plugin.settings.allowGuildPVP == false) {
								if (damagerGuild.equals(defenderGuild)) {
									event.setCancelled(true);
									plugin.util.msg(damager, "same guild PVP is not allowed!");
									return;
								}
							}
							damage = traitDamage(damage, event.getCause(), defenderGuild, true);
						} else {
							damage = traitDamage(damage, event.getCause(), defenderGuild, false);
						}
						if (defenderGuild.getReflect()) {
							if (event.getEntity() instanceof LivingEntity) {
								LivingEntity le = (LivingEntity) event.getEntity();
								le.damage(plugin.settings.setReflectDamage);
								if (damager != null) {
									plugin.util.msg(damager, "REFLECTED!");
								}
							}
						}
						if (defenderGuild.getPeacekeeper()) {
							if (damager != null) {
								plugin.util.msg(defender, "you are a peacekeeper!");
								event.setCancelled(true);
								return;
							}
						}
					}
				}
			}
			if (damagerGuild != null) {
				if (plugin.settings.allowMultiWorlds || plugin.defaultWorld.equals(damager.getWorld())) {
					if (damagerGuild.allowBiome(damager.getLocation().getBlock().getBiome())) {
						if (damagerGuild.getPeacekeeper()) {
							if (defender != null) {
								plugin.util.msg(damager, "you are a peacekeeper!");
								event.setCancelled(true);
								return;
							}
						}
						if (damagerGuild.getMelee() == 0) {
							damage = (double) 0;
						} else if (damagerGuild.getMelee() != 100) {
							damage = damage * (damagerGuild.getMelee() / (double) 100);
						}
						if (damagerGuild.getFireBlade()) {
							if (plugin.util.isBlade(damager.getItemInHand().getTypeId())) {
								event.getEntity().setFireTicks(plugin.settings.setFireBladeTicks);
							}
						}
						if (damagerGuild.getFirePunch()) {
							if (damager.getItemInHand().getType().equals(Material.AIR)) {
								event.getEntity().setFireTicks(plugin.settings.setFirePunchTicks);
							}
						}
						if (damagerGuild.getPoisonBlade()) {
							if (plugin.util.isBlade(damager.getItemInHand().getTypeId())) {
								if (defender != null) {
									defender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, plugin.settings.setPoisonBladeTicks, 1));
								}
							}
						}
					}
				}
			}
		}
		
		if (damage == 0) {
			if (plugin.settings.allowDamageAnimationOnZero) {
				event.setDamage(0);
				return;
			} else {
				event.setDamage(0);
				event.setCancelled(true);
				return;
			}
		}
		
		damage = Math.ceil(damage);
		event.setDamage((int) damage);
		
		return;
		
	}
	
	private double traitDamage(double d, DamageCause dc, Guild g, boolean player) {

		if (g != null) {
			
			double value = 0;
			
			switch (dc) {
				case CONTACT:
					value = g.getContact();
				break;
				case ENTITY_ATTACK:
					if (player == true) {
						value = g.getPlayer();
					} else {
						value = g.getMob();
					}
				break;
				case PROJECTILE:
					value = g.getProjectile();
				break;
				case SUFFOCATION:
					value = g.getSuffocation();
				break;
				case FALL:
					value = g.getFall();
				break;
				case FIRE:
					value = g.getFire();
				break;
				case FIRE_TICK:
					value = g.getFire();
				break;
				case LAVA:
					value = g.getFire();
				break;
				case DROWNING:
					value = g.getDrowning();
				break;
				case BLOCK_EXPLOSION:
					value = g.getExplosion();
				break;
				case ENTITY_EXPLOSION:
					value = g.getExplosion();
				break;
				case LIGHTNING:
					value = g.getLightning();
				break;
				case STARVATION:
					value = g.getStarvation();
				break;
				case POISON:
					value = g.getPoison();
				break;
				case MAGIC:
					value = g.getMagic();
				break;
			}
			
			if (value == 0) {
				d = 0;
			} else if (value == 100) {
				d = d * 1; // Same
				d = Math.ceil(d);
			} else {
				d = d * (value / (double) 100);
				d = Math.ceil(d);
			}
			
		}

		return d;
		
	}

}






