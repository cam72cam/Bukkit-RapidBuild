package me.cmesh.RapidBuilder;


import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class RapidBuilderPlayerListener implements Listener 
{
	public static RapidBuilder plugin;
    public RapidBuilderPlayerListener(RapidBuilder instance)
    {
        plugin = instance;
    }
	
    @EventHandler
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
