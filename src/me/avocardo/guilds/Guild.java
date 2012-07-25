package me.avocardo.guilds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;

public class Guild {
	
	private Guilds plugin;
	
	private FileConfiguration config;
	
	private List<Integer> restrictions = new ArrayList<Integer>();
	
	private List<String> permissions = new ArrayList<String>();
	
	private List<Biome> biomes = new ArrayList<Biome>();
	
	private String name;
	private String join;
	private String prefix;
	private String suffix;
	private String chatprefix;
	private Location location;
	
	private int melee; // IMPLEMENTED
	private int archer; // IMPLEMENTED
	private int fall; // IMPLEMENTED
	private int contact; // IMPLEMENTED
	private int player; // IMPLEMENTED
	private int mob; // IMPLEMENTED
	private int projectile; // IMPLEMENTED
	private int suffocation; // IMPLEMENTED
	private int fire; // IMPLEMENTED
	private int food; // IMPLEMENTED
	private int drowning; // IMPLEMENTED
	private int explosion; // IMPLEMENTED
	private int lightning; // IMPLEMENTED
	private int starvation; // IMPLEMENTED
	private int poison; // IMPLEMENTED
	private int magic; // IMPLEMENTED
	private int xp; // IMPLEMENTED
	
	private boolean peacekeeper; // IMPLEMENTED
	private boolean reflect; // IMPLEMENTED
	private boolean instabreak; // IMPLEMENTED
	private boolean knockback; // IMPLEMENTED
	private boolean mobtarget; // IMPLEMENTED
	private boolean poisonblade; // IMPLEMENTED
	private boolean fireblade; // IMPLEMENTED
	private boolean firepunch; // IMPLEMENTED
	private boolean firearrow; // IMPLEMENTED
	private boolean lightningarrow; // IMPLEMENTED
	private boolean explosivearrow; // IMPLEMENTED
	private boolean poisonarrow; // IMPLEMENTED
	private boolean tparrow; // IMPLEMENTED
	private boolean blindnessarrow; // IMPLEMENTED
	private boolean confusionarrow; // IMPLEMENTED
	private boolean straightarrow; // IMPLEMENTED
	private boolean zombiearrow; // IMPLEMENTED
	private boolean mobarrow; // IMPLEMENTED
	private boolean flight; // IMPLEMENTED
	private boolean highjump; // IMPLEMENTED
	private boolean sunlight; // IMPLEMENTED
	private boolean moonlight; // IMPLEMENTED
	private boolean storm; // IMPLEMENTED
	private boolean waterdamage; // IMPLEMENTED
	private boolean landdamage; // IMPLEMENTED
	private boolean invisible; // IMPLEMENTED
	private boolean recoverhealth; // IMPLEMENTED
	private boolean recoveritems; // IMPLEMENTED
	private boolean recoverexp; // IMPLEMENTED
	private boolean explodedeath; // IMPLEMENTED
	
	public Guild(String g, Guilds plugin, Location loc) {
		
		this.plugin = plugin;
		
		name = g;
		
		join = "open";
		prefix = "";
		suffix = "";
		chatprefix = "&d[G] ";
		location = loc;

		for (Biome b : Biome.values()) {
			biomes.add(b);
		}
		
		melee = 100;
		archer = 100;
		fall = 100;
		contact = 100;
		player = 100;
		mob = 100;
		projectile = 100;
		suffocation = 100;
		fire = 100;
		food = 100;
		drowning = 100;
		explosion = 100;
		lightning = 100;
		starvation = 100;
		poison = 100;
		magic = 100;
		xp = 100;
		
		peacekeeper = false;
		reflect = false;
		instabreak = false;
		knockback = false;
		mobtarget = false;
		poisonblade = false;
		fireblade = false;
		firepunch = false;
		firearrow = false;
		lightningarrow = false;
		explosivearrow = false;
		poisonarrow = false;
		tparrow = false;
		blindnessarrow = false;
		confusionarrow = false;
		straightarrow = false;
		zombiearrow = false;
		mobarrow = false;
		flight = false;
		highjump = false;
		sunlight = false;
		moonlight = false;
		storm = false;
		waterdamage = false;
		landdamage = false;
		invisible = false;
		recoverhealth = false;
		recoveritems = false;
		recoverexp = false;
		explodedeath = false;
		
	}

	public Guild(String g, Guilds plugin) {
		
		this.plugin = plugin;
		
		config = plugin.getConfig();

		name = config.getString("guilds." + g + ".name");
		
		join = config.getString("guilds." + g + ".join", "open");
		
		if (config.isSet("guilds." + name.toLowerCase() + ".biomes")) {
			for (String str : config.getStringList("guilds." + name.toLowerCase() + ".biomes")) {
				for (Biome b : Biome.values()) {
					if (b.name().equalsIgnoreCase(str)) {
						if (!containsBiome(b)) biomes.add(b);
					}
				}
			}
		}

		prefix = config.getString("guilds." + g + ".prefix", "");
		suffix = config.getString("guilds." + g + ".suffix", "");
		chatprefix = config.getString("guilds." + g + ".chatprefix", "");

		Location loc = new Location(Bukkit.getWorld(config.getString("guilds." + g + ".world", "world")), config.getDouble("guilds." + g + ".x", 0), config.getDouble("guilds." + g + ".y", 0), config.getDouble("guilds." + g + ".z", 0), (float)config.getDouble("guilds." + g + ".yaw", 0), (float)config.getDouble("guilds." + g + ".pitch", 0));

		location = loc;
		
		if (config.isSet("guilds." + name.toLowerCase() + ".restrictions")) {
			for (Integer i : config.getIntegerList("guilds." + name.toLowerCase() + ".restrictions")) {
				if (!containsRestriction(i)) restrictions.add(i);
			}
		}
		
		if (config.isSet("guilds." + name.toLowerCase() + ".permissions")) {
			for (String s : config.getStringList("guilds." + name.toLowerCase() + ".permissions")) {
				if (!containsPermissions(s)) permissions.add(s);
			}
		}
		
		melee = config.getInt("guilds." + g + ".traits.melee", 100);
		archer = config.getInt("guilds." + g + ".traits.archer", 100);
		fall = config.getInt("guilds." + g + ".traits.fall", 100);
		contact = config.getInt("guilds." + g + ".traits.contact", 100);
		player = config.getInt("guilds." + g + ".traits.player", 100);
		mob = config.getInt("guilds." + g + ".traits.mob", 100);
		projectile = config.getInt("guilds." + g + ".traits.projectile", 100);
		suffocation = config.getInt("guilds." + g + ".traits.suffocation", 100);
		fire = config.getInt("guilds." + g + ".traits.fire", 100);
		food = config.getInt("guilds." + g + ".traits.food", 100);
		drowning = config.getInt("guilds." + g + ".traits.drowning", 100);
		explosion = config.getInt("guilds." + g + ".traits.explosion", 100);
		lightning = config.getInt("guilds." + g + ".traits.lightning", 100);
		starvation = config.getInt("guilds." + g + ".traits.starvation", 100);
		poison = config.getInt("guilds." + g + ".traits.poison", 100);
		magic = config.getInt("guilds." + g + ".traits.magic", 100);
		xp = config.getInt("guilds." + g + ".traits.xp", 100);
		
		peacekeeper = config.getBoolean("guilds." + g + ".abilities.peacekeeper", false);
		reflect = config.getBoolean("guilds." + g + ".abilities.reflect", false);
		instabreak = config.getBoolean("guilds." + g + ".abilities.instabreak", false);
		knockback = config.getBoolean("guilds." + g + ".abilities.knockback", false);
		mobtarget = config.getBoolean("guilds." + g + ".abilities.mobtarget", false);
		poisonblade = config.getBoolean("guilds." + g + ".abilities.poisonblade", false);
		fireblade = config.getBoolean("guilds." + g + ".abilities.fireblade", false);
		firepunch = config.getBoolean("guilds." + g + ".abilities.firepunch", false);
		firearrow = config.getBoolean("guilds." + g + ".abilities.firearrow", false);
		lightningarrow = config.getBoolean("guilds." + g + ".abilities.lightningarrow", false);
		explosivearrow = config.getBoolean("guilds." + g + ".abilities.explosivearrow", false);
		poisonarrow = config.getBoolean("guilds." + g + ".abilities.poisonarrow", false);
		tparrow = config.getBoolean("guilds." + g + ".abilities.tparrow", false);
		blindnessarrow = config.getBoolean("guilds." + g + ".abilities.blindnessarrow", false);
		confusionarrow = config.getBoolean("guilds." + g + ".abilities.confusionarrow", false);
		straightarrow = config.getBoolean("guilds." + g + ".abilities.straightarrow", false);
		zombiearrow = config.getBoolean("guilds." + g + ".abilities.zombiearrow", false);
		mobarrow = config.getBoolean("guilds." + g + ".abilities.mobarrow", false);
		flight = config.getBoolean("guilds." + g + ".abilities.flight", false);
		highjump = config.getBoolean("guilds." + g + ".abilities.highjump", false);
		sunlight = config.getBoolean("guilds." + g + ".abilities.sunlight", false);
		moonlight = config.getBoolean("guilds." + g + ".abilities.moonlight", false);
		storm = config.getBoolean("guilds." + g + ".abilities.storm", false);
		waterdamage = config.getBoolean("guilds." + g + ".abilities.waterdamage", false);
		landdamage = config.getBoolean("guilds." + g + ".abilities.landdamage", false);
		invisible = config.getBoolean("guilds." + g + ".abilities.invisible", false);
		recoverhealth = config.getBoolean("guilds." + g + ".abilities.recoverhealth", false);
		recoveritems = config.getBoolean("guilds." + g + ".abilities.recoveritems", false);
		recoverexp = config.getBoolean("guilds." + g + ".abilities.recoverexp", false);
		explodedeath = config.getBoolean("guilds." + g + ".abilities.explodedeath", false);
		
	}
	
	public void saveGuild() {
		
		if (name != null) {
		
			config = plugin.getConfig();
			
			plugin.log("Guild.java: saving" + name + ".");
			
			config.set("guilds." + name.toLowerCase() + ".name", name);
			
			config.set("guilds." + name.toLowerCase() + ".join", join);
			
			List<String> biomesStr = new ArrayList<String>();
			
			for (Biome b : biomes) {
				biomesStr.add(b.name());
			}
			
			String[] saveBiomes = new String[ biomesStr.size() ];
			
			saveBiomes = biomesStr.toArray(saveBiomes);

			config.set("guilds." + name.toLowerCase() + ".biomes", null);
			
			config.set("guilds." + name.toLowerCase() + ".biomes", Arrays.asList(saveBiomes));
			config.set("guilds." + name.toLowerCase() + ".prefix", prefix);
			config.set("guilds." + name.toLowerCase() + ".suffix", suffix);
			config.set("guilds." + name.toLowerCase() + ".chatprefix", chatprefix);
	
			if (location != null) {
			
				config.set("guilds." + name.toLowerCase() + ".world", location.getWorld().getName());
				config.set("guilds." + name.toLowerCase() + ".x", location.getX());
				config.set("guilds." + name.toLowerCase() + ".y", location.getY());
				config.set("guilds." + name.toLowerCase() + ".z", location.getZ());
				config.set("guilds." + name.toLowerCase() + ".pitch", (double)location.getPitch());
				config.set("guilds." + name.toLowerCase() + ".yaw", (double)location.getYaw());
				
			} else {
				
				config.set("guilds." + name.toLowerCase() + ".world", "world");
				config.set("guilds." + name.toLowerCase() + ".x", 0);
				config.set("guilds." + name.toLowerCase() + ".y", 0);
				config.set("guilds." + name.toLowerCase() + ".z", 0);
				config.set("guilds." + name.toLowerCase() + ".pitch", (double) 0);
				config.set("guilds." + name.toLowerCase() + ".yaw", (double) 0);
				
			}

			Integer[] saveRestrictions = new Integer[ restrictions.size() ];
			
			saveRestrictions = restrictions.toArray(saveRestrictions);

			config.set("guilds." + name.toLowerCase() + ".restrictions", null);
			
			config.set("guilds." + name.toLowerCase() + ".restrictions", Arrays.asList(saveRestrictions));
			
			String[] savePermissions = new String[ permissions.size() ];
			
			savePermissions = permissions.toArray(savePermissions);
			
			config.set("guilds." + name.toLowerCase() + ".permissions", null);
			
			config.set("guilds." + name.toLowerCase() + ".permissions", Arrays.asList(savePermissions));
			
			config.set("guilds." + name.toLowerCase() + ".traits.melee", melee);
			config.set("guilds." + name.toLowerCase() + ".traits.archer", archer);
			config.set("guilds." + name.toLowerCase() + ".traits.fall", fall);
			config.set("guilds." + name.toLowerCase() + ".traits.contact", contact);
			config.set("guilds." + name.toLowerCase() + ".traits.player", player);
			config.set("guilds." + name.toLowerCase() + ".traits.mob", mob);
			config.set("guilds." + name.toLowerCase() + ".traits.projectile", projectile);
			config.set("guilds." + name.toLowerCase() + ".traits.suffocation", suffocation);
			config.set("guilds." + name.toLowerCase() + ".traits.fire", fire);
			config.set("guilds." + name.toLowerCase() + ".traits.food", food);
			config.set("guilds." + name.toLowerCase() + ".traits.drowning", drowning);
			config.set("guilds." + name.toLowerCase() + ".traits.explosion", explosion);
			config.set("guilds." + name.toLowerCase() + ".traits.lightning", lightning);
			config.set("guilds." + name.toLowerCase() + ".traits.starvation", starvation);
			config.set("guilds." + name.toLowerCase() + ".traits.poison", poison);
			config.set("guilds." + name.toLowerCase() + ".traits.magic", magic);
			config.set("guilds." + name.toLowerCase() + ".traits.xp", xp);
			
			config.set("guilds." + name.toLowerCase() + ".abilities.peacekeeper", peacekeeper);
			config.set("guilds." + name.toLowerCase() + ".abilities.reflect", reflect);
			config.set("guilds." + name.toLowerCase() + ".abilities.instabreak", instabreak);
			config.set("guilds." + name.toLowerCase() + ".abilities.knockback", knockback);
			config.set("guilds." + name.toLowerCase() + ".abilities.mobtarget", mobtarget);
			config.set("guilds." + name.toLowerCase() + ".abilities.poisonblade", poisonblade);
			config.set("guilds." + name.toLowerCase() + ".abilities.fireblade", fireblade);
			config.set("guilds." + name.toLowerCase() + ".abilities.firepunch", firepunch);
			config.set("guilds." + name.toLowerCase() + ".abilities.firearrow", firearrow);
			config.set("guilds." + name.toLowerCase() + ".abilities.lightningarrow", lightningarrow);
			config.set("guilds." + name.toLowerCase() + ".abilities.explosivearrow", explosivearrow);
			config.set("guilds." + name.toLowerCase() + ".abilities.poisonarrow", poisonarrow);
			config.set("guilds." + name.toLowerCase() + ".abilities.tparrow", tparrow);
			config.set("guilds." + name.toLowerCase() + ".abilities.blindnessarrow", blindnessarrow);
			config.set("guilds." + name.toLowerCase() + ".abilities.confusionarrow", confusionarrow);
			config.set("guilds." + name.toLowerCase() + ".abilities.straightarrow", straightarrow);
			config.set("guilds." + name.toLowerCase() + ".abilities.zombiearrow", zombiearrow);
			config.set("guilds." + name.toLowerCase() + ".abilities.mobarrow", mobarrow);
			config.set("guilds." + name.toLowerCase() + ".abilities.flight", flight);
			config.set("guilds." + name.toLowerCase() + ".abilities.highjump", highjump);
			config.set("guilds." + name.toLowerCase() + ".abilities.sunlight", sunlight);
			config.set("guilds." + name.toLowerCase() + ".abilities.moonlight", moonlight);
			config.set("guilds." + name.toLowerCase() + ".abilities.storm", storm);
			config.set("guilds." + name.toLowerCase() + ".abilities.waterdamage", waterdamage);
			config.set("guilds." + name.toLowerCase() + ".abilities.landdamage", landdamage);
			config.set("guilds." + name.toLowerCase() + ".abilities.invisible", invisible);
			config.set("guilds." + name.toLowerCase() + ".abilities.recoverhealth", recoverhealth);
			config.set("guilds." + name.toLowerCase() + ".abilities.recoveritems", recoveritems);
			config.set("guilds." + name.toLowerCase() + ".abilities.recoverexp", recoverexp);
			config.set("guilds." + name.toLowerCase() + ".abilities.explodedeath", explodedeath);
			
			plugin.saveConfig();
			
		}
		
	}
	
	public void sendLog() {
		
		plugin.pluginInfo("========== PRINT GUILD ==========");
		
		plugin.pluginInfo("name " + name);
			
		plugin.pluginInfo("biomes:");
		
		for (Biome b : biomes) {
			plugin.pluginInfo(" - " + b.name());
		}
		
		plugin.pluginInfo("join " + join);
		plugin.pluginInfo("prefix " + prefix);
		plugin.pluginInfo("suffix " + suffix);
		plugin.pluginInfo("chatprefix " + chatprefix);
		
		Integer[] r = new Integer[ restrictions.size() ];
		
		r = restrictions.toArray(r);
		
		plugin.pluginInfo("restrictions:");
		
		for (Integer i : r) {
			plugin.pluginInfo(" - " + i);
		}
		
		String[] p = new String[ permissions.size() ];
		
		p = permissions.toArray(p);
		
		plugin.pluginInfo("permissions:");
		
		for (String s : p) {
			plugin.pluginInfo(" - " + s);
		}
		
		plugin.pluginInfo("melee " + melee);
		plugin.pluginInfo("archer " + archer);
		plugin.pluginInfo("fall " + fall);
		plugin.pluginInfo("contact " + contact);
		plugin.pluginInfo("player " + player);
		plugin.pluginInfo("mob " + mob);
		plugin.pluginInfo("projectile " + projectile);
		plugin.pluginInfo("suffocation " + suffocation);
		plugin.pluginInfo("fire " + fire);
		plugin.pluginInfo("food " + food);
		plugin.pluginInfo("drowning " + drowning);
		plugin.pluginInfo("explosion " + explosion);
		plugin.pluginInfo("lightning " + lightning);
		plugin.pluginInfo("starvation " + starvation);
		plugin.pluginInfo("poison " + poison);
		plugin.pluginInfo("magic " + magic);
		plugin.pluginInfo("xp " + xp);
		
		plugin.pluginInfo("peacekeeper " + peacekeeper);
		plugin.pluginInfo("reflect " + reflect);
		plugin.pluginInfo("instabreak " + instabreak);
		plugin.pluginInfo("knockback " + knockback);
		plugin.pluginInfo("mobtarget " + mobtarget);
		plugin.pluginInfo("poisonblade " + poisonblade);
		plugin.pluginInfo("fireblade " + fireblade);
		plugin.pluginInfo("firepunch " + firepunch);
		plugin.pluginInfo("firearrow " + firearrow);
		plugin.pluginInfo("lightningarrow " + lightningarrow);
		plugin.pluginInfo("explosivearrow " + explosivearrow);
		plugin.pluginInfo("poisonarrow " + poisonarrow);
		plugin.pluginInfo("tparrow " + tparrow);
		plugin.pluginInfo("blindnessarrow " + blindnessarrow);
		plugin.pluginInfo("confusionarrow " + confusionarrow);
		plugin.pluginInfo("straightarrow " + straightarrow);
		plugin.pluginInfo("zombiearrow " + zombiearrow);
		plugin.pluginInfo("mobarrow " + mobarrow);
		plugin.pluginInfo("flight " + flight);
		plugin.pluginInfo("highjump " + highjump);
		plugin.pluginInfo("sunlight " + sunlight);
		plugin.pluginInfo("moonlight " + moonlight);
		plugin.pluginInfo("storm " + storm);
		plugin.pluginInfo("waterdamage " + waterdamage);
		plugin.pluginInfo("landdamage " + landdamage);
		plugin.pluginInfo("invisible " + invisible);
		plugin.pluginInfo("recoverhealth " + recoverhealth);
		plugin.pluginInfo("recoveritems " + recoveritems);
		plugin.pluginInfo("recoverexp " + recoverexp);
		plugin.pluginInfo("explodedeath " + explodedeath);
		
		plugin.pluginInfo("========== PRINT GUILD ==========");
		
	}
	
	public List<Integer> getRestrictions() {
		return restrictions;
	}
	
	public List<String> getPermissions() {
		return permissions;
	}
	
	public void addPermissions(String id) {
		permissions.add(id);
	}
	
	public boolean containsPermissions(String i) {
		return permissions.contains(i);
	}
	
	public void removePermissions(String i) {
		if (permissions.contains(i) == true) permissions.remove(i);
	}
	
	public void addRestriction(int id) {
		restrictions.add(id);
	}
	
	public boolean containsRestriction(int i) {
		return restrictions.contains(i);
	}
	
	public void removeRestriction(Integer i) {
		if (restrictions.contains(i) == true) restrictions.remove(i);
	}
	
	public void addBiome(String b) {
		for (Biome biome : Biome.values()) {
			if (b.equalsIgnoreCase(biome.name())) {
				if (!containsBiome(biome)) biomes.add(biome);
			}
		}
	}
	
	public boolean containsBiome(Biome b) {
		return biomes.contains(b);
	}
	
	public void removeBiome(String b) {
		for (Biome biome : Biome.values()) {
			if (b.equalsIgnoreCase(biome.name())) {
				if (!containsBiome(biome)) restrictions.remove(biome);
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getJoin() {
		if (join.equalsIgnoreCase("open")) return true;
		return false;
	}
	
	public void setJoin(String str) {
		join = str;
	}

	public void setName(String str) {
		name = str;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location loc) {
		location = loc;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String str) {
		prefix = str;
	}
	
	public String getChatPrefix() {
		return chatprefix;
	}
	
	public void setChatPrefix(String str) {
		chatprefix = str;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String str) {
		suffix = str;
	}
	
	public boolean allowBiome(Biome b) {
		if (biomes.size() == 0) return true;
		if (biomes.contains(b)) return true;
		return false;
	}

	public int getMelee() {
		return melee;
	}
	
	public void setMelee(int i) {
		melee = i;
	}
	
	public int getArcher() {
		return archer;
	}
	
	public void setArcher(int i) {
		archer = i;
	}
	
	public int getFall() {
		return fall;
	}
	
	public void setFall(int i) {
		fall = i;
	}
	
	public int getContact() {
		return contact;
	}
	
	public void setContact(int i) {
		contact = i;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public void setPlayer(int i) {
		player = i;
	}
	
	public int getMob() {
		return mob;
	}
	
	public void setMob(int i) {
		mob = i;
	}
	
	public int getProjectile() {
		return projectile;
	}
	
	public void setProjectile(int i) {
		projectile = i;
	}
	
	public int getSuffocation() {
		return suffocation;
	}
	
	public void setSuffocation(int i) {
		suffocation = i;
	}
	
	public int getFire() {
		return fire;
	}
	
	public void setFire(int i) {
		fire = i;
	}
	
	public int getFood() {
		return food;
	}
	
	public void setFood(int i) {
		food = i;
	}
	
	public int getDrowning() {
		return drowning;
	}
	
	public void setDrowning(int i) {
		drowning = i;
	}
	
	public int getExplosion() {
		return explosion;
	}
	
	public void setExplosion(int i) {
		explosion = i;
	}
	
	public int getLightning() {
		return lightning;
	}
	
	public void setLightning(int i) {
		lightning = i;
	}
	
	public int getStarvation() {
		return starvation;
	}
	
	public void setStarvation(int i) {
		starvation = i;
	}
	
	public int getPoison() {
		return poison;
	}
	
	public void setPoison(int i) {
		poison = i;
	}
	
	public int getMagic() {
		return magic;
	}
	
	public void setMagic(int i) {
		magic = i;
	}
	
	public int getXP() {
		return xp;
	}
	
	public void setXP(int i) {
		xp = i;
	}
	
	public boolean getPeacekeeper() {
		return peacekeeper;
	}
	
	public void setPeacekeeper(boolean i) {
		peacekeeper = i;
	}
	
	public boolean getReflect() {
		return reflect;
	}
	
	public void setReflect(boolean i) {
		reflect = i;
	}
	
	public boolean getInstabreak() {
		return instabreak;
	}
	
	public void setInstabreak(boolean i) {
		instabreak = i;
	}
	
	public boolean getKockback() {
		return knockback;
	}
	
	public void setKnockback(boolean i) {
		knockback = i;
	}
	
	public boolean getMobTarget() {
		return mobtarget;
	}
	
	public void setMobTarget(boolean i) {
		mobtarget = i;
	}
	
	public boolean getPoisonBlade() {
		return poisonblade;
	}
	
	public void setPoisonBlade(boolean i) {
		poisonblade = i;
	}
	
	public boolean getFireBlade() {
		return fireblade;
	}
	
	public void setFireBlade(boolean i) {
		fireblade = i;
	}
	
	public boolean getFirePunch() {
		return firepunch;
	}
	
	public void setFirePunch(boolean i) {
		firepunch = i;
	}
	
	public boolean getFireArrow() {
		return firearrow;
	}
	
	public void setFireArrow(boolean i) {
		firearrow = i;
	}
	
	public boolean getLightningArrow() {
		return lightningarrow;
	}
	
	public void setLightningArrow(boolean i) {
		lightningarrow = i;
	}
	
	public boolean getExplosiveArrow() {
		return explosivearrow;
	}
	
	public void setExplosiveArrow(boolean i) {
		explosivearrow = i;
	}
	
	public boolean getPoisonArrow() {
		return poisonarrow;
	}
	
	public void setPoisonArrow(boolean i) {
		poisonarrow = i;
	}
	
	public boolean getTpArrow() {
		return tparrow;
	}
	
	public void setTpArrow(boolean i) {
		tparrow = i;
	}
	
	public boolean getBlindnessArrow() {
		return blindnessarrow;
	}
	
	public void setBlindnessArrow(boolean i) {
		blindnessarrow = i;
	}
	
	public boolean getConfusionArrow() {
		return confusionarrow;
	}
	
	public void setConfusionArrow(boolean i) {
		confusionarrow = i;
	}
	
	public boolean getStraightArrow() {
		return straightarrow;
	}
	
	public void setStraightArrow(boolean i) {
		straightarrow = i;
	}
	
	public boolean getZombieArrow() {
		return zombiearrow;
	}
	
	public void setZombieArrow(boolean i) {
		zombiearrow = i;
	}
	
	public boolean getMobArrow() {
		return mobarrow;
	}
	
	public void setMobArrow(boolean i) {
		mobarrow = i;
	}
	
	public boolean getFlight() {
		return flight;
	}
	
	public void setFlight(boolean i) {
		flight = i;
	}
	
	public boolean getHighJump() {
		return highjump;
	}
	
	public void setHighJump(boolean i) {
		highjump = i;
	}
	
	public boolean getSunlight() {
		return sunlight;
	}
	
	public void setSunlight(boolean i) {
		sunlight = i;
	}
	
	public boolean getMoonlight() {
		return moonlight;
	}
	
	public void setMoonlight(boolean i) {
		moonlight = i;
	}
	
	public boolean getStorm() {
		return storm;
	}
	
	public void setStorm(boolean i) {
		storm = i;
	}
	
	public boolean getWaterDamage() {
		return waterdamage;
	}
	
	public void setWaterDamage(boolean i) {
		waterdamage = i;
	}
	
	public boolean getLandDamage() {
		return landdamage;
	}
	
	public void setLandDamage(boolean i) {
		landdamage = i;
	}
	
	public boolean getInvisible() {
		return invisible;
	}
	
	public void setInvisible(boolean i) {
		invisible = i;
	}

	public boolean getRecoverHealth() {
		return recoverhealth;
	}
	
	public void setRecoverHealth(boolean i) {
		recoverhealth = i;
	}

	public boolean getRecoverItems() {
		return recoveritems;
	}
	
	public void setRecoverItems(boolean i) {
		recoveritems = i;
	}

	public boolean getRecoverExp() {
		return recoverexp;
	}
	
	public void setRecoverExp(boolean i) {
		recoverexp = i;
	}

	public boolean getExplodeDeath() {
		return explodedeath;
	}
	
	public void setExplodeDeath(boolean i) {
		explodedeath = i;
	}
	
}
