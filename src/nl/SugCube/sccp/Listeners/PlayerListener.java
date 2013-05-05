package nl.SugCube.sccp.Listeners;

import nl.SugCube.sccp.Count.CountLeap;
import nl.SugCube.sccp.Count.Stopwatch;
import nl.SugCube.sccp.Main.Methods;
import nl.SugCube.sccp.Main.sccp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
	
	public static sccp plugin;
	
	public PlayerListener(sccp instance) {
		plugin = instance;
	}
	
	public boolean leapItem;
	public int leapItemId;
	public Methods m = new Methods(plugin);
	public CountLeap cl = new CountLeap();
	public static boolean deleteLeap = false;
	
	public void setLeapItem(boolean b) {
		leapItem = b;
	}
	
	public void setLeapItemId(int i) {
		leapItemId = i;
	}
	
	public void setDeleteLeap(boolean b) {
		deleteLeap = b;
	}
	
	//STOPWATCH-alternative stop
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
		if (sccp.swActionDeath.contains(player)) {
        	sccp.swPlayers.remove(player);
        	sccp.swActionDeath.remove(player);
        }
	}
	
	//STOPWATCH-command
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Stopwatch(player), 2, 2);
	}
	
	//LEAP-command: Item-use
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		int blockId = player.getItemInHand().getType().getId();
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (blockId == leapItemId && leapItem && player.hasPermission("sccp.leap")) {
				if (!sccp.canLeap.contains(player)) {
					if (deleteLeap) {
						player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
					}
					sccp.canLeap.add(player);
					m.doLeap(player);
					cl.setPlayer(player);
					new Thread(cl).start();
					event.setCancelled(true);
				}
			}
			if (sccp.swActionLeftClick.contains(player)) {
				sccp.swPlayers.remove(player);
				sccp.swActionLeftClick.remove(player);
	        }
		} else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (sccp.swActionRightClick.contains(player)) {
				sccp.swPlayers.remove(player);
				sccp.swActionRightClick.remove(player);
	        }
		}
	}
	
	//LEAP-command: Disable fall-damages
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (event.getCause() == DamageCause.FALL) {
        	//Check if player actually performed /leap
        	if (sccp.canLeap.contains(player)) {
        		event.setCancelled(true);
        	}
        	if (sccp.swActionDamage.contains(player)) {
            	sccp.swPlayers.remove(player);
            	sccp.swActionDamage.remove(player);
            }
        }
        if (sccp.swActionDamage.contains(player)) {
        	sccp.swPlayers.remove(player);
        	sccp.swActionDamage.remove(player);
        }
	}
	
}
	
