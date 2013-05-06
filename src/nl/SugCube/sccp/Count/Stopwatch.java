package nl.SugCube.sccp.Count;

import org.bukkit.entity.Player;

import nl.SugCube.sccp.Main.Methods;
import nl.SugCube.sccp.Main.sccp;

public class Stopwatch implements Runnable {

	public static sccp plugin;
	private Player player;
	public static int count = -60;
	public boolean sendMessage = false;
	
	public Stopwatch(Player p) {
		player = p;
		count = -60;
	}
	
	public static int getCount() {
		return count;
	}
	
	public void run() {
		if (sccp.swPlayers.contains(player)) {			
			if (!sendMessage) {
				sendMessage = true;
				count = -60;
			}
			count++;
			
			if (count < 0 && count % 10 == 0) {
				player.sendMessage(Methods.setColors("&a[&eStopwatch&a] Start in &e" + ((count / 10)) * -1));
			} else if (count == 0) {
				player.sendMessage(Methods.setColors("&a[&eStopwatch&a] Start!"));
			}
			
			if (player.getFoodLevel() <= 0) {
				if (sccp.swActionStarve.contains(player)) {
					sccp.swPlayers.remove(player);
		        	sccp.swActionStarve.remove(player);
		        }
			}
		} else {
			if (sendMessage) {				
				if (count > 0) {
					player.sendMessage(Methods.setColors("&a[&eStopwatch&a] Time: &e" + (count / 10) + "," + (count % 10) + "&a seconds"));
				} else {
					player.sendMessage(Methods.setColors("&a[&eStopwatch&a] Timing cancelled!"));
				}
				sendMessage = false;
			}
		}
	}
}
