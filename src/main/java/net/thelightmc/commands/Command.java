package net.thelightmc.commands;

import org.bukkit.command.CommandSender;

public abstract class Command {
    private final String command;
    public Command(String command) {
        this.command = command;
    }
    public abstract void execute(CommandSender sender,String[] args);
    public String getCommand() {
        return command;
    }
}
