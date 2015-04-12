package net.thelightmc.commands;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.thelightmc.KothLocation;
import net.thelightmc.util.LocationUtil;
import net.thelightmc.util.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdCreate extends Command {
    private final WorldEditPlugin worldEdit;

    public CmdCreate(WorldEditPlugin worldEdit) {
        super("create");
        this.worldEdit = worldEdit;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        LocationUtil.addLocation(args[0],new KothLocation(args[0],worldEdit.getSelection(player)));
        player.sendMessage(StringUtils.format("New koth location set to your current selection."));
    }
}