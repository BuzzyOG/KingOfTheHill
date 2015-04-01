package net.thelightmc.storage;

import net.thelightmc.KothLocation;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JSONDatabase extends Database {
    public JSONDatabase(String path) {
        super(path,DatabaseStorage.JSON);
    }

    @Override
    public void save(String name, KothLocation location) throws IOException {
        String s = getGson().toJson(location);
        FileWriter writer = new FileWriter(getPath());
        writer.write(s);
        writer.close();
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
