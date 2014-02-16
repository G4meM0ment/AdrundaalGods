package de.G4meM0ment.Framework;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.G4meM0ment.Handler.ConfigHandler;

public class AGPlayer {
	
	private List<Shrine> found;
	private HashMap<Shrine, Long> prayed;
	private String name;
	private boolean praying = false;
	
	public AGPlayer(String pName, List<Shrine> found, HashMap<Shrine, Long> prayed) {
		this.name = pName;
		this.found = found;
		this.prayed = prayed;
	}
	
	public List<Shrine> getFoundShrines() {
		return found;
	}
	public void setFoundShrines(List<Shrine> found) {
		this.found = found;
	}
	/**
	 * Returns if the player has found the specified shrine
	 * @param shrine
	 * @return
	 */
	public boolean hasFoundShrine(Shrine shrine)
	{
		if(shrine == null) return false;
		if(found.contains(shrine)) return true;
		return false;
	}

	public String getName()  {
		return name;
	}
	public void setName(String player) {
		this.name = player;
	}
	public Player getPlayer() {
		return Bukkit.getPlayer(name);
	}

	/**
	 * add a new shrine, probably the player just found it
	 * @param shrine
	 */
	public void addShrine(Shrine shrine) {
		found.add(shrine);
	}
	/**
	 * remove a shrine from the list
	 * @param shrine
	 */
	public void removeShrine(Shrine shrine) {
		found.remove(shrine);
	}

	/**
	 * returns if the player is praying
	 * @return
	 */
	public boolean isPraying() {
		return praying;
	}
	public void setPraying(boolean praying) {
		this.praying = praying;
	}

	public HashMap<Shrine, Long> getPrayed() {
		return prayed;
	}
	public void setPrayed(HashMap<Shrine, Long> prayed) {
		this.prayed = prayed;
	}
	public boolean hasCooldown(Shrine s) {
		if(!prayed.containsKey(s)) return false;
		if(System.currentTimeMillis()-prayed.get(s) < ConfigHandler.shrineCooldown) return true;
		return false;
	}
}
