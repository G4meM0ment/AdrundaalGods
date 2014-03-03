package de.G4meM0ment;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import de.G4meM0ment.Commands.CommandHandler;
import de.G4meM0ment.DataStorage.GodData;
import de.G4meM0ment.DataStorage.PlayerData;
import de.G4meM0ment.DataStorage.ShrineData;
import de.G4meM0ment.Handler.ConfigHandler;
import de.G4meM0ment.Handler.PlayerHandler;
import de.G4meM0ment.Listener.BListener;
import de.G4meM0ment.Listener.PListener;
import de.G4meM0ment.Messenger.Message;

public class AdrundaalGods extends JavaPlugin {
	
	@SuppressWarnings("unused")
	private ConfigHandler cH;
	private PlayerData pD;
	private ShrineData sD;
	private GodData gD;
	@SuppressWarnings("unused")
	private PlayerHandler pH;
	private CommandHandler cmdH;
	private AGManager agm;
	
	private PListener pListener;
	private BListener bListener;
	
	private WorldEditPlugin we;
	
	private static String dir = "plugins/AdrundaalGods";
	
	@Override
	public void onEnable() {
		agm = new AGManager(this);
		cH = new ConfigHandler(this);
		pD = new PlayerData(this);
		sD = new ShrineData(this);
		gD = new GodData(this);
		
		pListener = new PListener(this);
		bListener = new BListener();

		Bukkit.getPluginManager().registerEvents(pListener, this);
		Bukkit.getPluginManager().registerEvents(bListener, this);
		
		//Enabling config
		try {
			saveDefaultConfig();
			saveConfig();
			getLogger().info("Config loaded.");
		} catch(Exception e) {
			getLogger().info("Could not load config.");
		}
		ConfigHandler.loadSettings();
		gD.reloadConfig();
		gD.saveConfig();
		sD.reloadConfig();
		sD.saveConfig();
		pD.reloadConfig();
		pD.saveConfig();
		
		//init msgs
		new Message(this);
		Message.reloadFile();
		Message.saveFile();
		Message.loadMessages();
		
		//Looking for worldedit dependency
		we = initWorldEdit();
		//disabling if not found
		if(we == null) {
			getLogger().warning("No WorldEdit found, but needed! Disabling plugin!");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				gD.loadDataFromFile();
				sD.loadDataFromFile();
				pD.loadDataFromFile();	
				
				initAutoSaver();
			}
		});

		pH = new PlayerHandler(this);
		
		cmdH = new CommandHandler(this);
		getCommand("ag").setExecutor(cmdH);
	}
	
	@Override
	public void onDisable() {
		saveConfig();
		pD.saveDataToFile();
		sD.saveDataToFile();
		gD.saveConfig();
	}
	
	private void initAutoSaver() {
		final AdrundaalGods plugin = this;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				plugin.saveConfigs();
				plugin.saveDataFiles();
			}
		}, 0, 12000);
	}
	
	/**
	 * reload all configuration files and cache their data
	 */
	public void reloadConfigs() {
		reloadConfig();
		ConfigHandler.loadSettings();
		Message.reloadFile();
		Message.loadMessages();
		gD.reloadConfig();
		gD.loadDataFromFile();
	}
	/**
	 * save all configuration files
	 */
	public void saveConfigs() {
		saveConfig();
		Message.saveFile();
		//gD.saveConfig();
	}
	
	/**
	 * reload all data files
	 */
	public void reloadDataFiles() {
		AGManager.getShrineData().reloadConfig();
		AGManager.getShrineData().loadDataFromFile();
		AGManager.getPlayerData().reloadConfig();
		AGManager.getPlayerData().loadDataFromFile();
	}
	public void saveDataFiles() {
		AGManager.getShrineData().saveDataToFile();
		AGManager.getShrineData().saveConfig();
		AGManager.getPlayerData().saveDataToFile();
		AGManager.getPlayerData().saveConfig();
	}
	
	private WorldEditPlugin initWorldEdit() 
	{
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
	 
	    // WorldEdit may not be loaded
	    if (plugin == null || !(plugin instanceof WorldEditPlugin))
	    {
	        return null; // Maybe you want throw an exception instead
	    }
		getLogger().info("WorldEdit found enabled features");
	    return (WorldEditPlugin) plugin;
	}
	public WorldEditPlugin getWorldEdit() 
	{
		return we;
	}

	public String getDir() {
		return dir;
	}
	
	public AGManager getAGManager() {
		return agm;
	}
}
