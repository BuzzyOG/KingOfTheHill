package net.thelightmc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdKoth implements CommandExecutor {
    private final Command[] commands;

    public CmdKoth(Command... commands) {

        this.commands = commands;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (strings.length > 0) {
            for (Command c : commands) {
                if (c.getCommand().equalsIgnoreCase(strings[0])) {
                    c.execute(commandSender,strings);
                    break;
                }
            }
        } else {
            //ToDo: Add a better help menu
            commandSender.sendMessage(ChatColor.RED + "/Koth <Command>");
        }
        return true;
    }
}
