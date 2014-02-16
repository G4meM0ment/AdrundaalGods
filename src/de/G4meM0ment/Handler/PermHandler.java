package de.G4meM0ment.Handler;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.G4meM0ment.Framework.God.Praying;

public class PermHandler {
	
	/**
	 * @param p
	 * @return
	 */
	public static boolean hasUserPerm(Player p) {
		if(p.hasPermission("adrundaalgods.use"))
			return true;
		return false;
	}
	
	public static boolean hasPrayingPerm(Player p, Praying praying) {
		
		if(p.hasPermission("adrundaalgods.praying.*"))
			return true;
		
		if(praying == null) return false;
		if(p.hasPermission("adrundaalgods.praying."+praying.getName()))
			return true;
		
		return false;
	}
	
	public static boolean hasAdminPerm(Player p) {
		if(p.hasPermission("adrundaalgods.admin"))
			return true;
		return false;
	}
	
	/**
	 * Check for the given string permission
	 * @param player
	 * @param permission
	 * @return
	 */
	public static boolean hasPerm(Player player, String permission) {
		if(player == null || permission == null) return false;
		if(permission.isEmpty()) return false;
		if(player.hasPermission(permission))
			return true;
		return false;
	}	
	/**
	 * Check for the given string permission
	 * @param sender
	 * @param permission
	 * @return
	 */
	public static boolean hasPerm(CommandSender sender, String permission) {
		if(sender == null || permission == null) return false;
		if(permission.isEmpty()) return false;
		if(!(sender instanceof Player)) return true;
		if(sender.hasPermission(permission))
			return true;
		return false;
	}

}
