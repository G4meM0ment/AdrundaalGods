package de.G4meM0ment.DataStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Framework.Shrine;
import de.G4meM0ment.Handler.GodHandler;
import de.G4meM0ment.Handler.ShrineHandler;

public class ShrineData {
	
	private AdrundaalGods plugin;
	private ShrineHandler sH;
	private GodHandler gH;
	
	private static File configFile;
	private static FileConfiguration config = null;
	private static String defConfig = "dropDataFileExample.yml";
	
	private static String dir;

	public ShrineData(AdrundaalGods plugin) 
	{
		this.plugin = plugin;
		sH = new ShrineHandler();
		gH = new GodHandler();
		
		dir = plugin.getDir()+"/data";
		configFile = new File(dir+"/shrines.yml");
	}

	public ShrineData() 
	{
		plugin = ((AdrundaalGods) Bukkit.getPluginManager().getPlugin("AdrundaalGods"));
		sH = new ShrineHandler();
		gH = new GodHandler();
	}
	
	public void reloadConfig() 
	{
	    if (configFile == null) 
	    {
	    	configFile = new File(dir, "/shrines.yml");
	    	plugin.getLogger().info("Created shrine file.");
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
	    plugin.getLogger().info("Shrine data loaded.");
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
		
		//remove old saves
		for(String w : config.getKeys(false))
			config.set(w, null);
		/*
		 * save cache data to file
		 */
		for(Shrine s : sH.getShrines())
		{
			String path = s.getMax().getWorld().getName()+"."+s.getName();
			config.set(path+".max.x", s.getMax().getBlockX());
			config.set(path+".max.y", s.getMax().getBlockY());
			config.set(path+".max.z", s.getMax().getBlockZ());
			
			config.set(path+".min.x", s.getMin().getBlockX());
			config.set(path+".min.y", s.getMin().getBlockY());
			config.set(path+".min.z", s.getMin().getBlockZ());
			
			config.set(path+".god", s.getGod().getName());
		}
		saveConfig();
	}
	
	/**
	 * load data to cache all not saved changes in cache are lost
	 */
	public void loadDataFromFile()
	{
		FileConfiguration config = getConfig();
		List<Shrine> shrines = new ArrayList<Shrine>();
		for(String worldName : config.getKeys(false))
		{
			World world = Bukkit.getWorld(worldName);
			for(String shrineName : config.getConfigurationSection(worldName).getKeys(false))
			{
				String path = worldName+"."+shrineName+".";
				Location max = new Location(world, config.getInt(path+"max.x"), config.getInt(path+"max.y"), config.getInt(path+"max.z"));
				Location min = new Location(world, config.getInt(path+"min.x"), config.getInt(path+"min.y"), config.getInt(path+"min.z"));
				String god = config.getString(path+".god");
				
				//the loaded data transferred into a shrine and added to it's world list
				shrines.add(new Shrine(shrineName, gH.getGod(god), max, min));
			}
		}
		sH.setShrines(shrines);
	}
}
