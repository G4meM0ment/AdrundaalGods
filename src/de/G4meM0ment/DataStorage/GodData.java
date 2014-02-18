package de.G4meM0ment.DataStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Framework.God.God;
import de.G4meM0ment.Framework.God.Praying;
import de.G4meM0ment.Handler.GodHandler;

public class GodData {
	
	private AdrundaalGods plugin;
	private GodHandler gH;
	
	private static File configFile;
	private static FileConfiguration config = null;
	private static String defConfig = "gods.yml";
	
	private static String dir;

	public GodData(AdrundaalGods plugin) {
		this.plugin = plugin;
		gH = new GodHandler();
		
		dir = plugin.getDir();
//		configFile = new File(dir+"/gods.yml");
	}

	public GodData() {
		plugin = ((AdrundaalGods) Bukkit.getPluginManager().getPlugin("AdrundaalGods"));
		gH = new GodHandler();
	}
	
	public void reloadConfig() {
		boolean configExists = configExists();
		if (configFile == null) {
	    	configFile = new File(dir+"/gods.yml");
	    }
	    	
	    config = YamlConfiguration.loadConfiguration(configFile);
	    
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource(defConfig);
	    if (defConfigStream != null && !configExists) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			config.setDefaults(defConfig);
			config.options().copyHeader(true);
			config.options().copyDefaults(true);
			saveConfig();
			plugin.getLogger().info("Created god config.");
	    }
	    plugin.getLogger().info("Gods loaded.");
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
	private boolean configExists() {
		for(File file : new File(dir).listFiles()) {
			if(file.getName().equalsIgnoreCase("gods.yml"))
				return true;
		}
		return false;
	}
	
	public void loadDataFromFile()
	{	
		FileConfiguration config = getConfig();
		List<God> list = new ArrayList<God>();
			
		for(String gName : config.getKeys(false)) {	
		
			List<Praying> prayings = new ArrayList<Praying>();
			for(String pName : config.getConfigurationSection(gName+".prayings").getKeys(false)) {
				String path = gName+".prayings."+pName;
				prayings.add(new Praying(pName, config.getString(path+".displayname"), config.getString(path+".description"), config.getDouble(path+".chance"), config.getInt(path+".praytime"), config.getConfigurationSection(path+".benefit")));
			}
			
			list.add(new God(gName, prayings, config.getString(gName+".displayname"), config.getString(gName+".description")));
		}
		gH.setGodList(list);
	}
}
