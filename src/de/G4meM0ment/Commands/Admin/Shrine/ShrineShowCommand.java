package de.G4meM0ment.Commands.Admin.Shrine;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import de.G4meM0ment.AGManager;
import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Command;
import de.G4meM0ment.Commands.CommandInfo;
import de.G4meM0ment.Framework.Shrine;
import de.G4meM0ment.Messenger.Messenger;

@CommandInfo(
	    name = "showShrine",
	    pattern = "showShrine",
	    usage = "/ag showShrine <name>",
	    desc = "Shows the maximum and minimal coordinate of the shrine with red wool",
	    permission = "adrundaalgods.admin"
	)
public class ShrineShowCommand implements Command {
	
	private static List<Shrine> showing = new ArrayList<Shrine>();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(AdrundaalGods plugin, CommandSender sender, String... args) {
	    
		if(args.length < 1)
			return false;
		
		final Shrine s = AGManager.getShrineHandler().getShrine(args[0]);
			
		if(s == null)
			Messenger.sendMessage(sender, "Shrine not found");
		else if(showing.contains(s))
			Messenger.sendMessage(sender, "Shrine already shown!");
		else {			
			final Material maxMat = s.getMax().getBlock().getType();
			final Material minMat = s.getMin().getBlock().getType();
			final byte maxData = s.getMax().getBlock().getData();
			final byte minData = s.getMax().getBlock().getData();
		
			s.getMax().getBlock().setType(Material.WOOL);
			s.getMin().getBlock().setType(Material.WOOL);
			s.getMax().getBlock().setData((byte) 14);
			s.getMin().getBlock().setData((byte) 14);
			
			showing.add(s);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					s.getMax().getBlock().setType(maxMat);
					s.getMin().getBlock().setType(minMat);
					s.getMax().getBlock().setData(maxData);
					s.getMin().getBlock().setData(minData);
					showing.remove(s);
				}
			}, 200);
		}
		return true;
	}
}
