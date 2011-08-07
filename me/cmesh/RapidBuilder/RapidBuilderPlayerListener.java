package me.cmesh.RapidBuilder;


import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.block.Action;


public class RapidBuilderPlayerListener extends PlayerListener 
{
	public static RapidBuilder plugin;
    public RapidBuilderPlayerListener(RapidBuilder instance)
    {
        plugin = instance;
    }
	
	public void onPlayerInteract(PlayerInteractEvent event)
    {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			plugin.right.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
		}
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK))
		{
			plugin.left.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
		}
	}
}
