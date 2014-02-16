package de.G4meM0ment;

import de.G4meM0ment.DataStorage.GodData;
import de.G4meM0ment.DataStorage.PlayerData;
import de.G4meM0ment.DataStorage.ShrineData;
import de.G4meM0ment.Handler.GodHandler;
import de.G4meM0ment.Handler.PlayerHandler;
import de.G4meM0ment.Handler.ShrineHandler;

public class AGManager {
	
	private static AdrundaalGods plugin;
	private static PlayerHandler pH;
	private static ShrineHandler sH;
	private static GodHandler gH;
	private static PlayerData pD;
	private static ShrineData sD;
	private static GodData gD;
	
	public AGManager(AdrundaalGods plugin)
	{
		AGManager.plugin = plugin;
		pH = new PlayerHandler(plugin);
		sH = new ShrineHandler();
		gH = new GodHandler();
		pD = new PlayerData();
		sD = new ShrineData();
		gD = new GodData();
	}

	public static AdrundaalGods getPlugin() {
		return plugin;
	}

	public static PlayerHandler getPlayerHandler() {
		return pH;
	}
	
	public static ShrineHandler getShrineHandler() {
		return sH;
	}
	
	public static GodHandler getGodHandler() {
		return gH;
	}

	public static PlayerData getPlayerData() {
		return pD;
	}

	public static ShrineData getShrineData() {
		return sD;
	}

	public static GodData getGodData() {
		return gD;
	}
	
}
