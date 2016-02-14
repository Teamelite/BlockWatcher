package io.teamelite;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.InventoryHolder;

public class Events implements Listener{
	
	@EventHandler
	public void onPlace(BlockPlaceEvent ev){
		if(!allowed(ev.getBlock(), ev.getPlayer())){
			ev.getPlayer().sendMessage(ChatColor.RED+"[BlockWatcher] You do not have the permission to use this Block!");
			ev.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent ev){
		if(!allowed(ev.getBlock(), ev.getPlayer())){
			ev.getPlayer().sendMessage(ChatColor.RED+"[BlockWatcher] You do not have the permission to use this Block!");
			ev.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onTouch(BlockDamageEvent ev){
		if(!allowed(ev.getBlock(), ev.getPlayer())){
			ev.getPlayer().sendMessage(ChatColor.RED+"[BlockWatcher] You do not have the permission to use this Block!");
			ev.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHopper(InventoryMoveItemEvent ev){
		if(ev.getSource().getTitle().equalsIgnoreCase("container.hopper")){
			InventoryHolder invH = ev.getSource().getHolder();
			Location l = ((Hopper)invH).getLocation();
			if(Main.transfers.containsKey(l)){
				Main.transfers.put(l, Main.transfers.get(l)+1);
			} else{
				Main.transfers.put(l, 1);
			}
		}
	}
	
	
	private Boolean allowed(Block b, Player p){
		Material m = b.getType();
		if(Main.permissions.containsKey(m)){
			if(p.hasPermission(Main.permissions.get(m))){
				return true;
			}
			return false;
		}
		return true;
		
	}
}
