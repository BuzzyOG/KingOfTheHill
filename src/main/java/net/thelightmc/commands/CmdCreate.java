package net.thelightmc.commands;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.thelightmc.KothLocation;
import net.thelightmc.util.LocationUtil;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
        LocationUtil.addLocation(args[0],new KothLocation(worldEdit.getSelection(player)));
    }
}