package de.G4meM0ment.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.G4meM0ment.AGManager;
import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Framework.AGPlayer;
import de.G4meM0ment.Framework.IconMenu;
import de.G4meM0ment.Framework.Shrine;
import de.G4meM0ment.Framework.God.God;
import de.G4meM0ment.Framework.God.Praying;
import de.G4meM0ment.Messenger.Message;
import de.G4meM0ment.Messenger.Messenger;

public class PlayerHandler {

	private static AdrundaalGods plugin;
	
	private static List<AGPlayer> players = new ArrayList<AGPlayer>();
	
	public PlayerHandler(AdrundaalGods plugin) {
		PlayerHandler.plugin = plugin;
	}
	public PlayerHandler() {
		
	}
	
	public List<AGPlayer> getPlayers() {
		return players;
	}
	public void setPlayers(List<AGPlayer> list) {
		players = list;
	}
	
	public AGPlayer getAGPlayer(String pName)
	{
		for(AGPlayer agp : getPlayers())
			if(agp.getName().equalsIgnoreCase(pName))
				return agp;
		return null;
	}
	public AGPlayer addAGPlayer(String pName)
	{
		AGPlayer agp = new AGPlayer(pName, new ArrayList<Shrine>(), new HashMap<Shrine, Long>());
		getPlayers().add(agp);
		return agp;
	}
	
	/**
	 * Player choosen a praying
	 * @param player
	 * @param god
	 * @param praying
	 */
	public void prayed(final Player player, final AGPlayer agp, final God god, final Praying praying, Shrine s) {
		
		agp.setPraying(true);
		agp.getPrayed().put(s, System.currentTimeMillis());
		
		if(!agp.getFoundShrines().contains(s)) {
			Messenger.sendMessage(player, Message.foundShrine);
			agp.getFoundShrines().add(s);
		}
		
		if(ConfigHandler.removeNegativePotionEffects) {
			player.removePotionEffect(PotionEffectType.BLINDNESS);
			player.removePotionEffect(PotionEffectType.HARM);
			player.removePotionEffect(PotionEffectType.HUNGER);
			player.removePotionEffect(PotionEffectType.POISON);
			player.removePotionEffect(PotionEffectType.SLOW);
			player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
			player.removePotionEffect(PotionEffectType.SATURATION);
			player.removePotionEffect(PotionEffectType.CONFUSION);
			player.removePotionEffect(PotionEffectType.WEAKNESS);
			player.removePotionEffect(PotionEffectType.WITHER);
		}
		
		if(ConfigHandler.usePotionEffects) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, praying.getPraytime()/50, 2), false);
			player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, praying.getPraytime()/50, 2), false);
		}
		
		//run each seconds the message for the player
		final int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				if(agp.isPraying())
					Messenger.sendMessage(player, Message.timeRemaining, "%time%", "");
			}
		}, 20, 20);
		//praying finished player will get grace
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(id);
				if(agp.isPraying())
					gainedGrace(player, agp, god, praying);
				agp.setPraying(false);
			}
		}, praying.getPraytime()/50);
		
		updateBookText(player, player.getItemInHand());
	}
	
	/**
	 * After finishing praying the player gets it's benefits
	 * @param player
	 * @param god
	 * @param praying
	 */
	@SuppressWarnings("unchecked")
	public void gainedGrace(Player player, AGPlayer agp, God god, Praying praying) {
		
		//calculating if player gets grace randomly
		double random = Math.random()*100;
		if(random <= praying.getChance()) {
			
			//exp as benefit
			if(praying.getBenefit().getString("type").equalsIgnoreCase("EXP"))
				player.giveExp(praying.getBenefit().getInt("amount"));
			//cmd as benefit
			else if(praying.getBenefit().getString("type").equalsIgnoreCase("CMD"))
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), praying.getBenefit().getString("cmd").replace("%player%", player.getName()));
			//the normal item
			else {
				Map<String, Object> item = null;
				if(!(praying.getBenefit() instanceof LinkedHashMap)) {
					if(!(praying.getBenefit() instanceof MemorySection))
						plugin.getLogger().warning("Error while giving player grace benefit, item is not properly configured!");
					else {
						Map<String, Object> map = ((MemorySection) praying.getBenefit()).getValues(false);
						item = map;
					}
				}
				else
					item = (LinkedHashMap<String, Object>) praying.getBenefit();
				
				if(item == null)
					plugin.getLogger().warning("Error while giving player grace benefit, item is not properly configured!");
				else {
					try {
						player.getInventory().addItem(ItemStack.deserialize(item));
					} catch(NullPointerException e) {plugin.getLogger().warning("Error while giving player grace benefit, item is not properly configured!");}
				}
			}
			
			Messenger.sendMessage(player, Message.gainedGrace, "%god%", god.getDisplayname());
		//chance to get grace missed
		} else {
			Messenger.sendMessage(player, Message.noGrace, "%god%", god.getDisplayname());
		}
	}
	
	/**
	 * Opens the inv menu to let the player choose their praying
	 * @param p
	 * @param g
	 */
	public void openPrayMenu(final Player p, final God g, final Shrine s) {
		//Adding the player for statistics if not already registered
		AGPlayer agp = getAGPlayer(p.getName());
		if(agp == null)
			agp = addAGPlayer(p.getName());
		if(agp.isPraying()) {
			Messenger.sendMessage(p, Message.alreadyPraying);
			return;
		}
		
		//adding the inventory menu
		final AGPlayer fAGP = agp;
		IconMenu menu = new IconMenu(ConfigHandler.prayMenuTitle, g.getMenuSize(), new IconMenu.OptionClickEventHandler() {
	           @Override
	           public void onOptionClick(IconMenu.OptionClickEvent event) {
	        	   Praying praying = g.getPrayings().get(event.getPosition());
	        	   Messenger.sendMessage(event.getPlayer(), Message.choosenPraying, "%praying%", praying.getDisplayname());
	        	   prayed(event.getPlayer(), fAGP, g, praying, s);
	        	   event.setWillDestroy(true);
	        	   event.setWillClose(true);
	           }
	       }, plugin);
		 
		//adding the the items to choose the praying
		int i = 0;
		for(Praying praying : g.getPrayings()) {
			if(!PermHandler.hasPrayingPerm(p, praying)) continue;
			menu.setOption(i, new ItemStack(ConfigHandler.prayItem, 1), ChatColor.translateAlternateColorCodes('&', praying.getDisplayname()), ChatColor.translateAlternateColorCodes('&', praying.getDescription()));	 
			i++;
		}
		
		menu.open(p);
	}
	
	/**
	 * Check if the player is holding the right item while trying to pray
	 * @param p
	 * @return
	 */
	public boolean hasPrayItemInHand(Player p) {
		//material equals
		if(!p.getItemInHand().getType().equals(ConfigHandler.prayItem) || !p.getItemInHand().hasItemMeta())
			return false;
		//another check -.-
		if(!p.getItemInHand().getItemMeta().hasDisplayName())
			return false;
		//has correct name
		if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ConfigHandler.prayItemName))
			return true;
		return false;
	}
	/**
	 * Check if the player has a prayitem in his inventory
	 * @param p
	 * @return
	 */
	public boolean hasPrayItem(Player p) {
		if(getPrayItem(p) == null)
			return false;
		else
			return true;
	}
	/**
	 * Get pray item from player inventory
	 * @param p
	 * @return
	 */
	public ItemStack getPrayItem(Player p) {
		for(ItemStack i : p.getInventory()) {
			//material equals
			if(!i.getType().equals(ConfigHandler.prayItem) || !i.hasItemMeta())
				continue;
			//another check -.-
			if(!i.getItemMeta().hasDisplayName())
				continue;
			//has correct name
			if(i.getItemMeta().getDisplayName().equalsIgnoreCase(ConfigHandler.prayItemName))
				return i;
		}
		return null;
	}
	
	/**
	 * Update the praying items text if it's an written book
	 * @param p
	 */
	public void updateBookText(Player p, ItemStack item) {
		ItemStack i = item == null  ? p.getItemInHand() : item;
		
		if(!i.getType().equals(Material.WRITTEN_BOOK)) return;
		if(!(i.getItemMeta() instanceof BookMeta)) return;
		
		AGPlayer agp = getAGPlayer(p.getName());
		if(agp == null)
			agp = addAGPlayer(p.getName());
		BookMeta book = (BookMeta) i.getItemMeta();
		List<String> pages = new ArrayList<String>();
		
		pages.add(ChatColor.BOLD+"\n\n\n\n\n\n     "+stripColorCodes(ConfigHandler.prayItemName));
		pages.add("\n\n\n\n\n\nDu hast bereits "+agp.getFoundShrines().size()+" von "+AGManager.getShrineHandler().getShrines().size()+" Schreinen gefunden.");
		for(God god : AGManager.getGodHandler().getGodList()) {
			pages.add(ChatColor.BOLD+stripColorCodes(god.getDisplayname())+"\n\n"+ChatColor.RESET+stripColorCodes(god.getDescription()));
			
			for(Praying praying : god.getPrayings()) {
				pages.add(ChatColor.BOLD+stripColorCodes(praying.getDisplayname())+"\n\n"+ChatColor.RESET+stripColorCodes(praying.getDescription()));
			}
		}
		book.setPages(pages);
		i.setItemMeta(book);
	}
	private String stripColorCodes(String s) {
		return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', s));
	}
}
