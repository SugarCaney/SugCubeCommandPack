package nl.SugCube.sccp.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help {
	
	public static sccp plugin;
	public static boolean leapItem = false;
	public static int leapId = 0;
	
	public static void setLeapItem(boolean b) {
		leapItem = b;
	}
	
	public static void setLeapId(int i) {
		leapId = i;
	}
	
	//Return global help /sccp
	public static void showHelp(Player player) {
		player.sendMessage(
				ChatColor.YELLOW + "**" +
				ChatColor.GREEN + "-----=={" +
				ChatColor.YELLOW + " SugCubeCommandPack-Help " +
				ChatColor.GREEN + "}==-----" +
				ChatColor.YELLOW + "**");
		player.sendMessage(
				ChatColor.GOLD + "Plugin made by MrSugarCaney (vREC-0.2.1)");
		if (player.hasPermission("sccp.shoot")) {
			player.sendMessage(
					ChatColor.YELLOW + "/shoot [player] " +
					ChatColor.ITALIC + ChatColor.AQUA + "Shoot a player up to heaven");
		}
		if (player.hasPermission("sccp.leap")) {
			player.sendMessage(
					ChatColor.YELLOW + "/leap " +
					ChatColor.ITALIC + ChatColor.AQUA + "Make a leap");
			if (leapItem) {
				player.sendMessage(
						ChatColor.ITALIC + "" + ChatColor.AQUA + "> You can also left-click ItemID: " + leapId);
			}
		}
		if (player.hasPermission("sccp.mutes")) {
			player.sendMessage(
					ChatColor.YELLOW + "/mutes <player> " +
					ChatColor.ITALIC + ChatColor.AQUA + "Mutes a player");
		}
		if (player.hasPermission("sccp.firework")) {
			player.sendMessage(
					ChatColor.YELLOW + "/firework " +
					ChatColor.ITALIC + ChatColor.AQUA + "Shoots fireworks above all players' heads");
		}
		if (player.hasPermission("sccp.drunk")) {
			player.sendMessage(
					ChatColor.YELLOW + "/drunk <player> [seconds] " +
					ChatColor.ITALIC + ChatColor.AQUA + "Makes the selected player drunk");
		}
		if (player.hasPermission("sccp.recycle")) {
			player.sendMessage(
					ChatColor.YELLOW + "/recycle " +
					ChatColor.ITALIC + ChatColor.AQUA + "Open the global Recycle Bin");
		}
		if (player.hasPermission("sccp.stopwatch")) {
			player.sendMessage(
					ChatColor.YELLOW + "/sw <start|stop|check> [stop cause] " +
					ChatColor.ITALIC + ChatColor.AQUA + "Use stopwatch");
		}
		if (player.hasPermission("sccp.calculator")) {
			player.sendMessage(
					ChatColor.YELLOW + "/calc <number1> <operator> <number2> " +
					ChatColor.ITALIC + ChatColor.AQUA + "Calculator");
		}
	}
	
	//Return help by incorrectly using /calc
	public static void calcHelp(Player player) {
		player.sendMessage(
				ChatColor.YELLOW + "**" +
				ChatColor.GREEN + "-----=={" +
				ChatColor.YELLOW + " SugCubeCommandPack-Help " +
				ChatColor.GREEN + "}==-----" +
				ChatColor.YELLOW + "**");
		player.sendMessage(
				ChatColor.GOLD + "Usage: /calc <number1> <operator> <number2>");
		player.sendMessage(
				ChatColor.AQUA + "Possible operators:");
		player.sendMessage(Methods.setColors("&6 + &a| &fPlus"));
		player.sendMessage(Methods.setColors("&6 - &a| &fMinus"));
		player.sendMessage(Methods.setColors("&6 * &a| &fTimes"));
		player.sendMessage(Methods.setColors("&6 / &a| &fDivide"));
		player.sendMessage(Methods.setColors("&6pow &a| &fPower"));
		player.sendMessage(Methods.setColors("&6sqrt &a| &fSquare Root, 2nd number not needed"));
		player.sendMessage(
				ChatColor.AQUA + "Possible variables (number):");
		player.sendMessage(Methods.setColors("&6ans &a| &fRepresents previous answer"));
	}
	
	//Return help by incorrectly using /mutes
	public static void muteHelp(Player player) {
		player.sendMessage(
				ChatColor.YELLOW + "**" +
				ChatColor.GREEN + "-----=={" +
				ChatColor.YELLOW + " SugCubeCommandPack-Help " +
				ChatColor.GREEN + "}==-----" +
				ChatColor.YELLOW + "**");
		player.sendMessage(
				ChatColor.GOLD + "Usage: /mutes <player to (un)mute>");
	}
	
	//Return help by incorrectly using /stopwatch or /sw
	public static void stopwatchHelp(Player player) {
		player.sendMessage(
				ChatColor.YELLOW + "**" +
				ChatColor.GREEN + "-----=={" +
				ChatColor.YELLOW + " SugCubeCommandPack-Help " +
				ChatColor.GREEN + "}==-----" +
				ChatColor.YELLOW + "**");
		player.sendMessage(
				ChatColor.GOLD + "Usage: /sw <start|stop|check> [alternative stop cause]");
		player.sendMessage(
				ChatColor.AQUA + "Alternative causes (not required)");
		player.sendMessage(Methods.setColors("&6damage &f(taking damage), &6leftclick &f(left click), &6rightclick &f(right click), " +
				"&6death &f(when died), &6starve &f(no hungerpoints left), &6projectile &f(hit by projectile), " +
				"&6sprint &f(toggle sprint)"));
	}
	
}
