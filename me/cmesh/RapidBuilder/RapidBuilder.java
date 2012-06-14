package me.cmesh.RapidBuilder;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.Location;
import org.bukkit.Material;

public class RapidBuilder extends JavaPlugin
{
	public static final Logger log = Logger.getLogger("Minecraft");
	private final RapidBuilderPlayerListener playerListener = new RapidBuilderPlayerListener(this);
	
	public HashMap<UUID, Location> left = new HashMap<UUID, Location>();
	public HashMap<UUID, Location> right = new HashMap<UUID, Location>();
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(playerListener, this);
	}
	
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
    	if (commandLabel.equalsIgnoreCase("rf") || commandLabel.equalsIgnoreCase("rfill")) 
    	{
    		if (sender instanceof Player) 
    		{
    			Player player = (Player) sender;
    			
    			UUID key = player.getUniqueId();
    			
    			if(!right.containsKey(key) || !left.containsKey(key))
    			{
    				player.sendMessage("please select 2 blocks");
    				return true;
    			}
    			
    			
    			Location  location1 = right.get(key);
    			Location  location2 = left.get(key);
    			
    			
    			if(location2 != null)
    			{
	    			if(location1.getX() > location2.getX())
	    			{
	    				double temp = location2.getX();
	    				location2.setX(location1.getX());
	    				location1.setX(temp);
	    			}
	    			if(location1.getY() > location2.getY())
	    			{
	    				double temp = location2.getY();
	    				location2.setY(location1.getY());
	    				location1.setY(temp);
	    			}
	    			if(location1.getZ() > location2.getZ())
	    			{
	    				double temp = location2.getZ();
	    				location2.setZ(location1.getZ());
	    				location1.setZ(temp);
	    			}
	    			
	    			
	    			Material type = player.getItemInHand().getType();
	    			
	    			
	    			
    				for(double x = location1.getX(); x<=location2.getX();x++)
    				{
    					for(double y = location1.getY(); y<=location2.getY();y++)
    					{
    						for(double z = location1.getZ(); z <= location2.getZ();z++)
    						{
    							if(player.isOp())
    							{
    								breakBlock(player, new Location(player.getWorld(), x,y,z), type);
    							}
    							else
    							{
    							
	    							if(player.getItemInHand().getAmount() > 0)
	    							{
	    								player.setItemInHand(new ItemStack(type, player.getItemInHand().getAmount()-1));
	    								try
	    								{
	    									player.getWorld().getBlockAt(new Location(player.getWorld(), x,y,z)).setType(type);
        								}
        								catch (java.lang.ArrayIndexOutOfBoundsException e)
        								{
        									player.sendMessage("Invalid Block Type");
	    									return true;
        								}
	    								if(player.getItemInHand().getAmount() <= 0)
	    								{
	    									clearInHand(player);
	    								}
	    							}
	    							else
	    							{
	    								if(player.getInventory().contains(type))
	    								{
	    									ItemStack [] inventoryArray = player.getInventory().getContents();
	    									for(int i = 0; i<inventoryArray.length; i++)
	    									{
	    										try
	    										{
		    										if(inventoryArray[i]!=null && inventoryArray[i].getAmount() > 0  && inventoryArray[i].getType() == type)
		    										{
		    											int temp = inventoryArray[i].getAmount();
		    											player.getInventory().clear(i);
		    											
		    											player.setItemInHand(new ItemStack(type, temp-1));
		    											
		    											try
		    		    								{
		    												player.getWorld().getBlockAt(new Location(player.getWorld(), x,y,z)).setType(type);
		    		    								}
		    		    								catch (java.lang.ArrayIndexOutOfBoundsException e)
		    		    								{
		    		    									player.sendMessage("Invalid Block Type");
		    		    									return true;
		    		    								}
		    	        								
		    	        								if(player.getItemInHand().getAmount() <= 0)
		    	            							{
		    	        									clearInHand(player);
		    	            							}
		    											break;
		    										}
	    										}
	    										catch (java.lang.NullPointerException e)
	    										{
	    											player.sendMessage("Hit an exeption at "+i);
	    										}
	    									}
	    									
	    								}
	    								else
										{
	        								player.sendMessage("You ran out of blocks :(");
	        								return true;
										}
	    							}
    							}
    						}
    					}
    				}
    			}
    			player.sendMessage("Blocks Placed");
    			
    		}
    		return true;
    	}
		return false;   	
    }
	
	private void breakBlock(Player player, Location loc, Material type)
	{
		try
		{
			Block block = player.getWorld().getBlockAt(loc);
			if(type == Material.AIR && block.getType() != Material.AIR)
			{
				player.getWorld().dropItemNaturally(loc,new ItemStack(block.getType(),1));
			}
			block.setType(type);
		}
		catch (java.lang.ArrayIndexOutOfBoundsException e)
		{
			player.sendMessage("Invalid Block Type");
		}
	}
	
	
	private void clearInHand(Player player)
	{
		ItemStack [] inventoryArray = player.getInventory().getContents();
		for(int i = 0; i<inventoryArray.length; i++)
		{
			try
			{
				if(inventoryArray[i].equals(player.getItemInHand()))
				{
					player.getInventory().clear(i);
					return;
				}
			}
			catch (java.lang.NullPointerException e)
			{
				player.sendMessage("Hit an exeption at "+i);
			}
		}
		player.sendMessage("Aw Crap");
		
	}
}
