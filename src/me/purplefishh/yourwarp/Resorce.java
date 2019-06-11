package me.purplefishh.yourwarp;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Resorce {

	private static Plugin plugin = Main.plugin();
	private static FileConfiguration config = plugin.getConfig();

	private static String colorconv(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	private static String repwarp_name(String s, String warp) {
		s = s.replaceAll("%warp_name%", warp);
		return s;
	}

	public static String permission() {
		return colorconv(config.getString("permission"));
	}

	// create
	public static String createargs() {
		return colorconv(config.getString("createargs"));
	}

	public static String warpargs() {
		return colorconv(config.getString("warpargs"));
	}

	public static String existingwarpname() {
		return colorconv(config.getString("existingwarpname"));
	}

	public static String warpcreate(String warp_name) {
		return repwarp_name(colorconv(config.getString("warpcreate")), warp_name);
	}

	// teleport
	public static String nonexwarpteleport() {
		return colorconv(config.getString("non-exwarpteleport"));
	}

	public static String warpteleport(String warp_name) {
		return repwarp_name(colorconv(config.getString("warpteleport")), warp_name);
	}

	// list
	public static String warplistowner() {
		return colorconv(config.getString("warplistowner"));
	}

	public static String warplistglobal() {
		return colorconv(config.getString("warplistglobal"));
	}

	public static String warplistcolor() {
		return colorconv(config.getString("warplistcolor"));
	}

	// delete
	public static String deleteargs() {
		return colorconv(config.getString("deleteargs"));
	}

	public static String nonexwarpdelete() {
		return colorconv(config.getString("non-exwarpdelete"));
	}

	public static String successdelete() {
		return colorconv(config.getString("successdelete"));
	}


	
}
