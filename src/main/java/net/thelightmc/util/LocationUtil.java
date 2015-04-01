package net.thelightmc.util;

import net.thelightmc.KothLocation;
import org.bukkit.Location;

import java.util.HashMap;

public final class LocationUtil {
    private static HashMap<String,KothLocation> arenaLocations = new HashMap<>();
    private static Location rewardLocation;
    public static void addLocation(String name,KothLocation location) {
        arenaLocations.put(name,location);
    }
    public static KothLocation getLocation(String name) {
        return arenaLocations.get(name);
    }
    public static void removeLocation(String name) {
        arenaLocations.remove(name);
    }
    public static HashMap<String,KothLocation> getList() {
        return arenaLocations;
    }

    public static Location getRewardLocation() {
        return rewardLocation;
    }

    public static void setRewardLocation(Location rewardLocation) {
        LocationUtil.rewardLocation = rewardLocation;
    }
}
