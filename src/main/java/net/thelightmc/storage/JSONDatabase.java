package net.thelightmc.storage;

import com.google.gson.Gson;
import net.thelightmc.KothLocation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONDatabase extends Database {
    public JSONDatabase(String path) {
        super(path,DatabaseStorage.JSON);
    }

    @Override
    public void save(String name, KothLocation location) throws IOException {
        Gson gson = new Gson();
        String s = gson.toJson(location);
        File file = new File(getPath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter writer = new FileWriter(file);
        writer.write(s);
    }

    @Override
    public KothLocation[] load() {
        return null;
    }
}
