package de.G4meM0ment.Commands.Admin.Shrine;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.G4meM0ment.AGManager;
import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Command;
import de.G4meM0ment.Commands.CommandInfo;
import de.G4meM0ment.Framework.Shrine;
import de.G4meM0ment.Messenger.Messenger;

@CommandInfo(
	    name = "tpShrine",
	    pattern = "tpShrine",
	    usage = "/ag tpShrine <name>",
	    desc = "Teleports you to the given shrine",
	    permission = "adrundaalgods.admin"
	)
public class ShrineTpCommand implements Command {

	@Override
	public boolean execute(AdrundaalGods plugin, CommandSender sender, String... args) {
	    
		if(args.length < 1)
			return false;
		
		final Shrine s = AGManager.getShrineHandler().getShrine(args[0]);
		
		if(!(sender instanceof Player))
			Messenger.sendMessage(sender, "You're not a player");
		else if(s == null)
			Messenger.sendMessage(sender, "Shrine not found");
		else		
			((Player)sender).teleport(s.getMax());
		return true;
	}
}
