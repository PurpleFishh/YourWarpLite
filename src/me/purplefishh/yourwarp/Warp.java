package me.purplefishh.yourwarp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Warp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (!sender.hasPermission("yourwarp.warp")) {
				sender.sendMessage(Resorce.permission());
				return true;
			}
			Player p = ((Player) sender).getPlayer();
			FileConfiguration coordconfig = Main.configcoord();
			// 0 args
			if (args.length == 0) {
				if (!sender.hasPermission("yourwarp.list")) {
					sender.sendMessage(Resorce.permission());
					return true;
				}
				if (coordconfig.isConfigurationSection("warp")) {
					if (sender.hasPermission("yourwarp.list.global")
							&& coordconfig.isConfigurationSection("warp.~GLOBAL")
							&& !coordconfig.getConfigurationSection("warp.~GLOBAL").getKeys(false).isEmpty())
						Main.list.listdisply(coordconfig.getConfigurationSection("warp.~GLOBAL").getKeys(false), p,
								Resorce.warplistglobal());
					if (sender.hasPermission("yourwarp.list.owner")
							&& coordconfig.isConfigurationSection("warp." + p.getName())
							&& !coordconfig.getConfigurationSection("warp." + p.getName()).getKeys(false).isEmpty())
						Main.list.listdisply(coordconfig.getConfigurationSection("warp." + p.getName()).getKeys(false), p,
								Resorce.warplistowner());
					if (!sender.hasPermission("yourwarp.list.owner") && !sender.hasPermission("yourwarp.list.global")) {
						sender.sendMessage(Resorce.permission());
						return true;
					}
				}
				return true;
			}
			// create
			if (args[0].equalsIgnoreCase("create")) {
				if (!sender.hasPermission("yourwarp.createwarp.owner")) {
					sender.sendMessage(Resorce.permission());
					return true;
				}
				if (args.length == 1) {
					sender.sendMessage(Resorce.createargs());
					return true;
				}
				String pname = p.getName();
				if (args.length >= 3)
					if (args[2].equalsIgnoreCase("global"))
						if (!sender.hasPermission("yourwarp.createwarp.global")) {
							sender.sendMessage(Resorce.permission());
							return true;
						} else
							pname = "~GLOBAL";

				String warpname = args[1].toLowerCase();
				if (!coordconfig.getConfigurationSection("warp").getKeys(false).isEmpty()
						&& coordconfig.isConfigurationSection("warp." + pname)) {
					if (coordconfig.getConfigurationSection("warp." + pname).getKeys(false).contains(warpname)) {
						sender.sendMessage(Resorce.existingwarpname());
						return true;
					}
				}

				Location loc = p.getLocation();
				coordconfig.set("warp." + pname + "." + warpname + ".coordX", loc.getX());
				coordconfig.set("warp." + pname + "." + warpname + ".coordY", loc.getY());
				coordconfig.set("warp." + pname + "." + warpname + ".coordZ", loc.getZ());
				coordconfig.set("warp." + pname + "." + warpname + ".coordPitch", loc.getPitch());
				coordconfig.set("warp." + pname + "." + warpname + ".coordYaw", loc.getYaw());
				coordconfig.set("warp." + pname + "." + warpname + ".world", loc.getWorld().getName());

				Main.SaveCoordConfig();
				p.sendMessage(Resorce.warpcreate(warpname));
			}
			// delete
			else if (args[0].equalsIgnoreCase("delete")) {
				if (!sender.hasPermission("yourwarp.delete.owner")) {
					sender.sendMessage(Resorce.permission());
					return true;
				}
				if (args.length == 1) {
					p.sendMessage(Resorce.deleteargs());
					return true;
				}
				String warpname = args[1].toLowerCase();
				String pname = p.getName();
				if (coordconfig.getConfigurationSection("warp." + p.getName()).getKeys(false).contains(warpname)) {
					coordconfig.getConfigurationSection("warp." + pname).set(warpname, null);
					Main.SaveCoordConfig();
					p.sendMessage(Resorce.successdelete());
				} else if (coordconfig.getConfigurationSection("warp." + "~GLOBAL").getKeys(false).contains(warpname)) {
					if (!sender.hasPermission("yourwarp.delete.global")) {
						sender.sendMessage(Resorce.permission());
						return true;
					}
					coordconfig.getConfigurationSection("warp.~GLOBAL").set(warpname, null);
					Main.SaveCoordConfig();
					p.sendMessage(Resorce.successdelete());
				} else {
					p.sendMessage(Resorce.nonexwarpdelete());
					return true;
				}

			}
			// teleport
			else {
				teleport((Player) sender, coordconfig, args[0].toLowerCase());
			}

		}
		return true;
	}


	public static void teleport(Player p, FileConfiguration coordconfig, String warpname) {
		if (!p.hasPermission("yourwarp.teleport.owner")) {
			p.sendMessage(Resorce.permission());
			return;
		}
		String pname = p.getName();
		if (coordconfig.isConfigurationSection("warp.~GLOBAL") && coordconfig.getConfigurationSection("warp.~GLOBAL").getKeys(false).contains(warpname))
			if (!p.hasPermission("yourwarp.teleport.global")) {
				p.sendMessage(Resorce.permission());
				return;
			} else
				pname = "~GLOBAL";
		if ((!coordconfig.isConfigurationSection("warp." + p.getName())
				|| !coordconfig.getConfigurationSection("warp." + p.getName()).getKeys(false).contains(warpname))
				&& !coordconfig.getConfigurationSection("warp.~GLOBAL").getKeys(false).contains(warpname)) {
			Main.list.listdisply(coordconfig.getConfigurationSection("warp.~GLOBAL").getKeys(false), p, Resorce.warplistglobal());
			return;
		}
		
		Double x = coordconfig.getDouble("warp." + pname + "." + warpname + ".coordX");
		Double y = coordconfig.getDouble("warp." + pname + "." + warpname + ".coordY");
		Double z = coordconfig.getDouble("warp." + pname + "." + warpname + ".coordZ");
		float pitch = (float) coordconfig.getDouble("warp." + pname + "." + warpname + ".coordPitch");
		float yaw = (float) coordconfig.getDouble("warp." + pname + "." + warpname + ".coordYaw");
		World world = Bukkit.getWorld(coordconfig.getString("warp." + pname + "." + warpname + ".world"));
		Location loc = new Location(world, x, y, z, yaw, pitch);

		p.teleport(loc);
		p.sendMessage(Resorce.warpteleport(warpname));
	}

}
