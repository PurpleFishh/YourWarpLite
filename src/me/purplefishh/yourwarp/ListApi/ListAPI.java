package me.purplefishh.yourwarp.ListApi;

import java.util.Set;

import org.bukkit.entity.Player;

public abstract interface ListAPI {

	
	public abstract void listdisply(Set<String> setkey, Player p, String listformat);
}
