package me.avocardo.guilds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Guilds extends JavaPlugin {
	
	public Map <Player, Guild> onlinePlayers = new HashMap <Player, Guild>();
	
	public Map <String, Guild> offlinePlayers = new HashMap <String, Guild>();
	
	public Map <Player, Integer> tasks = new HashMap <Player, Integer>();
	
	public Map <Player, List<ItemStack>> inventory = new HashMap <Player, List<ItemStack>>();
	
	public List <Guild> guilds = new ArrayList <Guild>();
	
	public Settings settings;
	
	public Util util;
	
	public String chatManager;
	
	public boolean logging = false;
	
	public World defaultWorld = null;
	
	public final Logger logger = Logger.getLogger("Minecraft"); 
	
	public String v = "2.0.4";

	public void pluginInfo(String msg) {
		
		//System.out.println("[Guilds] " + msg);
		
	}
	
	public void log(String msg) {
		
		//if (logging == true) System.out.println("[Guilds] " + msg);
		
	}

	public void onEnable() {

		new Configuration(this);
		
		new Load(this);

		this.getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		util.hidePlayers();
		
		new Scheduler(this).save();
		
		defaultWorld = Bukkit.getServer().getWorlds().get(0);
		
		chatManager = "";
		
	}

	public void onDisable() {
		
		this.getServer().getScheduler().cancelAllTasks();

		new Save(this);

	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player)sender;
			
			if (label.equalsIgnoreCase("guilds")) { // GUILDS COMMAND
				
				if (args.length > 0) { // ARGUMENTS EXIST
					
					if (args[0].equalsIgnoreCase("list")) util.list(player, args); // TESTED
					
					if (args[0].equalsIgnoreCase("save")) { onDisable(); util.msg(player, "config saved to file..."); }
					
					if (args[0].equalsIgnoreCase("load")) { onEnable(); util.msg(player, "config saved to file..."); }
					
					if (args[0].equalsIgnoreCase("settings")) settings.set(player, args); // TESTED
					
					if (args[0].equalsIgnoreCase("traits")) util.traits(player, args); // TESTED
						
					if (args[0].equalsIgnoreCase("abilities")) util.abilities(player, args); // TESTED
					
					if (args[0].equalsIgnoreCase("restrict")) util.restrict(player, args); // TESTED
					
					if (args[0].equalsIgnoreCase("perms")) util.restrict(player, args); // TESTED
					
					if (args[0].equalsIgnoreCase("join")) util.join(player, args); // TESTED
					
					if (args[0].equalsIgnoreCase("add")) util.add(player, args); // TESTED
					
					if (args[0].equalsIgnoreCase("kick")) util.kick(player, args); // TESTED
					
					if (args[0].equalsIgnoreCase("create")) util.create(player, args); // TESTED
					
					if (args[0].equalsIgnoreCase("remove")) util.remove(player, args); // TESTED
					
					if (args[0].equalsIgnoreCase("base")) new Base(this).base(player); // TESTED
					
					if (args[0].equalsIgnoreCase("setbase")) new Base(this).setbase(player, player.getLocation(), args); // TESTED
					
					for (Guild g : guilds) {
						if (args[0].equalsIgnoreCase(g.getName())) {
							if (args.length > 2) {
								String str = args[2];
								if (args[1].equalsIgnoreCase("biome")) {
									g.addBiome(str);
								}
								if (args[1].equalsIgnoreCase("prefix")) {
									g.setPrefix(str);
								}
								if (args[1].equalsIgnoreCase("suffix")) {
									g.setSuffix(str);
								}
								if (args[1].equalsIgnoreCase("chatprefix")) {
									g.setChatPrefix(str);
								}
							}
						}
					}
					
					if (args[0].equalsIgnoreCase("help")) {
						
						player.sendMessage(ChatColor.YELLOW + "=============== Guilds v2.0.4 ===============");
						player.sendMessage(ChatColor.AQUA + "/guilds list " + ChatColor.YELLOW + ": list all guilds.");
						player.sendMessage(ChatColor.AQUA + "/guilds list <guild> " + ChatColor.YELLOW + ": list online members.");
						player.sendMessage(ChatColor.AQUA + "/guilds <guild> <biome/prefix/suffix/chatprefix> <value>");
						player.sendMessage(ChatColor.AQUA + "/guilds save " + ChatColor.YELLOW + ": save to file.");
						player.sendMessage(ChatColor.AQUA + "/guilds load " + ChatColor.YELLOW + ": load from file.");
						player.sendMessage(ChatColor.AQUA + "/guilds join <guild> " + ChatColor.YELLOW + ": join guild.");
						player.sendMessage(ChatColor.AQUA + "/guilds kick <player> " + ChatColor.YELLOW + ": kick player from guild.");
						player.sendMessage(ChatColor.AQUA + "/guilds add <guild> <player> " + ChatColor.YELLOW + ": add player to guild.");
						player.sendMessage(ChatColor.AQUA + "/guilds create <guild> " + ChatColor.YELLOW + ": create guild.");
						player.sendMessage(ChatColor.AQUA + "/guilds remove <guild> " + ChatColor.YELLOW + ": remove guild.");
						player.sendMessage(ChatColor.AQUA + "/guilds settings <setting> <value> " + ChatColor.YELLOW + ": set a setting.");
						player.sendMessage(ChatColor.AQUA + "/guilds restrict <guild> <itemid> " + ChatColor.YELLOW + ": restrict item for guild.");
						player.sendMessage(ChatColor.AQUA + "/guilds triats <guild> <trait> <value> " + ChatColor.YELLOW + ": set trait for guild.");
						player.sendMessage(ChatColor.AQUA + "/guilds abilities <guild> <ability> <value> " + ChatColor.YELLOW + ": set ability for guild.");
						player.sendMessage(ChatColor.AQUA + "/guilds perms <guild> <permission> " + ChatColor.YELLOW + ": add permission to guild.");
						player.sendMessage(ChatColor.AQUA + "/base (guild) " + ChatColor.YELLOW + ": tp to guild base.");
						player.sendMessage(ChatColor.AQUA + "/setbase (guild) " + ChatColor.YELLOW + ": set location for guild base.");
						player.sendMessage(ChatColor.YELLOW + "=============== Guilds v2.0.4 ===============");
						
					}

				} else {
					
					player.sendMessage(ChatColor.YELLOW + "=============== Guilds v2.0.4 ===============");
					player.sendMessage(ChatColor.AQUA + "/guilds list " + ChatColor.YELLOW + ": list all guilds.");
					player.sendMessage(ChatColor.AQUA + "/guilds list <guild> " + ChatColor.YELLOW + ": list online members.");
					player.sendMessage(ChatColor.AQUA + "/guilds <guild> <biome/prefix/suffix/chatprefix> <value>");
					player.sendMessage(ChatColor.AQUA + "/guilds save " + ChatColor.YELLOW + ": save to file.");
					player.sendMessage(ChatColor.AQUA + "/guilds load " + ChatColor.YELLOW + ": load from file.");
					player.sendMessage(ChatColor.AQUA + "/guilds join <guild> " + ChatColor.YELLOW + ": join guild.");
					player.sendMessage(ChatColor.AQUA + "/guilds kick <player> " + ChatColor.YELLOW + ": kick player from guild.");
					player.sendMessage(ChatColor.AQUA + "/guilds add <guild> <player> " + ChatColor.YELLOW + ": add player to guild.");
					player.sendMessage(ChatColor.AQUA + "/guilds create <guild> " + ChatColor.YELLOW + ": create guild.");
					player.sendMessage(ChatColor.AQUA + "/guilds remove <guild> " + ChatColor.YELLOW + ": remove guild.");
					player.sendMessage(ChatColor.AQUA + "/guilds settings <setting> <value> " + ChatColor.YELLOW + ": set a setting.");
					player.sendMessage(ChatColor.AQUA + "/guilds restrict <guild> <itemid> " + ChatColor.YELLOW + ": restrict item for guild.");
					player.sendMessage(ChatColor.AQUA + "/guilds triats <guild> <trait> <value> " + ChatColor.YELLOW + ": set trait for guild.");
					player.sendMessage(ChatColor.AQUA + "/guilds abilities <guild> <ability> <value> " + ChatColor.YELLOW + ": set ability for guild.");
					player.sendMessage(ChatColor.AQUA + "/guilds perms <guild> <permission> " + ChatColor.YELLOW + ": add permission to guild.");
					player.sendMessage(ChatColor.AQUA + "/base (guild) " + ChatColor.YELLOW + ": tp to guild base.");
					player.sendMessage(ChatColor.AQUA + "/setbase (guild) " + ChatColor.YELLOW + ": set location for guild base.");
					player.sendMessage(ChatColor.YELLOW + "=============== Guilds v2.0.4 ===============");
					
				}
				
				return true;

			}
			
			if (label.equalsIgnoreCase("base")) { // BASE COMMAND
				
				new Base(this).base(player); // TESTED
				
				return true;
				
			}
			
			if (label.equalsIgnoreCase("setbase")) { // SETBASE COMMAND
				
				new Base(this).setbase(player, player.getLocation(), args); // TESTED
				
				return true;
				
			}
			
			if (label.equalsIgnoreCase("gc")) { // SETBASE COMMAND
				
				new Chat(this).chat(player, args); // TESTED
				
				return true;
				
			}
			
		} else {
			
			if (args[0].equalsIgnoreCase("list")) new Console(this).list(args);
			
			if (args[0].equalsIgnoreCase("save")) { onDisable(); pluginInfo("config saved to file..."); }
			
			if (args[0].equalsIgnoreCase("load")) { onEnable(); pluginInfo("config saved to file..."); }
			
			if (args[0].equalsIgnoreCase("settings")) settings.set(args);
			
			if (args[0].equalsIgnoreCase("traits")) new Console(this).traits(args);
			
			if (args[0].equalsIgnoreCase("abilities")) new Console(this).abilities(args);
			
			if (args[0].equalsIgnoreCase("kick")) new Console(this).kick(args);
			
			if (args[0].equalsIgnoreCase("remove")) new Console(this).remove(args);
			
			if (args[0].equalsIgnoreCase("add")) new Console(this).add(args);
			
			if (args[0].equalsIgnoreCase("create")) new Console(this).create(args);
			
			if (args[0].equalsIgnoreCase("restrict")) new Console(this).restrict(args);
			
			if (args[0].equalsIgnoreCase("perms")) new Console(this).permissions(args);
			
			for (Guild g : guilds) {
				if (g.getName().equalsIgnoreCase(args[0])) {
					g.sendLog();
				}
			}
			
			if (args.length > 2) {
				for (Guild g : guilds) {
					if (args[0].equalsIgnoreCase(g.getName())) {
						String str = args[2];
						if (args[1].equalsIgnoreCase("biome")) {
							g.addBiome(str);
						}
						if (args[1].equalsIgnoreCase("prefix")) {
							g.setPrefix(str);
						}
						if (args[1].equalsIgnoreCase("suffix")) {
							g.setSuffix(str);
						}
						if (args[1].equalsIgnoreCase("chatprefix")) {
							g.setChatPrefix(str);
						}
					}
				}
			}
			
			return true;
			
		}
		
		return false;
		
	}
	
}
