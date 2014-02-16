package de.G4meM0ment.Commands.Admin.Shrine;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import de.G4meM0ment.AGManager;
import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Command;
import de.G4meM0ment.Commands.CommandInfo;
import de.G4meM0ment.Messenger.Messenger;

@CommandInfo(
	    name = "addShrine",
	    pattern = "addShrine",
	    usage = "/ag addShrine <name> <god> (Needs worldedit area selected)",
	    desc = "Adding a new shrine for the specified god with the name given in the selected worldedit area",
	    permission = "adrundaalgods.admin"
	)
public class ShrineAddCommand implements Command {
	@Override
	public boolean execute(AdrundaalGods plugin, CommandSender sender, String... args) {
		
		//Shrine creation for players only!
		if(!(sender instanceof Player))
			return false;
		
		if(args.length < 2)
			return false;
	        
		/*
		 * adding a shrine to cache
		 */
		Player player = (Player) sender;
		Selection sel = plugin.getWorldEdit().getSelection(player);
			
		//always requires a selected worldedit area
		if(sel == null)
			Messenger.sendMessage(player, "You need to select an area");
		else  {
			if(AGManager.getShrineHandler().getShrine(args[0]) != null)
				Messenger.sendMessage(player, "Shrine already exists!");
			else if(AGManager.getGodHandler().getGod(args[1]) == null)
				Messenger.sendMessage(player, "God not found");
			else if(AGManager.getShrineHandler().addShrine(args[0], args[1],  sel.getMaximumPoint(), sel.getMinimumPoint()))
				Messenger.sendMessage(player, "Shrine created");
			else
				Messenger.sendMessage(player, "Couldn't add shrine");
		}
		return true;
	}
}
