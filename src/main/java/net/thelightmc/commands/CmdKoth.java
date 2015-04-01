package net.thelightmc.commands;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.thelightmc.GameManager;
import org.bukkit.command.*;
import org.bukkit.command.Command;

public class CmdKoth implements CommandExecutor {
    private final GameManager gameManager;
    private final WorldEditPlugin worldEditPlugin;

    public CmdKoth(GameManager gameManager,WorldEditPlugin plugin) {

        this.gameManager = gameManager;
        this.worldEditPlugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        CmdCreate create = new CmdCreate(worldEditPlugin);
        CmdDebug debug = new CmdDebug(gameManager);
        create.execute(commandSender,strings);
        debug.execute(commandSender,strings);
        return true;
    }
}
