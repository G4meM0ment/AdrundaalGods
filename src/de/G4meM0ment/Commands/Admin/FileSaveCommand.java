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
		String arg1 = (args.length > 0 ? args[0] : "");
		String arg2 = (args.length > 1 ? args[1] : "");
		        
		if(arg1.equalsIgnoreCase("reload")) {
			if(arg2.isEmpty()) {
				//TODO save plugin config
				Messenger.sendMessage(sender, ChatColor.GRAY+"All files saved");
				return true;
			}

			if(arg2.equalsIgnoreCase("config")) {
				//TODO save plugin config
				Messenger.sendMessage(sender, ChatColor.GRAY+"Configs saved");
				return true;
			} else if(arg2.equalsIgnoreCase("data")) {
				//TODO save data
				Messenger.sendMessage(sender, ChatColor.GRAY+"Data files saved");
				return true;
			} else
				return false;          
		}
		return false;
	}
}
