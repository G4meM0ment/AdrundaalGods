package de.G4meM0ment.Commands.Admin.Shrine;

import org.bukkit.command.CommandSender;

import de.G4meM0ment.AGManager;
import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Command;
import de.G4meM0ment.Commands.CommandInfo;
import de.G4meM0ment.Framework.Shrine;
import de.G4meM0ment.Messenger.Messenger;

@CommandInfo(
	    name = "removeShrine",
	    pattern = "removeShrine",
	    usage = "/ag removeShrine <name>",
	    desc = "Remove the given shrine",
	    permission = "adrundaalgods.admin"
	)
public class ShrineRemoveCommand implements Command {
	@Override
	public boolean execute(AdrundaalGods plugin, CommandSender sender, String... args) {
	    
		if(args.length < 1)
			return false;
		
		/*
		 * removing an existing shrine from cache
		 */
		Shrine s = AGManager.getShrineHandler().getShrine(args[0]);
			
		if(s == null)
			Messenger.sendMessage(sender, "Shrine not found");
		else {
			AGManager.getShrineHandler().removeShrine(s);
			Messenger.sendMessage(sender, "Shrine removed");
		}
		return true;
	}
}
