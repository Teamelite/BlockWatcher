package io.teamelite;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	public static HashMap<Material, String> permissions = new HashMap<Material, String>();
	
	public static HashMap<Location, Integer> transfers = new HashMap<Location, Integer>();
	
	public void onEnable() {
		
		String redstonepermission = "build.redstone.";
		
		permissions.put(Material.REDSTONE_WIRE, redstonepermission+"wire");
		permissions.put(Material.REDSTONE, redstonepermission+"redstone");
//		permissions.put(Material.REDSTONE_BLOCK, redstonepermission+"block");
		permissions.put(Material.REDSTONE_COMPARATOR, redstonepermission+"comperator");
		permissions.put(Material.REDSTONE_COMPARATOR_ON, redstonepermission+"comperator_on");
		permissions.put(Material.REDSTONE_COMPARATOR_OFF, redstonepermission+"comperator_off");
//		permissions.put(Material.REDSTONE_LAMP_OFF, redstonepermission+"lamp_off");
//		permissions.put(Material.REDSTONE_LAMP_ON, redstonepermission+"lamp_on");
//		permissions.put(Material.REDSTONE_ORE, redstonepermission+"ore");
//		permissions.put(Material.REDSTONE_TORCH_OFF, redstonepermission+"torch_off");
//		permissions.put(Material.REDSTONE_TORCH_ON, redstonepermission+"torch_on");
		permissions.put(Material.DIODE, redstonepermission+"diode");
		permissions.put(Material.DIODE_BLOCK_ON, redstonepermission+"diode_on");
		permissions.put(Material.DIODE_BLOCK_OFF, redstonepermission+"diode_off");
		
		getServer().getPluginManager().registerEvents(new Events(), this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("hopper-info")){
			if(sender.hasPermission("hopperinfo")){
				int amount;
				if(args.length==0){
					amount = 10;
				} else{
					amount = Integer.valueOf(args[0]);
				}
				if(amount==0||0==transfers.keySet().size()){
					sender.sendMessage("[BlockWatcher] No hoppers are logged!");
					return true;
				}
				if(amount>transfers.keySet().size()){
					amount = transfers.keySet().size();
					sender.sendMessage("[BlockWatcher] Only "+amount+" hoppers are logged.");
				}
				ArrayList<Integer> amounts = new ArrayList<Integer>();
				for(Location l : transfers.keySet()){
					amounts.add(transfers.get(l));
				}
				amounts = quicksort(amounts);
				sender.sendMessage("[BlockWatcher] The first "+amount+" hoppers with the most transfers during this runtime:");
				int x = 0;
				while(x<amount){
					for(Location l : transfers.keySet()){
						if(transfers.get(l)==amounts.get(amounts.size()-x-1)){
							sender.sendMessage((x+1)+": "+l.getX()+", "+l.getY()+", "+l.getZ()+" with "+transfers.get(l)+" transfers.");
							amounts.set(amounts.size()-x-1, -1);
							x++;
							break;
						}
					}
				}
				return true;
			} else{
				sender.sendMessage("[BlockWatcher] You do not have the permission to use this command!");
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Integer> quicksort(ArrayList<Integer> tosort){
		ArrayList<Integer> less = new ArrayList<Integer>();
		ArrayList<Integer> pivot = new ArrayList<Integer>();
		ArrayList<Integer> more = new ArrayList<Integer>();
		if(tosort.size()<=1){
			return tosort;
		} else{
			int pivotInt = (tosort.get(Math.round(tosort.size()/2)));
			for(Integer i : tosort){
				if(i>pivotInt){
					more.add(i);
				} else if(i<pivotInt){
					less.add(i);
				} else{
					pivot.add(i);
				}
			}
			less = quicksort(less);
			more = quicksort(more);
			pivot.addAll(more);
			less.addAll(pivot);
			return less;
		}
	}

}
