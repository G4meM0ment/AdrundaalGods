package de.G4meM0ment.Commands.Admin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Command;
import de.G4meM0ment.Commands.CommandInfo;
import de.G4meM0ment.Messenger.Messenger;

@CommandInfo(
	    name = "reload",
	    pattern = "reload",
	    usage = "/ag reload (config|data)",
	    desc = "Reload configs or data files",
	    permission = "adrundaalgods.admin"
	)
public class FileReloadCommand implements Command {
	@Override
	public boolean execute(AdrundaalGods plugin, CommandSender sender, String... args) {
		// Grab the argument, if any.
	    String arg1 = (args.length > 0 ? args[0] : "");
	        
	    if(arg1.isEmpty()) {
	    	plugin.reloadConfigs();
	    	plugin.reloadDataFiles();
	        Messenger.sendMessage(sender, ChatColor.GRAY+"All files reloaded");
	        return true;
	    }

	    if(arg1.equalsIgnoreCase("config")) {
	    	plugin.reloadConfigs();
	    	Messenger.sendMessage(sender, ChatColor.GRAY+"Configs reloaded");
	    	return true;
	    } else if(arg1.equalsIgnoreCase("data")) {
	        plugin.reloadDataFiles();
	        Messenger.sendMessage(sender, ChatColor.GRAY+"Data files reloaded");
	        return true;
	    } else
	    	return false;
	}
}