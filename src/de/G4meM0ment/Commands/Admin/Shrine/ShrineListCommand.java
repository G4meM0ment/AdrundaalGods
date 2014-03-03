package de.G4meM0ment.Commands.Admin.Shrine;

import org.bukkit.command.CommandSender;

import de.G4meM0ment.AGManager;
import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Command;
import de.G4meM0ment.Commands.CommandInfo;
import de.G4meM0ment.Framework.Shrine;
import de.G4meM0ment.Messenger.Messenger;

@CommandInfo(
	    name = "listShrines",
	    pattern = "listShrines",
	    usage = "/ag listShrines",
	    desc = "List all shrines",
	    permission = "adrundaalgods.admin"
	)
public class ShrineListCommand implements Command {
	@Override
	public boolean execute(AdrundaalGods plugin, CommandSender sender, String... args) {
		/*
		 * list all existing shrines
		 */
		String list = "Shrines: ";
		for(Shrine s : AGManager.getShrineHandler().getShrines())
			list += s.getName()+", ";
			
		Messenger.sendMessage(sender, list);
		return true;
	}
}
