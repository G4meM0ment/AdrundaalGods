package de.G4meM0ment.Handler;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import de.G4meM0ment.AdrundaalGods;

public class ConfigHandler {
	
	private static AdrundaalGods plugin;
	
	/*
	 * 
	 * Settings
	 * 
	 */
	public static Material prayItem = Material.WRITTEN_BOOK;
	public static String prayItemName = "&5Gebetsbuch";
	public static String prayMenuTitle = "Wähle dein Gebet";
	public static boolean usePotionEffects = true;
	public static boolean disableMovement = true;
	public static boolean removeNegativePotionEffects = true;
	public static boolean disableChatAndCommands = true;
	public static long shrineCooldown = 86400000;
	
	/*
	 * 
	 * Settings paths
	 * 
	 */
	private static String prayItemPath = "prayItem";
	private static String prayItemNamePath = "prayItemName";
	private static String prayMenuTitlePath = "prayMenuTitle";
	private static String usePotionEffectsPath = "usePotionEffects";
	private static String disableMovementPath = "disableMovement";
	private static String removeNegativePotionEffectsPath = "removeNegativePotionEffects";
	private static String disableChatAndCommandsPath = "disableChatAndCommands";
	private static String shrineCooldownPath = "shrineCooldown";
	
	public ConfigHandler(AdrundaalGods plugin)
	{
		ConfigHandler.plugin = plugin;
	}
	public ConfigHandler()
	{
	}
	
	/**
	 * load all settings from the config to cache unsaved changes are gone
	 */
	public static void loadSettings()
	{
		FileConfiguration config = plugin.getConfig();
		
		prayItem = Material.valueOf(config.getString(prayItemPath));
		prayItemName = config.getString(prayItemNamePath);
		prayMenuTitle = config.getString(prayMenuTitlePath);
		usePotionEffects = config.getBoolean(usePotionEffectsPath);
		disableMovement = config.getBoolean(disableMovementPath);
		removeNegativePotionEffects = config.getBoolean(removeNegativePotionEffectsPath);
		disableChatAndCommands = config.getBoolean(disableChatAndCommandsPath);
		shrineCooldown = config.getLong(shrineCooldownPath);
	}
	
/*	/**
	 * save all settings from cache to file, changes made in flat file are gone
	 *
	public static void saveSettings()
	{
		FileConfiguration config = plugin.getConfig();
		
		config.set(dropItemsPath, dropItems);
		
		plugin.saveConfig();
	} */
}
