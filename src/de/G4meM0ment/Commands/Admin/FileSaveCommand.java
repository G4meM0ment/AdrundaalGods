package de.G4meM0ment.Commands.Admin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Command;
import de.G4meM0ment.Commands.CommandInfo;
import de.G4meM0ment.Messenger.Messenger;

@CommandInfo(
	name = "save",
	pattern = "save",
	usage = "/ag save (config|data)",
	desc = "Saves configs or data files",
	permission = "adrundaalgods.admin"
)
public class FileSaveCommand implements Command {
	@Override
	public boolean execute(AdrundaalGods plugin, CommandSender sender, String... args) {
		// Grab the argument, if any.
		String arg1 = (args.length > 1 ? args[0] : "");
		        
		if(arg1.isEmpty()) {
			plugin.saveConfigs();
			plugin.saveDataFiles();
			Messenger.sendMessage(sender, ChatColor.GRAY+"All files saved");
			return true;
		}

		if(arg1.equalsIgnoreCase("config")) {
			plugin.saveConfigs();
			Messenger.sendMessage(sender, ChatColor.GRAY+"Configs saved");
			return true;
		} else if(arg1.equalsIgnoreCase("data")) {
			plugin.saveDataFiles();
			Messenger.sendMessage(sender, ChatColor.GRAY+"Data files saved");
			return true;
		} else
			return false;          
	}
}
