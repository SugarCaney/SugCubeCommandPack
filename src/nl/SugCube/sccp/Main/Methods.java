package nl.SugCube.sccp.Main;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Methods {
	
	public static sccp plugin;
	public Methods(sccp sccp) {
		plugin = sccp;
	}
	
	public static String setColors(String s) {
		s = s.replace("&0", ChatColor.BLACK + "").
		replace("&1", ChatColor.DARK_BLUE + "").
		replace("&2", ChatColor.DARK_GREEN + "").
		replace("&3", ChatColor.DARK_AQUA + "").
		replace("&4", ChatColor.DARK_RED + "").
		replace("&5", ChatColor.DARK_PURPLE + "").
		replace("&6", ChatColor.GOLD + "").
		replace("&7", ChatColor.GRAY + "").
		replace("&8", ChatColor.DARK_GRAY + "").
		replace("&9", ChatColor.BLUE + "").
		replace("&a", ChatColor.GREEN + "").
		replace("&b", ChatColor.AQUA + "").
		replace("&c", ChatColor.RED + "").
		replace("&d", ChatColor.LIGHT_PURPLE + "").
		replace("&e", ChatColor.YELLOW + "").
		replace("&f", ChatColor.WHITE + "").
		replace("&k", ChatColor.MAGIC + "").
		replace("&l", ChatColor.BOLD + "").
		replace("&m", ChatColor.STRIKETHROUGH + "").
		replace("&n", ChatColor.UNDERLINE + "").
		replace("&o", ChatColor.ITALIC + "").
		replace("&r", ChatColor.RESET + "");
		return s;
	}
	
	public static void giveNausea(Player player, int time) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (time * 20), 2));
	}
	
	public static void shootFirework() {
		//FIREWORK-command: Randomizer
		for (Player player : Bukkit.getOnlinePlayers()) {
			Firework fw = (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);
			FireworkMeta fm = fw.getFireworkMeta();
			Random r = new Random();
			Type type = null;
			int fType = r.nextInt(4) + 1;
			switch(fType) {
				default: type = Type.BALL; break;
				case 1: type = Type.BALL; break;
				case 2: type = Type.BALL_LARGE; break;
				case 3: type = Type.BURST; break;
				case 4: type = Type.CREEPER; break;
				case 5: type = Type.STAR; break;
			}
			
			int c1i = r.nextInt(15) + 1;
			int c2i = r.nextInt(15) + 1;
			Color c1 = getColor(c1i);
			Color c2 = getColor(c2i);
			FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
			fm.addEffect(effect);
			fm.setPower(r.nextInt(2) + 1);
			fw.setFireworkMeta(fm);
		}
	}

	public static Color getColor(int c) {
		//FIREWORK-command: Color randomizer
		switch (c) {
		default: return Color.LIME;
		case 1: return Color.AQUA;
		case 2: return Color.YELLOW;
		case 3: return Color.BLUE;
		case 4: return Color.FUCHSIA;
		case 5: return Color.YELLOW;
		case 6: return Color.GREEN;
		case 7: return Color.LIME;
		case 8: return Color.MAROON;
		case 9: return Color.NAVY;
		case 10: return Color.OLIVE;
		case 11: return Color.PURPLE;
		case 12: return Color.RED;
		case 13: return Color.SILVER;
		case 14: return Color.TEAL;
		case 15: return Color.ORANGE;
		case 16: return Color.YELLOW;
		}
	}
	
	public void doShoot(Player player) {
		//SHOOT-command
		player.setVelocity(new Vector(player.getVelocity().getX(), player.getVelocity().getY() + 10, player.getVelocity().getZ()));
		player.sendMessage(ChatColor.AQUA + "You have been shot into the air!");
		player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 10, 1);
	}
	
	public void doLeap(Player player) {
		//LEAP-command
		player.setVelocity(new Vector(player.getVelocity().getX(), player.getVelocity().getY() + 1.4, player.getVelocity().getZ()));
		player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 5, 1);
	}
	
}