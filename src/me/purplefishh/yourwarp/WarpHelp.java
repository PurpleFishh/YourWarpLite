package me.purplefishh.yourwarp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpHelp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("yourwarp.warphelp")) {
			sender.sendMessage(Resorce.permission());
			return true;
		}
		Player p = (Player) sender;
		p.sendMessage(ChatColor.GREEN + "      --=Your Warp Help=--      ");
		p.sendMessage(ChatColor.GOLD + "/warp" + ChatColor.YELLOW + " get a list of the warps");
		p.sendMessage(ChatColor.GOLD + "/warp create <warp_name>" + ChatColor.YELLOW + " create a warp for you");
		p.sendMessage(ChatColor.GOLD + "/warp create <warp_name> global" + ChatColor.YELLOW
				+ " create a warp for all the server");
		p.sendMessage(ChatColor.GOLD + "/warp create <warp_name>" + ChatColor.YELLOW
				+ " create a warp for you");
		p.sendMessage(ChatColor.GOLD + "/warp create <warp_name> global" + ChatColor.YELLOW
				+ " create a warp for the server");
		p.sendMessage(ChatColor.GOLD + "/warp delete <warp_name>" + ChatColor.YELLOW + " delete a warp");

		return true;
	}

}
