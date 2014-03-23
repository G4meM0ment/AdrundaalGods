/**
 * Command system originally by garbagemule, thanks to him
 */
package de.G4meM0ment.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.G4meM0ment.AdrundaalGods;
import de.G4meM0ment.Commands.Admin.FileReloadCommand;
import de.G4meM0ment.Commands.Admin.FileSaveCommand;
import de.G4meM0ment.Commands.Admin.VersionCommand;
import de.G4meM0ment.Commands.Admin.Shrine.ShrineAddCommand;
import de.G4meM0ment.Commands.Admin.Shrine.ShrineListCommand;
import de.G4meM0ment.Commands.Admin.Shrine.ShrineRedefineCommand;
import de.G4meM0ment.Commands.Admin.Shrine.ShrineRemoveCommand;
import de.G4meM0ment.Commands.Admin.Shrine.ShrineShowCommand;
import de.G4meM0ment.Commands.Admin.Shrine.ShrineTpCommand;
import de.G4meM0ment.Commands.User.StatisticCommand;
import de.G4meM0ment.Handler.PermHandler;
import de.G4meM0ment.Messenger.Message;
import de.G4meM0ment.Messenger.Messenger;

public class CommandHandler implements CommandExecutor {
	
	private AdrundaalGods plugin;
    
    private Map<String,Command> commands;
    
    public CommandHandler(AdrundaalGods plugin) {
        this.plugin = plugin;
        
        registerCommands();
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command bcmd, String label, String[] args) {
        // Grab the base and arguments.
        String base = (args.length > 0 ? args[0] : "");
        String last = (args.length > 0 ? args[args.length - 1] : "");
        
        // If there's no base argument, show a helpful message.
        if (base.equals("")) {
            Messenger.sendMessage(sender, "/ag help|?");
            return true;
        }
        
        // The help command is a little special
        if (base.equals("?") || base.equalsIgnoreCase("help")) {
            showHelp(sender);
            return true;
        }
        
        // Get all commands that match the base.
        List<Command> matches = getMatchingCommands(base);
        
        // If there's more than one match, display them.
        if (matches.size() > 1) {
            Messenger.sendMessage(sender, "Multiple command matches");
            for (Command cmd : matches) {
                showUsage(cmd, sender, false);
            }
            return true;
        }
        
        // If there are no matches at all, notify.
        if (matches.size() == 0) {
          	Messenger.sendMessage(sender, "Command not found");
            return true;
        }
        
        // Grab the only match.
        Command command = matches.get(0);
        CommandInfo info = command.getClass().getAnnotation(CommandInfo.class);
        
        // First check if the sender has permission.
        if (!PermHandler.hasPerm(sender, info.permission())) {
        	Messenger.sendMessage(sender, Message.noPermission);
            return true;
        }
        
        // Check if the last argument is a ?, in which case, display usage and description
        if (last.equals("?") || last.equals("help")) {
            showUsage(command, sender, true);
            return true;
        }
        
        // Otherwise, execute the command!
        String[] params = trimFirstArg(args);
        if (!command.execute(plugin, sender, params)) {
            showUsage(command, sender, true);
        }
        return true;
    }
    
    /**
	* Get all commands that match a given string.
	* @param arg the given string
	* @return a list of commands whose patterns match the given string
	*/
    private List<Command> getMatchingCommands(String arg) {
        List<Command> result = new ArrayList<Command>();
        
        // Grab the commands that match the argument.
        for (Entry<String,Command> entry : commands.entrySet()) {
            if (arg.equalsIgnoreCase(entry.getKey())) {
                result.add(entry.getValue());
            }
        }
        
        return result;
    }
    
    /**
	* Show the usage and description messages of a command to a player.
	* The usage will only be shown, if the player has permission for the command.
	* @param cmd a Command
	* @param sender a CommandSender
	*/
    private void showUsage(Command cmd, CommandSender sender, boolean prefix) {
        CommandInfo info = cmd.getClass().getAnnotation(CommandInfo.class);
        if(!PermHandler.hasPerm(sender, info.permission())) return;

        sender.sendMessage((prefix ? "Usage: " : "") + info.usage() + " " + ChatColor.YELLOW + info.desc());
    }
    
    /**
	* Remove the first argument of a string. This is because the very first
	* element of the arguments array will be the command itself.
	* @param args an array of length n
	* @return the same array minus the first element, and thus of length n-1
	*/
    private String[] trimFirstArg(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }
    
    /**
	* List all the available MobArena commands for the CommandSender.
	* @param sender a player or the console
	*/
    private void showHelp(CommandSender sender) {
        StringBuilder user = new StringBuilder();
        StringBuilder admin = new StringBuilder();

        for (Command cmd : commands.values()) {
            CommandInfo info = cmd.getClass().getAnnotation(CommandInfo.class);
            if(!PermHandler.hasPerm(sender, info.permission())) continue;

            StringBuilder buffy;
            if (info.permission().startsWith("adrundaalgods.admin"))
                buffy = admin;
            else 
                buffy = user;
            
            buffy.append("\n")
                 .append(ChatColor.RESET).append(info.usage()).append(" ")
                 .append(ChatColor.YELLOW).append(info.desc());
        }

        if (admin.length() == 0) {
        	Messenger.sendMessage(sender, "Available Commands: "+user.toString());
        } else {
        	Messenger.sendMessage(sender, "User Commands: "+user.toString());
        	Messenger.sendMessage(sender, "Admin Commands: "+admin.toString());
        }
    }
    
    /**
	* Register all the commands directly.
	* This could also be done with a somewhat dirty classloader/resource reader
	* method, but this is neater, albeit more manual work.
	*/
    private void registerCommands() {
        commands = new LinkedHashMap<String,Command>();
        
        /*
         *  admin commands
         */
        //file util cmds
        register(FileReloadCommand.class);
        register(FileSaveCommand.class);
        register(VersionCommand.class);
        //shrine cmds
        register(ShrineAddCommand.class);
        register(ShrineRedefineCommand.class);
        register(ShrineListCommand.class);
        register(ShrineRemoveCommand.class);
        register(ShrineShowCommand.class);
        register(ShrineTpCommand.class);
        
        /*        
         * user commands
         */
        register(StatisticCommand.class);
    }

	/**
	* Register a command.
	* The Command's CommandInfo annotation is queried to find its pattern
	* string, which is used to map the commands.
	* @param c a Command
	*/
    public void register(Class<? extends Command> c) {
        CommandInfo info = c.getAnnotation(CommandInfo.class);
        if (info == null) return;
        
        try {
            commands.put(info.pattern(), c.newInstance());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }	
}
