package nl.SugCube.sccp.Count;

import nl.SugCube.sccp.Main.sccp;

public class RecycleClear implements Runnable {
	
	public void run() {
		
		if (sccp.recycleSecondsLeft > 0) {
			sccp.recycleSecondsLeft--;
		} else {
			sccp.clearRecycle();
		}
	}
		
}