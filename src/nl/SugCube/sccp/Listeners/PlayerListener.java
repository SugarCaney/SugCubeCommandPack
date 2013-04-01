package nl.SugCube.sccp.Listeners;

import nl.SugCube.sccp.Count.CountLeap;
import nl.SugCube.sccp.Main.Methods;
import nl.SugCube.sccp.Main.sccp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {
	
	public static sccp plugin;
	
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
        }
	}
	
}
	
