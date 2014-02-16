package de.G4meM0ment.DataStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Framework.AGPlayer;
import de.G4meM0ment.Framework.Shrine;
import de.G4meM0ment.Handler.PlayerHandler;
import de.G4meM0ment.Handler.ShrineHandler;

public class PlayerData {
	
	private AdrundaalGods plugin;
	private PlayerHandler pH;
	private ShrineHandler sH;
	
	private static File configFile;
	private static FileConfiguration config = null;
	private static String defConfig = "dropDataFileExample.yml";
	
	private static String dir;

	public PlayerData(AdrundaalGods plugin) 
	{
		this.plugin = plugin;
		pH = new PlayerHandler();
		sH = new ShrineHandler();
		
		dir = plugin.getDir()+"/data";
		configFile = new File(dir+"/player");
	}

	public PlayerData() 
	{
		plugin = ((AdrundaalGods) Bukkit.getPluginManager().getPlugin("AdrundaalGods"));
		pH = new PlayerHandler();
		sH = new ShrineHandler();
	}
	
	public void reloadConfig() 
	{
	    if (configFile == null) 
	    {
	    	configFile = new File(dir, "/player");
	    	plugin.getLogger().info("Created player file.");
	    }
	    config = YamlConfiguration.loadConfiguration(configFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource(defConfig);
	    if (defConfigStream != null)
	    {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        config.setDefaults(defConfig);
	        config.options().copyHeader(true);
	        config.options().copyDefaults(true);
	    }
	    plugin.getLogger().info("Player data loaded.");
	}
	public FileConfiguration getConfig() 
	{
	    if (config == null)
	        reloadConfig();
	    return config;
	}
	public void saveConfig() 
	{
	    if (config == null || configFile == null) 
	    	return;
	    
	    try 
	    {
	        config.save(configFile);
	    } catch (IOException ex) 
	    {
	    	plugin.getLogger().log(Level.SEVERE, "Could not save data to " + configFile, ex);
	    }
	}
	
	public void saveDataToFile()
	{	
		FileConfiguration config = getConfig();
		
		/*
		 * save cache data to file
		 */
		for(AGPlayer p : pH.getPlayers())
		{
				//remove old saves
				config.set(p.getName(), null);
				
				List<String> shrineNames = new ArrayList<String>();
				for(Shrine s : p.getFoundShrines())
					shrineNames.add(s.getName());
				
				config.set(p.getName()+".found", shrineNames);
				
				for(Shrine s : p.getPrayed().keySet())
					config.set(p.getName()+".prayed."+s.getName(), p.getPrayed().get(s));
		}
		saveConfig();
	}
	
	public void loadDataFromFile()
	{	
		FileConfiguration config = getConfig();
		List<AGPlayer> list = new ArrayList<AGPlayer>();
			
		for(String pName : config.getKeys(false))
		{	
			List<Shrine> shrines = new ArrayList<Shrine>();
			for(String s : config.getStringList(pName+".found")) {
				Shrine shrine = sH.getShrine(s);
				if(shrine != null)
					shrines.add(sH.getShrine(s));
			}
			
			HashMap<Shrine, Long> prayed = new HashMap<Shrine, Long>();
			if(config.getConfigurationSection(pName).contains("prayed")) {
				for(String s : config.getConfigurationSection((pName+".prayed")).getKeys(false)) {
					Shrine shrine = sH.getShrine(s);
					if(shrine != null)
						prayed.put(shrine, config.getLong(pName+".prayed."+s));
				}
			}
			
			//add new darplayer to list
			list.add(new AGPlayer(pName, shrines, prayed));	
		}
		pH.setPlayers(list);
	}
}