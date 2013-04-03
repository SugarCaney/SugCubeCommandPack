package nl.SugCube.sccp.Listeners;

import nl.SugCube.sccp.Main.sccp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	
	//MUTES-command: Check muted
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = (Player) event.getPlayer();
		if (sccp.mutedPlayers.contains(player)) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "[!!] You have been muted! You can't talk!");
		}
	}
}
