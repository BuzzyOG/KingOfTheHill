package net.thelightmc.storage;

import com.google.gson.Gson;
import net.thelightmc.KothLocation;

import java.io.IOException;
import java.util.Collection;

public abstract class Database {
    private final String path;
    private final Gson gson = new Gson();
    private final DatabaseStorage storage;

    public Database(String path,DatabaseStorage storage) {
        this.path = path;
        this.storage = storage;
    }

    public DatabaseStorage getStorage() {
        return storage;
    }
    public abstract void save(String name,KothLocation location) throws IOException;
    public abstract Collection<? extends KothLocation> load();

    public String getPath() {
        return path;
    }

    public Gson getGson() {
        return gson;
    }

    enum DatabaseStorage {
        JSON,MySQL,FlatFile,MongoDB
    }
}
