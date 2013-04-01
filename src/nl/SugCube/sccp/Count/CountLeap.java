package nl.SugCube.sccp.Count;

import nl.SugCube.sccp.Main.sccp;

import org.bukkit.entity.Player;

public class CountLeap implements Runnable {

	public Player player1 = null;
	
	public void setPlayer(Player pname) {
		this.player1 = pname;
	}
	
	public void run() {
		try {
			//LEAP-command: set command-cooldown
			Thread.sleep(2000);
			sccp.canLeap.remove(player1);
		} catch (Exception e) {
			sccp.canLeap.remove(player1);
		}
	}

}
