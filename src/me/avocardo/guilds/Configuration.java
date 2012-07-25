package me.avocardo.guilds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

	public Configuration(Guilds plugin) {
		
		plugin.reloadConfig();
		
		FileConfiguration config = plugin.getConfig();
		
		config.addDefault("settings.allowLogger", true);
		config.addDefault("settings.allowChangeGuild", false);
		config.addDefault("settings.allowNoGuild", false);
		config.addDefault("settings.allowGuildPVP", false);
		config.addDefault("settings.allowBase", false);
		config.addDefault("settings.allowBaseOnDeath", false);
		config.addDefault("settings.allowChatFormat", false);
		config.addDefault("settings.allowChatColor", true);
		config.addDefault("settings.allowGuildPrefix", false);
		config.addDefault("settings.allowGuildProtection", false);
		config.addDefault("settings.allowMultiWorlds", true);
		config.addDefault("settings.allowDamageAnimationOnZero", true);
		
		config.addDefault("settings.chatPrefix", "<");
		config.addDefault("settings.chatSuffix", ">");
		
		config.addDefault("settings.setReflectDamage", 1);
		config.addDefault("settings.setKnockback", 8);
		config.addDefault("settings.setFlightSpeed", 2);
		config.addDefault("settings.setJumpMultiplier", 4);
		config.addDefault("settings.setSprintSpeed", 2);
		config.addDefault("settings.setExplodeOnDeathPower", 1);
		config.addDefault("settings.setPoisonArrowTicks", 100);
		config.addDefault("settings.setPoisonBladeTicks", 100);
		config.addDefault("settings.setConfusionArrowTicks", 100);
		config.addDefault("settings.setBlindnessArrowTicks", 100);
		config.addDefault("settings.setFireArrowTicks", 100);
		config.addDefault("settings.setExplosiveArrowPower", 1);
		config.addDefault("settings.setFireBladeTicks", 100);
		config.addDefault("settings.setFirePunchTicks", 100);
		config.addDefault("settings.setInstabreakItem", 285);
		config.addDefault("settings.setGuildProtectionBarrier", 25);

		if (config.isSet("guilds") == false) {
			
			plugin.pluginInfo("creating config...");

			config.set("guilds.test.name", "test");
			config.set("guilds.test.join", "open");
			config.set("guilds.test.prefix", "&a[Test] &f");
			config.set("guilds.test.suffix", ":&f");
			config.set("guilds.test.chatprefix", "&d[G] ");
			config.set("guilds.test.x", 0);
			config.set("guilds.test.y", 0);
			config.set("guilds.test.z", 0);
			config.set("guilds.test.yaw", 0);
			config.set("guilds.test.pitch", 0);
			config.set("guilds.test.world", "world");
			
			List<String> biomesStr = new ArrayList<String>();
			
			for (Biome b : Biome.values()) {
				biomesStr.add(b.name());
			}
			
			String[] saveBiomes = new String[ biomesStr.size() ];
			
			saveBiomes = biomesStr.toArray(saveBiomes);
			
			config.set("guilds.test.biomes", Arrays.asList(saveBiomes));
			
			Integer[] restrictions = { 310, 311, 312, 313 };
			
			config.set("guilds.test.restrictions", Arrays.asList(restrictions));
			
			String[] permissions = { "guilds.list", "essentials.give" };
			
			config.set("guilds.test.permissions", Arrays.asList(permissions));
			
			config.set("guilds.test.traits.melee", 100);
			config.set("guilds.test.traits.archer", 100);
			config.set("guilds.test.traits.fall", 100);
			config.set("guilds.test.traits.contact", 100);
			config.set("guilds.test.traits.player", 100);
			config.set("guilds.test.traits.mob", 100);
			config.set("guilds.test.traits.projectile", 100);
			config.set("guilds.test.traits.suffocation", 100);
			config.set("guilds.test.traits.fire", 100);
			config.set("guilds.test.traits.food", 100);
			config.set("guilds.test.traits.drowning", 100);
			config.set("guilds.test.traits.explosion", 100);
			config.set("guilds.test.traits.lightning", 100);
			config.set("guilds.test.traits.starvation", 100);
			config.set("guilds.test.traits.poison", 100);
			config.set("guilds.test.traits.magic", 100);
			config.set("guilds.test.traits.xp", 100);

			config.set("guilds.test.abilities.peacekeeper", false);
			config.set("guilds.test.abilities.reflect", false);
			config.set("guilds.test.abilities.instabreak", false);
			config.set("guilds.test.abilities.knockback", false);
			config.set("guilds.test.abilities.mobtarget", false);
			config.set("guilds.test.abilities.poisonblade", false);
			config.set("guilds.test.abilities.fireblade", false);
			config.set("guilds.test.abilities.firepunch", false);
			config.set("guilds.test.abilities.firearrow", false);
			config.set("guilds.test.abilities.lightningarrow", false);
			config.set("guilds.test.abilities.explosivearrow", false);
			config.set("guilds.test.abilities.poisonarrow", false);
			config.set("guilds.test.abilities.tparrow", false);
			config.set("guilds.test.abilities.blindnessarrow", false);
			config.set("guilds.test.abilities.confusionarrow", false);
			config.set("guilds.test.abilities.straightarrow", false);
			config.set("guilds.test.abilities.zombiearrow", false);
			config.set("guilds.test.abilities.mobarrow", false);
			config.set("guilds.test.abilities.speed", false);
			config.set("guilds.test.abilities.flight", false);
			config.set("guilds.test.abilities.highjump", false);
			config.set("guilds.test.abilities.sunlight", false);
			config.set("guilds.test.abilities.moonlight", false);
			config.set("guilds.test.abilities.storm", false);
			config.set("guilds.test.abilities.waterdamage", false);
			config.set("guilds.test.abilities.landdamage", false);
			config.set("guilds.test.abilities.invisible", false);
			config.set("guilds.test.abilities.recoverhealth", false);
			config.set("guilds.test.abilities.recoveritems", false);
			config.set("guilds.test.abilities.recoverexp", false);
			config.set("guilds.test.abilities.explodedeath", false);
		
		}
		
		if (!config.isSet("players")) {
			
			config.set("players.avocardo", "test");
			
		}
		
		config.options().copyDefaults(true); // Do Not Write Over Existing Settings
		
		plugin.saveConfig();

	}
	
}
