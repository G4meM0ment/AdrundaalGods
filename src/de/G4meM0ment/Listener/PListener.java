package de.G4meM0ment.Listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Framework.AGPlayer;
import de.G4meM0ment.Framework.Shrine;
import de.G4meM0ment.Handler.ConfigHandler;
import de.G4meM0ment.Handler.PermHandler;
import de.G4meM0ment.Handler.PlayerHandler;
import de.G4meM0ment.Handler.ShrineHandler;
import de.G4meM0ment.Messenger.Message;
import de.G4meM0ment.Messenger.Messenger;

public class PListener implements Listener {
	
	private AdrundaalGods plugin;
	private PlayerHandler pH;
	private ShrineHandler sH;
	
	private static List<String> prayingMoveCooldown = new ArrayList<String>();
	
	public PListener(AdrundaalGods plugin) {
		this.plugin = plugin;
		pH = new PlayerHandler();
		sH = new ShrineHandler();
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		//if player not has prayitem but is rightclicking a shrine, he gets a message
		if(!PermHandler.hasUserPerm(event.getPlayer())) return;
		if(!pH.hasPrayItem(event.getPlayer()) && sH.isShrine(event.getClickedBlock().getLocation(), 0)) {
			Messenger.sendMessage(event.getPlayer(), Message.wrongItem);
			event.setCancelled(true);
			return;
		}
		if(!pH.hasPrayItem(event.getPlayer())) return;
		
		AGPlayer agp = pH.getAGPlayer(event.getPlayer().getName());
		if(agp == null)
			agp = pH.addAGPlayer(event.getPlayer().getName());
		
		if(!sH.isShrine(event.getClickedBlock().getLocation(), 0)) {
			pH.updateBookText(event.getPlayer());
		} else {
			if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
			
			Shrine s = sH.getShrine(event.getClickedBlock().getLocation(), 0);
			if(agp.hasCooldown(s)) {
				Messenger.sendMessage(event.getPlayer(), Message.cooldown, "%min%", ((ConfigHandler.shrineCooldown-(System.currentTimeMillis()-agp.getPrayed().get(s)))/1000)/60+"");
				return;
			}
			
			pH.openPrayMenu(event.getPlayer(), sH.getShrine(event.getClickedBlock().getLocation(), 0).getGod(), s);
			event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		
		Player p = (Player) event.getEntity();
		AGPlayer agp = pH.getAGPlayer(p.getName());
		if(agp == null)
			return;
		
		if(agp.isPraying()) {
			agp.setPraying(false);
			if(ConfigHandler.usePotionEffects) {
				p.removePotionEffect(PotionEffectType.BLINDNESS);
				p.removePotionEffect(PotionEffectType.CONFUSION);
			}
			Messenger.sendMessage(p, Message.interrupted);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onPlayerMove(final PlayerMoveEvent event) {
		AGPlayer agp = pH.getAGPlayer(event.getPlayer().getName());
		if(agp == null)
			return;
		
		if(agp.isPraying() && ConfigHandler.disableMovement) {
			if(!prayingMoveCooldown.contains(event.getPlayer().getName())) {
				Messenger.sendMessage(event.getPlayer(), Message.cantDoThat);
				prayingMoveCooldown.add(event.getPlayer().getName());
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						prayingMoveCooldown.remove(event.getPlayer().getName());
					}
				}, 200);
			}
			
			event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		AGPlayer agp = pH.getAGPlayer(event.getPlayer().getName());
		if(agp == null)
			return;
		
		if(agp.isPraying() && ConfigHandler.disableChatAndCommands) {
			Messenger.sendMessage(event.getPlayer(), Message.cantDoThat);
			event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		AGPlayer agp = pH.getAGPlayer(event.getPlayer().getName());
		if(agp == null)
			return;
		
		if(agp.isPraying() && ConfigHandler.disableChatAndCommands) {
			Messenger.sendMessage(event.getPlayer(), Message.cantDoThat);
			event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerCraft(PrepareItemCraftEvent event)
	{
		ItemStack item = event.getRecipe().getResult();
		if(item.getType().equals(Material.WRITTEN_BOOK) && item.hasItemMeta())
			if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ConfigHandler.prayItemName))
				event.getInventory().setResult(null);	
	}
}
