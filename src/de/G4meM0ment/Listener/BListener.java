package de.G4meM0ment.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.G4meM0ment.AGManager;
import de.G4meM0ment.Handler.PermHandler;
import de.G4meM0ment.Messenger.Message;
import de.G4meM0ment.Messenger.Messenger;

public class BListener implements Listener {
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(AGManager.getShrineHandler().isShrine(event.getBlock().getLocation(), 0) && !PermHandler.hasAdminPerm(event.getPlayer())) {
			Messenger.sendMessage(event.getPlayer(), Message.noBuilding);
			event.setCancelled(true);
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		if(AGManager.getShrineHandler().isShrine(event.getBlock().getLocation(), 0) && !PermHandler.hasAdminPerm(event.getPlayer())) {
			Messenger.sendMessage(event.getPlayer(), Message.noBuilding);
			event.setCancelled(true);
		}
	}
}
