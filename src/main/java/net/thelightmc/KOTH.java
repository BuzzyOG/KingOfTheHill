package net.thelightmc;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import net.thelightmc.commands.CmdCreate;
import net.thelightmc.commands.CmdKoth;
import net.thelightmc.commands.CmdStart;
import net.thelightmc.listeners.KothListener;
import net.thelightmc.storage.Database;
import net.thelightmc.storage.IData;
import net.thelightmc.storage.JSONDatabase;
import net.thelightmc.util.ItemLoader;
import net.thelightmc.util.LocationUtil;
import net.thelightmc.util.WeightedList;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class KOTH extends JavaPlugin implements Listener {

	 private IData data;
    private WorldEditPlugin worldEdit;
    //private Database database = new JSONDatabase(getDataFolder().getAbsolutePath() + File.separator + "arenas.json");
    private WeightedList<ItemStack> weights = new WeightedList<>();
    private final GameManager gameManager = new GameManager(weights);
    @Override
    public void onEnable() {
    	try {
            if (this.getDataFolder().mkdirs()) {
                this.getLogger().info("Created data directories");
            }
        }
        catch (SecurityException ex) {
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (!getWorldEdit()) {
            getLogger().severe("World edit plugin is not available.");
            getLogger().severe("Exiting plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        this.data = new JSONDatabase(this);
        //loadList();
        //Not tested loading
        //loadLocations();
        //ToDo Fix area for reward location
        registerListeners(this, new KothListener(gameManager));
        getCommand("Koth").setExecutor(new CmdKoth(new CmdCreate(worldEdit),new CmdStart(gameManager)));
    }



    @Override
    public void onDisable() {
        this.data.saveData();
    }

    public IData getData() { return this.data; }

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
    /*
    private void loadList() {
        for (String s : getConfig().getStringList("Rewards")) {
            ItemLoader.loadFromString(s, weights);
        }
    }
    private void loadLocations() {
        for (KothLocation location : database.load()) {
            LocationUtil.addLocation(location.getName(), location);
        }
    }
    private void saveLocations() throws IOException {
        for (String name : LocationUtil.getList().keySet()) {
            database.save(name,LocationUtil.getLocation(name));
        }
    }
*/
}
