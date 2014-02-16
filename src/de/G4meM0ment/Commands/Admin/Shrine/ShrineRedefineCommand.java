package de.G4meM0ment.Commands.Admin.Shrine;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import de.G4meM0ment.AGManager;
import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Command;
import de.G4meM0ment.Commands.CommandInfo;
import de.G4meM0ment.Framework.Shrine;
import de.G4meM0ment.Messenger.Messenger;

@CommandInfo(
	    name = "redefineShrine",
	    pattern = "redefineShrine",
	    usage = "/ag redefineShrine <name>",
	    desc = "Update area of the given shrine",
	    permission = "adrundaalgods.admin"
	)
public class ShrineRedefineCommand implements Command {
	@Override
	public boolean execute(AdrundaalGods plugin, CommandSender sender, String... args) {
		
		//Shrine creation for players only!
		if(!(sender instanceof Player))
			return false;
		
		if(args.length < 1)
			return false;
	        
		/*
		 * adding a shrine to cache
		 */
		Player player = (Player) sender;
		Selection sel = plugin.getWorldEdit().getSelection(player);
		Shrine shrine = AGManager.getShrineHandler().getShrine(args[0]);
			
		//always requires a selected worldedit area
		if(sel == null)
			Messenger.sendMessage(player, "You need to select an area");
		else  {
			if(shrine == null)
				Messenger.sendMessage(player, "Shrine doens't exists!");
			else {
				shrine.setMax(sel.getMaximumPoint());
				shrine.setMin(sel.getMinimumPoint());
				Messenger.sendMessage(player, "Shrine area for shrine: "+shrine.getName()+" updated");
			}
		}
		return true;
	}
}
