package me.purplefishh.yourwarp.ListApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.purplefishh.yourwarp.Resorce;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;
import net.minecraft.server.v1_13_R2.IChatBaseComponent.ChatSerializer;

public class ListAPI_1_13 implements ListAPI{

	
	public void listdisply(Set<String> setkey, Player p, String listformat) {
		List<String> warplist = new ArrayList<>();
		for (String key : setkey)
			warplist.add(key);
		String s = listformat;
		s = "[{\"text\":\"" + listformat.split("%warp_list%")[0] + "\"}";
		for (int i = 0; i < warplist.size() - 1; ++i)
			s += ",{\"text\":\"" + Resorce.warplistcolor() + warplist.get(i)
					+ "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\": {\"text\":\"\",\"extra\":[{\"text\":\"§6Teleport to warp!\"}]}},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/warp "
					+ warplist.get(i) + "\"}}" + ",{\"text\":\"" + Resorce.warplistcolor() + ", " + "\"}";
		s += ",{\"text\":\"" + Resorce.warplistcolor() + warplist.get(warplist.size() - 1)
				+ "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\": {\"text\":\"\",\"extra\":[{\"text\":\"§6Teleport to warp!\"}]}},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/warp "
				+ warplist.get(warplist.size() - 1) + "\"}}";
		if (listformat.split("%warp_list%").length == 2)
			s += ",{\"text\":\"" + listformat.split("%warp_list%")[1] + "\"}";
		s += "]";
		IChatBaseComponent comp = ChatSerializer.a(s);

		PacketPlayOutChat packet = new PacketPlayOutChat(comp);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
