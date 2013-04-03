package nl.SugCube.sccp.Listeners;

import nl.SugCube.sccp.Main.Methods;
import nl.SugCube.sccp.Main.sccp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListener implements Listener {
	
	public static sccp plugin;
	public static String ping;
	
	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		event.setMotd(Methods.setColors(ping));
	}
	
	public void setPing(String s) {
		ping = s;
	}
	
}
