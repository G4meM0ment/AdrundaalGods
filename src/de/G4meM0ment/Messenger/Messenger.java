package de.G4meM0ment.Messenger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messenger {
	
	/**
	 * 
	 * @param reciever
	 * @param msg
	 */
	public static void sendMessage(Player reciever, String msg)
	{
		if(reciever == null || msg == null) return;
		
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		
		if(msg.isEmpty()) return;
		reciever.sendMessage(msg);
	}
	/**
	 * 
	 * @param p
	 * @param msg
	 * @param placeHolder
	 * @param replacemnet
	 */
	public static void sendMessage(Player reciever, String msg, String placeHolder, String replacement)
	{
		if(reciever == null || msg == null) return;
		
		msg = ChatColor.translateAlternateColorCodes('&', msg.replace(placeHolder, replacement));
		
		if(msg.isEmpty()) return;
		reciever.sendMessage(msg);
	}
	
	/**
	 * 
	 * @param reciever
	 * @param msg
	 * @param placeHolder
	 * @param replacement
	 * @param placeHolder2
	 * @param replacement2
	 */
	public static void sendMessage(Player reciever, String msg, String placeHolder, String replacement, String placeHolder2, String replacement2)
	{
		if(reciever == null || msg == null) return;
		
		msg = ChatColor.translateAlternateColorCodes('&', msg.replace(placeHolder, replacement).replace(placeHolder2, replacement2));
		
		if(msg.isEmpty()) return;
		reciever.sendMessage(msg);
	}
	
	/**
	 * 
	 * @param reciever
	 * @param msg
	 * @param placeHolder
	 * @param replacement
	 * @param placeHolder2
	 * @param replacement2
	 */
	public static void sendMessage(CommandSender reciever, String msg)
	{
		if(reciever == null || msg == null) return;
		
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		
		if(msg.isEmpty()) return;
		reciever.sendMessage(msg);
	}
	/**
	 * 
	 * @param reciever
	 * @param msg
	 * @param placeHolder
	 * @param replacement
	 * @param placeHolder2
	 * @param replacement2
	 */
	public static void sendMessage(CommandSender reciever, String msg, String placeHolder, String replacement)
	{
		if(reciever == null || msg == null) return;
		
		msg = ChatColor.translateAlternateColorCodes('&', msg.replace(placeHolder, replacement));
		
		if(msg.isEmpty()) return;
		reciever.sendMessage(msg);
	}
	/**
	 * 
	 * @param reciever
	 * @param msg
	 * @param placeHolder
	 * @param replacement
	 * @param placeHolder2
	 * @param replacement2
	 */
	public static void sendMessage(CommandSender reciever, String msg, String placeHolder, String replacement, String placeHolder2, String replacement2)
	{
		if(reciever == null || msg == null) return;
		
		msg = ChatColor.translateAlternateColorCodes('&', msg.replace(placeHolder, replacement).replace(placeHolder2, replacement2));
		
		if(msg.isEmpty()) return;
		reciever.sendMessage(msg);
	}
}
