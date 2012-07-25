package me.avocardo.guilds;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Settings {

	private Guilds instance;
	
	private FileConfiguration config;

	public boolean allowLogger;
	public boolean allowChangeGuild;
	public boolean allowNoGuild;
	public boolean allowGuildPVP;
	public boolean allowBase;
	public boolean allowBaseOnDeath;
	public boolean allowChatFormat;
	public boolean allowChatColor;
	public boolean allowGuildPrefix;
	public boolean allowGuildProtection;
	public boolean allowMultiWorlds;
	public boolean allowDamageAnimationOnZero;
	
	public String chatPrefix;
	public String chatSuffix;
	
	public int setReflectDamage;
	public int setKnockback;
	public int setFlightSpeed;
	public int setSprintSpeed;
	public int setJumpMultiplier;
	public int setExplodeOnDeathPower;
	public int setPoisonArrowTicks;
	public int setPoisonBladeTicks;
	public int setConfusionArrowTicks;
	public int setBlindnessArrowTicks;
	public int setFireArrowTicks;
	public int setExplosiveArrowPower;
	public int setFireBladeTicks;
	public int setFirePunchTicks;
	public int setInstabreakItem;
	public int setGuildProtectionBarrier;
	
	public Settings(Guilds plugin) {
		
		instance = plugin;
		instance.reloadConfig();
		config = instance.getConfig();

		plugin.logging = config.getBoolean("settings.allowLogger", true);
		
		allowLogger = config.getBoolean("settings.allowLogger", true);
		plugin.log("LOADING settings.allowLogger");
		allowChangeGuild = config.getBoolean("settings.allowChangeGuild", false);
		plugin.log("LOADING settings.allowChangeGuild");
		allowNoGuild = config.getBoolean("settings.allowNoGuild", false);
		plugin.log("LOADING settings.allowNoGuild");
		allowGuildPVP = config.getBoolean("settings.allowGuildPVP", false);
		plugin.log("LOADING settings.allowGuildPVP");
		allowBase = config.getBoolean("settings.allowBase", false);
		plugin.log("LOADING settings.allowBase");
		allowBaseOnDeath = config.getBoolean("settings.allowBaseOnDeath", false);
		plugin.log("LOADING settings.allowBaseOnDeath");
		allowChatFormat = config.getBoolean("settings.allowChatFormat", false);
		plugin.log("LOADING settings.allowChatFormat");
		allowChatColor = config.getBoolean("settings.allowChatColor", true);
		plugin.log("LOADING settings.allowChatColor");
		allowGuildPrefix = config.getBoolean("settings.allowGuildPrefix", false);
		plugin.log("LOADING settings.allowGuildPrefix");
		allowGuildProtection = config.getBoolean("settings.allowGuildProtection", false);
		plugin.log("LOADING settings.allowGuildProtection");
		allowMultiWorlds = config.getBoolean("settings.allowMultiWorlds", true);
		plugin.log("LOADING settings.allowMultiWorlds");
		allowDamageAnimationOnZero = config.getBoolean("settings.allowDamageAnimationOnZero", true);
		plugin.log("LOADING settings.allowDamageAnimationOnZero");
		
		chatPrefix = config.getString("settings.chatPrefix", "'<'");
		plugin.log("LOADING settings.chatPrefix");
		chatSuffix = config.getString("settings.chatSuffix", "'>'");
		plugin.log("LOADING settings.chatSuffix");
		
		setReflectDamage = config.getInt("settings.setReflectDamage", 1);
		plugin.log("LOADING settings.setReflectDamage");
		setKnockback = config.getInt("settings.setKnockback", 1);
		plugin.log("LOADING settings.setKnockback");
		setFlightSpeed = config.getInt("settings.setFlightSpeed", 2);
		plugin.log("LOADING settings.setFlightSpeed");
		setJumpMultiplier = config.getInt("settings.setJumpMultiplier", 4);
		plugin.log("LOADING settings.setJumpMultiplier");
		setSprintSpeed = config.getInt("settings.setSprintSpeed", 2);
		plugin.log("LOADING settings.setSprintSpeed");
		setExplodeOnDeathPower = config.getInt("settings.setExplodeOnDeathPower", 1);
		plugin.log("LOADING settings.setExplodeOnDeathPower");
		setPoisonArrowTicks = config.getInt("settings.setPoisonArrowTicks", 100);
		plugin.log("LOADING settings.setPoisonArrowTicks");
		setPoisonBladeTicks = config.getInt("settings.setPoisonBladeTicks", 100);
		plugin.log("LOADING settings.setPoisonBladeTicks");
		setConfusionArrowTicks = config.getInt("settings.setConfusionArrowTicks", 100);
		plugin.log("LOADING settings.setConfusionArrowTicks");
		setBlindnessArrowTicks = config.getInt("settings.setBlindnessArrowTicks", 100);
		plugin.log("LOADING settings.setBlindnessArrowTicks");
		setFireArrowTicks = config.getInt("settings.setFireArrowTicks", 100);
		plugin.log("LOADING settings.setFireArrowTicks");
		setExplosiveArrowPower = config.getInt("settings.setExplosiveArrowPower", 1);
		plugin.log("LOADING settings.setExplosiveArrowPower");
		setFireBladeTicks = config.getInt("settings.setFireBladeTicks", 100);
		plugin.log("LOADING settings.setFireBladeTicks");
		setFirePunchTicks = config.getInt("settings.setFirePunchTicks", 100);
		plugin.log("LOADING settings.setFirePunchTicks");
		setInstabreakItem = config.getInt("settings.setInstabreakItem", 285);
		plugin.log("LOADING settings.setInstabreakItem");
		setGuildProtectionBarrier = config.getInt("settings.setGuildProtectionBarrier", 25);
		plugin.log("LOADING settings.setGuildProtectionBarrier");

	}
	
	public void save() {
		
		config = instance.getConfig();
		
		config.set("settings.allowLogger", allowLogger);
		instance.log("SAVING settings.allowLogger");
		config.set("settings.allowChangeGuild", allowChangeGuild);
		instance.log("SAVING settings.allowChangeGuild");
		config.set("settings.allowNoGuild", allowNoGuild);
		instance.log("SAVING settings.allowNoGuild");
		config.set("settings.allowGuildPVP", allowGuildPVP);
		instance.log("SAVING settings.allowGuildPVP");
		config.set("settings.allowBase", allowBase);
		instance.log("SAVING settings.allowBase");
		config.set("settings.allowBaseOnDeath", allowBaseOnDeath);
		instance.log("SAVING settings.allowBaseOnDeath");
		config.set("settings.allowChatFormat", allowChatFormat);
		instance.log("SAVING settings.allowChatFormat");
		config.set("settings.allowChatColor", allowChatColor);
		instance.log("SAVING settings.allowChatColor");
		config.set("settings.allowGuildPrefix", allowGuildPrefix);
		instance.log("SAVING settings.allowGuildPrefix");
		config.set("settings.allowGuildProtection", allowGuildProtection);
		instance.log("SAVING settings.allowGuildProtection");
		config.set("settings.allowMultiWorlds", allowMultiWorlds);
		instance.log("SAVING settings.allowMultiWorlds");
		config.set("settings.allowDamageAnimationOnZero", allowDamageAnimationOnZero);
		instance.log("SAVING settings.allowDamageAnimationOnZero");
		config.set("settings.chatPrefix", chatPrefix);
		instance.log("SAVING settings.chatPrefix");
		config.set("settings.chatSuffix", chatSuffix);
		instance.log("SAVING settings.chatSuffix");
		config.set("settings.setReflectDamage", setReflectDamage);
		instance.log("SAVING settings.setReflectDamage");
		config.set("settings.setKnockback", setKnockback);
		instance.log("SAVING settings.setKnockback");
		config.set("settings.setFlightSpeed", setFlightSpeed);
		instance.log("SAVING settings.setFlightSpeed");
		config.set("settings.setJumpMultiplier", setJumpMultiplier);
		instance.log("SAVING settings.setJumpMultiplier");
		config.set("settings.setSprintSpeed", setSprintSpeed);
		instance.log("SAVING settings.setSprintSpeed");
		config.set("settings.setExplodeOnDeathPower", setExplodeOnDeathPower);
		instance.log("SAVING settings.setExplodeOnDeathPower");
		config.set("settings.setPoisonArrowTicks", setPoisonArrowTicks);
		instance.log("SAVING settings.setPoisonArrowTicks");
		config.set("settings.setPoisonBladeTicks", setPoisonBladeTicks);
		instance.log("SAVING settings.setPoisonBladeTicks");
		config.set("settings.setConfusionArrowTicks", setConfusionArrowTicks);
		instance.log("SAVING settings.setConfusionArrowTicks");
		config.set("settings.setBlindnessArrowTicks", setBlindnessArrowTicks);
		instance.log("SAVING settings.setBlindnessArrowTicks");
		config.set("settings.setFireArrowTicks", setFireArrowTicks);
		instance.log("SAVING settings.setFireArrowTicks");
		config.set("settings.setExplosiveArrowPower", setExplosiveArrowPower);
		instance.log("SAVING settings.setExplosiveArrowPower");
		config.set("settings.setFireBladeTicks", setFireBladeTicks);
		instance.log("SAVING settings.setFireBladeTicks");
		config.set("settings.setFirePunchTicks", setFirePunchTicks);
		instance.log("SAVING settings.setFirePunchTicks");
		config.set("settings.setInstabreakItem", setInstabreakItem);
		instance.log("SAVING settings.setInstabreakItem");
		config.set("settings.setGuildProtectionBarrier", setGuildProtectionBarrier);
		instance.log("SAVING settings.setGuildProtectionBarrier");
		
		instance.saveConfig();
		
	}
	
	public void set(Player p, String [] args) {
		
		String v = null;
		String s = null;
		boolean found = false;
		
		if (p.hasPermission("guilds.settings")) {

				if (args.length > 2) {
					
					s = args[1];
					v = args[2];
		
					if (instance.util.isNumber(v) == true) {
						
						instance.log("Settings.java int " + s);
						int value = Integer.parseInt(v);
						
						if (s.equalsIgnoreCase("setReflectDamage")) { setReflectDamage = value; found = true; }
						if (s.equalsIgnoreCase("setKnockback")) { setKnockback = value; found = true; }
						if (s.equalsIgnoreCase("setJumpMultiplier")) { setJumpMultiplier = value; found = true; }
						if (s.equalsIgnoreCase("setFlightSpeed")) { setFlightSpeed = value; found = true; }
						if (s.equalsIgnoreCase("setSprintSpeed")) { setSprintSpeed = value; found = true; }
						if (s.equalsIgnoreCase("setExplodeOnDeathPower")) { setExplodeOnDeathPower = value; found = true; }
						if (s.equalsIgnoreCase("setPoisonArrowTicks")) { setPoisonArrowTicks = value; found = true; }
						if (s.equalsIgnoreCase("setPoisonBladeTicks")) { setPoisonBladeTicks = value; found = true; }
						if (s.equalsIgnoreCase("setConfusionArrowTicks")) { setConfusionArrowTicks = value; found = true; }
						if (s.equalsIgnoreCase("setBlindnessArrowTicks")) { setBlindnessArrowTicks = value; found = true; }
						if (s.equalsIgnoreCase("setFireArrowTicks")) { setFireArrowTicks = value; found = true; }
						if (s.equalsIgnoreCase("setExplosiveArrowPower")) { setExplosiveArrowPower = value; found = true; }
						if (s.equalsIgnoreCase("setFireBladeTicks")) { setFireBladeTicks = value; found = true; }
						if (s.equalsIgnoreCase("setFirePunchTicks")) { setFirePunchTicks = value; found = true; }
						if (s.equalsIgnoreCase("setInstabreakItem")) { setInstabreakItem = value; found = true; }
						if (s.equalsIgnoreCase("setGuildProtectionBarrier")) { setGuildProtectionBarrier = value; found = true; }

					} else if (instance.util.isBoolean(v) == true) {
						
						instance.log("Settings.java boolean " + s);
						boolean value = Boolean.parseBoolean(v);
						
						if (s.equalsIgnoreCase("allowLogger")) { allowLogger = value; found = true; }
						if (s.equalsIgnoreCase("allowChangeGuild")) { allowChangeGuild = value; found = true; }
						if (s.equalsIgnoreCase("allowNoGuild")) { allowNoGuild = value; found = true; }
						if (s.equalsIgnoreCase("allowGuildPVP")) { allowGuildPVP = value; found = true; }
						if (s.equalsIgnoreCase("allowBase")) { allowBase = value; found = true; }
						if (s.equalsIgnoreCase("allowBaseOnDeath")) { allowBaseOnDeath = value; found = true; }
						if (s.equalsIgnoreCase("allowChatFormat")) { allowChatFormat = value; found = true; }
						if (s.equalsIgnoreCase("allowChatColor")) { allowChatColor = value; found = true; }
						if (s.equalsIgnoreCase("allowGuildPrefix")) { allowGuildPrefix = value; found = true; }
						if (s.equalsIgnoreCase("allowGuildProtection")) { allowGuildProtection = value; found = true; }
						if (s.equalsIgnoreCase("allowMultiWorlds")) { allowMultiWorlds = value; found = true; }
						if (s.equalsIgnoreCase("allowDamageAnimationOnZero")) { allowDamageAnimationOnZero = value; found = true; }
						
					} else {
						
						instance.log("Settings.java text/other " + s);
						String value = v;
			
						if (s.equalsIgnoreCase("chatPrefix")) { chatPrefix = value; found = true; }
						if (s.equalsIgnoreCase("chatSuffix")) { chatSuffix = value; found = true; }
						
					}
					
				} else {
					instance.util.msg(p, "missing parameters... /guilds settings <setting> <value>");
				}
		} else {
			instance.util.msg(p, "you do not have permissions...");
		}
		
		if (found == true) instance.util.msg(p, "successfully set " + args[1] + " to " + args[2]);
		
	}
	
public void set(String [] args) {
		
		String v = null;
		String s = null;
		boolean found = false;

				if (args.length > 2) {
					
					s = args[1];
					v = args[2];
		
					if (instance.util.isNumber(v) == true) {
						
						instance.log("Settings.java int " + s);
						int value = Integer.parseInt(v);
						
						if (s.equalsIgnoreCase("setReflectDamage")) { setReflectDamage = value; found = true; }
						if (s.equalsIgnoreCase("setKnockback")) { setKnockback = value; found = true; }
						if (s.equalsIgnoreCase("setJumpMultiplier")) { setJumpMultiplier = value; found = true; }
						if (s.equalsIgnoreCase("setFlightSpeed")) { setFlightSpeed = value; found = true; }
						if (s.equalsIgnoreCase("setSprintSpeed")) { setSprintSpeed = value; found = true; }
						if (s.equalsIgnoreCase("setExplodeOnDeathPower")) { setExplodeOnDeathPower = value; found = true; }
						if (s.equalsIgnoreCase("setPoisonArrowTicks")) { setPoisonArrowTicks = value; found = true; }
						if (s.equalsIgnoreCase("setPoisonBladeTicks")) { setPoisonBladeTicks = value; found = true; }
						if (s.equalsIgnoreCase("setConfusionArrowTicks")) { setConfusionArrowTicks = value; found = true; }
						if (s.equalsIgnoreCase("setBlindnessArrowTicks")) { setBlindnessArrowTicks = value; found = true; }
						if (s.equalsIgnoreCase("setFireArrowTicks")) { setFireArrowTicks = value; found = true; }
						if (s.equalsIgnoreCase("setExplosiveArrowPower")) { setExplosiveArrowPower = value; found = true; }
						if (s.equalsIgnoreCase("setFireBladeTicks")) { setFireBladeTicks = value; found = true; }
						if (s.equalsIgnoreCase("setFirePunchTicks")) { setFirePunchTicks = value; found = true; }
						if (s.equalsIgnoreCase("setInstabreakItem")) { setInstabreakItem = value; found = true; }
						if (s.equalsIgnoreCase("setGuildProtectionBarrier")) { setGuildProtectionBarrier = value; found = true; }

					} else if (instance.util.isBoolean(v) == true) {
						
						instance.log("Settings.java boolean " + s);
						boolean value = Boolean.parseBoolean(v);
						
						if (s.equalsIgnoreCase("allowLogger")) { allowLogger = value; found = true; }
						if (s.equalsIgnoreCase("allowChangeGuild")) { allowChangeGuild = value; found = true; }
						if (s.equalsIgnoreCase("allowNoGuild")) { allowNoGuild = value; found = true; }
						if (s.equalsIgnoreCase("allowGuildPVP")) { allowGuildPVP = value; found = true; }
						if (s.equalsIgnoreCase("allowBase")) { allowBase = value; found = true; }
						if (s.equalsIgnoreCase("allowBaseOnDeath")) { allowBaseOnDeath = value; found = true; }
						if (s.equalsIgnoreCase("allowChatFormat")) { allowChatFormat = value; found = true; }
						if (s.equalsIgnoreCase("allowChatColor")) { allowChatColor = value; found = true; }
						if (s.equalsIgnoreCase("allowGuildPrefix")) { allowGuildPrefix = value; found = true; }
						if (s.equalsIgnoreCase("allowGuildProtection")) { allowGuildProtection = value; found = true; }
						if (s.equalsIgnoreCase("allowMultiWorlds")) { allowMultiWorlds = value; found = true; }
						if (s.equalsIgnoreCase("allowDamageAnimationOnZero")) { allowDamageAnimationOnZero = value; found = true; }
						
					} else {
						
						instance.log("Settings.java text/other " + s);
						String value = v;
			
						if (s.equalsIgnoreCase("chatPrefix")) { chatPrefix = value; found = true; }
						if (s.equalsIgnoreCase("chatSuffix")) { chatSuffix = value; found = true; }
						
					}
					
				} else {
					instance.pluginInfo("missing parameters... /guilds settings <setting> <value>");
				}
		
		if (found == true) instance.pluginInfo("successfully set " + args[1] + " to " + args[2]);
		
	}

}
