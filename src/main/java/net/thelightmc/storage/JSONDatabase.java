package net.thelightmc.storage;

import net.thelightmc.KOTH;
import net.thelightmc.KothLocation;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sk89q.worldedit.Location;

public class JSONDatabase extends Database implements IData {
	
	public JSONDatabase(String path, DatabaseStorage storage) {
		super(path, storage);
		// TODO Auto-generated constructor stub
	}


	private final Gson gson = new Gson();
	private String LOCATIONS_FILE;

    private final KOTH plugin;

    private HashMap<String, KothLocation> koth_locations;
    private Boolean is_loading;
    private Boolean is_saving;

	@Override
    public void save(String name, KothLocation location) throws IOException {
        String s = getGson().toJson(location);
        FileWriter writer = new FileWriter(getPath());
        writer.write(s);
        writer.close();
        
        this.LOCATIONS_FILE = this.plugin.getDataFolder()+ File.separator+"locations.json";

        this.is_loading = false;
        this.is_saving = false;

        if (!this.loadData()) {
            this.plugin.getLogger().severe("Could not load plugin data!");
            Bukkit.getPluginManager().disablePlugin(this.plugin);
        }
        
        this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                if (plugin.getData().isSavingData() || plugin.getData().isLoadingData()) {
                    plugin.getLogger().info("Koth is loading or saving. Skipping this save cycle.");
                }
                else {
                    plugin.getData().saveData();
                }
            }
        }, 1200L, 6000L);
    }

    public Boolean loadData() {
        this.is_loading = true;

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.koth_locations = new HashMap<>();

        // set up locations
        this.plugin.getLogger().info("Loading locations.json...");
        try {
            JsonReader locations_reader = new JsonReader(new FileReader(this.LOCATIONS_FILE));
            List<KothLocation> locations = gson.fromJson(locations_reader,
                    new TypeToken<ArrayList<KothLocation>>(){}.getType());
            locations_reader.close();
            for (KothLocation location : locations) {
                if (location.getName() != null && !location.getName().equals("") &&
                        location.getWorld() != null && !location.getWorld().equals("") &&
                        location.location.length == 3 && location.location[1] > 0) {
                    if (this.koth_locations.containsKey(location.getName())) {
                        this.plugin.getLogger().warning(String.format("Duplicate location: %s, ignoring second " +
                                "instance.", location.getName()));
                    }
                    else {
                        this.koth_locations.put(location.getName(), location);
                    }
                }
            }

            this.plugin.getLogger().info(String.format("%s koth location(s) loaded.",
                    this.koth_locations.values().size()));
        }
        catch (IOException ex) {
            this.plugin.getLogger().warning("No locations.json file found.");
        }

        this.is_loading = false;
        return true;
    }
    public Boolean saveData() {
        this.is_saving = true;
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        // write data
        this.plugin.getLogger().info("Saving data...");
        try {
            PrintWriter writer;

            String locations_string = "" +
                    "/*\n" +
                    "\n" +
                    "Koth Locations\n" +
                    "\n" +
                    "- format is (x, y, z)\n" +
                    "\n" +
                    "Example config:\n" +
                    "[\n" +
                    "  {\n" +
                    "    \"name\": \"location_1\",\n" +
                    "    \"world\": \"world\"\n" +
                    "    \"location\": [\n" +
                    "      0,\n" +
                    "      63,\n" +
                    "      0,\n" +
                    "    ],\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"name\": \"location_1_nether\",\n" +
                    "    \"world\": \"DIM-1\"\n" +
                    "    \"location\": [\n" +
                    "      -843,\n" +
                    "      12,\n" +
                    "      473,\n" +
                    "    ],\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"name\": \"location_1_end\",\n" +
                    "    \"world\": \"DIM1\"\n" +
                    "    \"location\": [\n" +
                    "      -843,\n" +
                    "      12,\n" +
                    "      473,\n" +
                    "    ],\n" +
                    "  }\n" +
                    "]\n" +
                    "\n" +
                    "*/\n\n\n"+
                    gson.toJson(this.koth_locations.values());
            writer = new PrintWriter(this.LOCATIONS_FILE);
            writer.print(locations_string);
            writer.close();
        }
        catch (IOException ex) {
            this.plugin.getLogger().severe("Failed to save the plugin data!");
            this.is_saving = false;
            return false;
        }

        this.is_saving = false;
        return true;
    }

    public HashMap<String, KothLocation> getLocations() {
        return this.koth_locations;
    }

    public Boolean isLoadingData() {
        return this.is_loading;
    }
    public Boolean isSavingData() {
        return this.is_saving;
    }

    
    @Override
    public Collection<? extends KothLocation> load() {
        List<KothLocation> locationList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getPath()));
            String line;
            while((line=reader.readLine())!=null) {
                if (line.equalsIgnoreCase("")) {
                    break;
                }
                locationList.add(getGson().fromJson(line,KothLocation.class));
            }
        } catch (FileNotFoundException e) {
            e.getSuppressed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locationList;
    }
}
