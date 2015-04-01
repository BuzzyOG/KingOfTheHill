package net.thelightmc;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.thelightmc.commands.CmdKoth;
import net.thelightmc.listeners.KothListener;
import net.thelightmc.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class KOTH extends JavaPlugin {
    private final GameManager gameManager = new GameManager();
    private WorldEditPlugin worldEdit;
    @Override
    public void onEnable() {
        if (!getWorldEdit()) {
            getLogger().severe("World edit plugin is not available.");
            getLogger().severe("Exiting plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        LocationUtil.setRewardLocation(new Location(Bukkit.getWorlds().get(0),10,10,10));
        registerListeners(this, new KothListener(gameManager));
        getCommand("Koth").setExecutor(new CmdKoth(gameManager,worldEdit));
    }

    @Override
    public void onDisable() {

    }
    private boolean getWorldEdit() {
        try {
            worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        } catch (NullPointerException ex) {
            ex.getSuppressed();
            return false;
        }
        return true;
    }
    private void registerListeners(Plugin plugin,Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener,plugin);
        }
    }
}
