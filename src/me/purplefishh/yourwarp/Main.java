package me.purplefishh.yourwarp;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.purplefishh.yourwarp.ListApi.ListAPI;
import me.purplefishh.yourwarp.ListApi.ListAPI_1_10;
import me.purplefishh.yourwarp.ListApi.ListAPI_1_11;
import me.purplefishh.yourwarp.ListApi.ListAPI_1_12;
import me.purplefishh.yourwarp.ListApi.ListAPI_1_13;
import me.purplefishh.yourwarp.ListApi.ListAPI_1_14;
import me.purplefishh.yourwarp.ListApi.ListAPI_1_8;
import me.purplefishh.yourwarp.ListApi.ListAPI_1_8_8;
import me.purplefishh.yourwarp.ListApi.ListAPI_1_9;



public class Main extends JavaPlugin {

	private static FileConfiguration configcoord;
	private static File coordfile;
	
	private static Plugin pl;
	public static ListAPI list;

	@Override
	public void onEnable() {
		if (setupAPI()) {
			System.out.println("[YourWarpLite] Enable!");
		} else {
			getLogger().severe("[YourWarpLite] Failed to setup!");
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED
					+ "[YourWarpLite] The plugin was stpoed! Verfy the server version!");

			Bukkit.getPluginManager().disablePlugin(this);
		}
		saveDefaultConfig();
				
		getCommand("warp").setExecutor(new Warp());
		getCommand("warphelp").setExecutor(new WarpHelp());
		try {
			RegisterConfigCoord();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pl = this;

	}

	@Override
	public void onDisable() {
		System.out.println("[YourWarpLite] Disable!");

	}

	public static FileConfiguration configcoord() {
		return configcoord;
	}

	public static Plugin plugin() {
		return pl;
	}

	
	private boolean setupAPI() {
		String version;
		try {
			version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		} catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
			return false;
		}
		getLogger().info("Your server is running version " + version);

		if (version.equals("v1_14_R1")) {
			list = new ListAPI_1_14();
		}else if (version.equals("v1_13_R2")) {
			list = new ListAPI_1_13();
		}else if (version.equals("v1_12_R1")) {
			list = new ListAPI_1_12();
		}else if (version.equals("v1_11_R1")) {
			list = new ListAPI_1_11();
		}else if (version.equals("v1_10_R1")) {
			list = new ListAPI_1_10();
		} else if (version.equals("v1_9_R1")) {
			list = new ListAPI_1_9();
		}if (version.equals("v1_8_R1")) {
			list = new ListAPI_1_8();
		}if (version.equals("v1_8_R3")) {
			list = new ListAPI_1_8_8();
		}

		return list != null;
	}

	private void RegisterConfigCoord() throws IOException {
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
		coordfile = new File(getDataFolder(), "configcoord.yml");
		if (!coordfile.exists()) {
			try {
				coordfile.createNewFile();
			} catch (IOException e) {
				System.out.println("Can't create configcoord.yml");
			}
		}
		configcoord = YamlConfiguration.loadConfiguration(coordfile);
		configcoord.addDefault("lastid", 0);
		if(!configcoord.isConfigurationSection("warp"))
			configcoord.createSection("warp");
		configcoord.options().copyDefaults(true);
		configcoord.save(coordfile);
		SaveCoordConfig();
	}
	public static void SaveCoordConfig()
	{
		try {
			configcoord.save(coordfile);
		} catch (IOException e) {
			System.out.println("Can't save configcoord.yml");
		}
	}
}
