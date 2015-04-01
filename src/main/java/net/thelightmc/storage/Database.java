package net.thelightmc.storage;

import net.thelightmc.KothLocation;

import java.io.IOException;

public abstract class Database {
    private final String path;
    private final DatabaseStorage storage;

    public Database(String path,DatabaseStorage storage) {
        this.path = path;
        this.storage = storage;
    }

    public DatabaseStorage getStorage() {
        return storage;
    }
    public abstract void save(String name,KothLocation location) throws IOException;
    public abstract KothLocation[] load();

    public String getPath() {
        return path;
    }

    enum DatabaseStorage {
        JSON,MySQL,FlatFile,MongoDB
    }
}
