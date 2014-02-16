package de.G4meM0ment.Commands.Admin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Command;
import de.G4meM0ment.Commands.CommandInfo;
import de.G4meM0ment.Messenger.Messenger;

@CommandInfo(
		name = "version",
		pattern = "version",
		usage = "/ag version",
		desc = "Displays currently used version of AdrundaalGods",
		permission = "adrundaalgods.admin"
	)
public class VersionCommand implements Command {
	@Override
	public boolean execute(AdrundaalGods plugin, CommandSender sender, String... args) {
		Messenger.sendMessage(sender, ChatColor.GRAY+"AdrundaalGods Version: "+ChatColor.WHITE+plugin.getDescription().getVersion());
		return false;
	}
}
