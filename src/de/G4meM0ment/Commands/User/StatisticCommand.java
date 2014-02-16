package de.G4meM0ment.Commands.User;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.G4meM0ment.AGManager;
import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Command;
import de.G4meM0ment.Commands.CommandInfo;
import de.G4meM0ment.Framework.AGPlayer;
import de.G4meM0ment.Messenger.Message;
import de.G4meM0ment.Messenger.Messenger;

@CommandInfo(
		name = "shrines",
		pattern = "shrines",
		usage = "/ag shrines (<name>)",
		desc = "Shows shrines found and total for given player",
		permission = "adrundaalgods.admin"
	)
public class StatisticCommand implements Command {
	@Override
	public boolean execute(AdrundaalGods plugin, CommandSender sender, String... args) {
		// Grab the argument, if any.
	    String arg1 = (args.length > 0 ? args[0] : "");
		
	    if(Bukkit.getPlayer(arg1) == null && !arg1.isEmpty()) {
	    	Messenger.sendMessage(sender, Message.playerNotFound);
	    	return true;
	    }
	    
	    Player p = (Bukkit.getPlayer(arg1) != null ? Bukkit.getPlayer(arg1) : (Player) sender);
	    AGPlayer agp = AGManager.getPlayerHandler().getAGPlayer(p.getName());
		if(agp == null) {
			Messenger.sendMessage(sender, Message.playerNotFound);
			return true;	
		}	
		Messenger.sendMessage(sender, Message.shrinesFound, "%found%", agp.getFoundShrines().size()+"", "%max%", AGManager.getShrineHandler().getShrines().size()+"");
		return true;
	}	
}
