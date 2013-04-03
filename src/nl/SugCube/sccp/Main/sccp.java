package nl.SugCube.sccp.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import nl.SugCube.sccp.Count.CountLeap;
import nl.SugCube.sccp.Count.RecycleClear;
import nl.SugCube.sccp.Listeners.ChatListener;
import nl.SugCube.sccp.Listeners.PlayerListener;
import nl.SugCube.sccp.Listeners.ServerListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class sccp extends JavaPlugin {
	
	public final Logger logger = Logger.getLogger("Minecraft");
	public static sccp plugin;
	public static int recycleLoop = 0;
	public static int recycleSecondsLeft;
	public static boolean notifyRecycle = false;
	public static int configRecycleInterval;
	public static int leapItemId;
	public static boolean leapItem;
	
	//ArrayList containing cooldown-players LEAP-command
	public static List<Player> canLeap = new ArrayList<Player>();
	
	//ArrayList containing muted-players
	public static List<Player> mutedPlayers = new ArrayList<Player>();
	
	//ArrayList containing frozen-players
	public static List<Player> frozenPlayers = new ArrayList<Player>();
	
	//Calling supporting classes
	public CountLeap cl = new CountLeap();
	public PlayerListener pll = new PlayerListener();
	public ChatListener chl = new ChatListener();
	public ServerListener svl = new ServerListener();
	public Methods m = new Methods(this);
	public static Inventory recycleBin;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("[" + pdfFile.getName() + "] " + pdfFile.getVersion() + " Has Been Disabled");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("[" + pdfFile.getName() + "] " + pdfFile.getVersion() + " Has Been Enabled");
		this.logger.info("[" + pdfFile.getName() + "] " + "Plugin made by MrSugarCaney");
		
		//Create config-file
		File file = new File(getDataFolder() + File.separator + "config.yml");
		if (!file.exists()) {
			this.logger.info("[" + pdfFile.getName() + "] " + "Generating config.yml");
			getConfig().options().copyDefaults(true);
			saveConfig();
			this.logger.info("[" + pdfFile.getName() + "] " + "config.yml succesfully generated!");
			this.logger.info("[" + pdfFile.getName() + "] " + "Visit the Bukkit DEV page for config-help.");
		}
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(pll, this);
		pm.registerEvents(chl, this);
		pm.registerEvents(svl, this);
		
		recycleBin = Bukkit.createInventory(null, getConfig().getInt("recycle.size"), getConfig().getString("recycle.bin-name"));
		
		//Auto-empty recycle loop
		if (getConfig().getBoolean("recycle.auto-empty")) {
			configRecycleInterval = getConfig().getInt("recycle.empty-interval") * 60;
			recycleSecondsLeft = configRecycleInterval;
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new RecycleClear(), 20, 20);
			notifyRecycle = getConfig().getBoolean("recycle.notify-empty");
		}
		
		leapItemId = getConfig().getInt("leap.item-id");
		leapItem = getConfig().getBoolean("leap.item-enabled");
		pll.setLeapItemId(leapItemId);
		pll.setLeapItem(leapItem);
		pll.setDeleteLeap(getConfig().getBoolean("leap.consumes-item"));
		svl.setPing(getConfig().getString("motd.motd"));
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (label.equalsIgnoreCase("sccp")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				Help.setLeapId(leapItemId);
				Help.setLeapItem(leapItem);
				Help.showHelp(player);
			} else {
				System.out.println("Only In-game Players can perform this command!");
			}
		}
		
		else if (label.equalsIgnoreCase("recycle")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("sccp.recycle")) {
					player.openInventory(recycleBin);
				} else {
					player.sendMessage(ChatColor.RED + "[!!] You do not have permission to perform this command!");
				}
			} else {
				System.out.println("Only In-game Players can perform this command!");
			}
		}
		
		else if (label.equalsIgnoreCase("drunk")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("sccp.drunk")) {
					if (args.length == 0) {
						Methods.giveNausea(player, 60);
					} else if (args.length == 1) {
						Player target = getServer().getPlayer(args[0]);
						Methods.giveNausea(target, 60);
					} else if (args.length == 2) {
						Player target = getServer().getPlayer(args[0]);
						Methods.giveNausea(target, Integer.parseInt(args[1]));
					}
				}
			} else {
				System.out.println("Only In-game Players can perform this command!");
			}
		}
		
		else if (label.equalsIgnoreCase("firework")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("sccp.firework")) {
					Methods.shootFirework();
				}
			} else {
				System.out.println("Only In-game Players can perform this command!");
			}
		}
		
		else if (label.equalsIgnoreCase("mutes")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("sccp.mute")) {
					if (args.length == 0) {
						Help.muteHelp(player);
					} else {
						Player target = getServer().getPlayer(args[0]);
						if (mutedPlayers.contains(target)) {
							//Remove target from the mute-list
							mutedPlayers.remove(target);
							player.sendMessage(ChatColor.GREEN + target.getName() + " has been unmuted!");
							target.sendMessage(ChatColor.GREEN + "You have been unmuted by " + player.getName());
						} else {
							//Adds target to the mute-list
							mutedPlayers.add(target);
							player.sendMessage(ChatColor.GREEN + target.getName() + " has been muted!");
							target.sendMessage(ChatColor.RED + "You have been muted by " + player.getName());
						}
					}
				} else {
					player.sendMessage(ChatColor.RED + "[!!] You do not have permission to perform this command!");
				}
			} else {
				System.out.println("Only In-game Players can perform this command!");
			}
		}
		
		else if (label.equalsIgnoreCase("leap")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("sccp.leap")) {
					if (!canLeap.contains(player)) {
						//Adds to canLeap delay-list
						canLeap.add(player);
						//Starts cooldown-thread
						m.doLeap(player);
						cl.setPlayer(player);
						new Thread(cl).start();
					}
				} else {
					player.sendMessage(ChatColor.RED + "[!!] You do not have permission to perform this command!");
				}
			} else {
				System.out.println("Only In-game Players can perform this command!");
			}
		}
		
		else if (label.equalsIgnoreCase("shoot")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("sccp.shoot")) {
					if (args.length == 0) {
						m.doShoot(player);
					} else {
						try {
							Player target = getServer().getPlayer(args[0]);
							if (target.isOnline()) {
								m.doShoot(target);
							} else {
								player.sendMessage(ChatColor.RED + "[!!] That player is offline!");
							}
						} catch (Exception e) {
							player.sendMessage(ChatColor.RED + "[!!] That player is offline!");
						}
					}
				} else {
					player.sendMessage(ChatColor.RED + "[!!] You do not have permission to perform this command!");
				}
			} else {
				System.out.println("Only In-game Players can perform this command!");
			}
		}
		
		return false;
	}
	
	public static void clearRecycle() {
		recycleBin.clear();
		recycleSecondsLeft = configRecycleInterval;
		if (notifyRecycle) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "Recycle Bin has been cleared!");
		}
	}
	
}
