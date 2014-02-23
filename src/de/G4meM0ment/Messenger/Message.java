package de.G4meM0ment.Messenger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.G4meM0ment.AdrundaalGods;

public class Message {
	
	private static AdrundaalGods plugin;
	
	private static File file;
	private static FileConfiguration msgs;
	
	/*
	 * 
	 * Settings
	 * 
	 */	
	public static String wrongItem = "Du musst ein Gebetsbuch haben beten zu können!";
	public static String choosenPraying = "Du hast das Gebet des %praying% gewählt";
	public static String timeRemaining = "Du betest... %time%";
	public static String noGrace = "Du hast keine Gunst von %god% erhalten";
	public static String gainedGrace = "Du hast die Gunst von %god% erhalten";
	public static String alreadyPraying = "Du betest bereits!";
	public static String shrinesFound = "Du hast bereits %found% von %max% Schreinen entdeckt";
	public static String cantDoThat = "Du kannst das nicht tun während du betest!";
	public static String cooldown = "Du kannst erst in %min% Minuten wieder beten!";
	public static String foundShrine = "Du hast einen neuen Schrein entdeckt";
	public static String interrupted = "Du wurdest beim Beten unterbrochen";
	public static String noPermission = "Du hast keine Rechte dazu!";
	public static String playerNotFound = "Spieler nicht gefunden!";
	public static String noBuilding = "Du kannst nicht auf Schreinen bauen!";

	/*
	 * 
	 * Settings paths
	 * 
	 */
	private static String wrongItemPath = "wrongItem";
	private static String choosenPrayingPath = "choosenPraying";
	private static String timeRemainingPath = "timeRemaining";
	private static String noGracePath = "noGrace";
	private static String gainedGracePath = "gainedGrace";
	private static String alreadyPrayingPath = "alreadyPraying";
	private static String shrinesFoundPath = "shrinesFound";
	private static String cantDoThatPath = "cantDoThat";
	private static String cooldownPath = "cooldown";
	private static String foundShrinePath = "foundShrine";
	private static String interruptedPath = "interrupted";
	private static String noPermissionPath = "noPermission";
	private static String playerNotFoundPath = "playerNotFound";
	private static String noBuildingPath = "noBuilding";
	
	public Message(AdrundaalGods plugin)
	{
		Message.plugin = plugin;
		file = new File(plugin.getDir()+"/messages.yml");
	}
	
	public static void reloadFile() 
	{
		boolean configExists = configExists();
	    if(file == null) 
	    {
	    	file = new File(plugin.getDir()+"/messages.yml");
	    }
	    msgs = YamlConfiguration.loadConfiguration(file);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource("messages.yml");
	    if(!configExists && defConfigStream != null)
	    {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        msgs.setDefaults(defConfig);
	        msgs.options().copyHeader(true);
	        msgs.options().copyDefaults(true);
	        plugin.getLogger().info("Messages file created.");
	    }
	    plugin.getLogger().info("Messages file loaded.");
	}
	public static void saveFile() 
	{
	    if (msgs == null || msgs == null)
	    	return;
	    try 
	    {
	        msgs.save(file);
	    } 
	    catch (IOException ex)
	    {
	    	plugin.getLogger().log(Level.SEVERE, "Could not save data to " + file, ex);
	    }
	}
	private static boolean configExists() {
		for(File file : new File(plugin.getDir()).listFiles()) {
			if(file.getName().equalsIgnoreCase("messages.yml"))
				return true;
		}
		return false;
	}
	
	public static void loadMessages()
	{		
		wrongItem = msgs.getString(wrongItemPath);
		choosenPraying = msgs.getString(choosenPrayingPath);
		timeRemaining = msgs.getString(timeRemainingPath);
		noGrace = msgs.getString(noGracePath);
		gainedGrace = msgs.getString(gainedGracePath);
		alreadyPraying = msgs.getString(alreadyPrayingPath);
		shrinesFound = msgs.getString(shrinesFoundPath);
		cantDoThat = msgs.getString(cantDoThatPath);
		cooldown = msgs.getString(cooldownPath);
		foundShrine = msgs.getString(foundShrinePath);
		interrupted = msgs.getString(interruptedPath);
		noPermission = msgs.getString(noPermissionPath);
		playerNotFound = msgs.getString(playerNotFoundPath);
		noBuilding = msgs.getString(noBuildingPath);
	}
}
