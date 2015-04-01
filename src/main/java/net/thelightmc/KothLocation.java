package net.thelightmc;

import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.io.Serializable;
/*
@Author TheLightMC
 */
public class KothLocation implements Serializable {
    private String world = "world";
    private double rewardX=10;
    private double rewardY=63;
    private double rewardZ=10;
    private final String name;
    private final Selection selection;

    public KothLocation(String name,Selection selection) {
        this.name = name;
        this.selection = selection;
    }

    /*
    private double xMin;
    private double xMax;
    private double zMin;
    private double zMax;
    private final transient Material material = Material.IRON_BLOCK;
    public KothLocation(Block block) {
        nearbyMaterial(material,block);
        world = block.getWorld().getName();
    }
    public KothLocation(Location location) {
        this(location.getBlock());
    }
    public KothLocation(World world,double x,double y,double z) {
        this(new Location(world,x,y,z));
    }
    private void nearbyMaterial(Material material,Block block) {
        int north = block.getZ();
        Block b = block;
        while(b.getRelative(BlockFace.NORTH).getType() == material) {
            b = b.getRelative(BlockFace.NORTH);
            north++;
        }
        int west = block.getX();
        b = block;
        while(b.getRelative(BlockFace.WEST).getType() == material) {
            b = b.getRelative(BlockFace.WEST);
            west--;
        }
        int east = block.getX();
        b = block;
        while(b.getRelative(BlockFace.EAST).getType() == material) {
            b = b.getRelative(BlockFace.EAST);
            east++;
        }
        int south = block.getZ();
        b = block;
        while(b.getRelative(BlockFace.SOUTH).getType() == material) {
            b = b.getRelative(BlockFace.SOUTH);
            south--;
        }
        xMax = west;
        xMin = east;
        zMax = north;
        zMin = south;
        Bukkit.broadcastMessage(String.format("%s,%s,%s,%s",xMax,xMin,zMax,xMin));
    }
    // Get the middle of the koth cap zone.
    public Location getMiddle() {
        World w = Bukkit.getWorld(world);
        double x = zMax-(xMax-xMin);
        double z = zMax-(zMax-zMin);
        double y = w.getHighestBlockYAt((int)x,(int)z);
        return new Location(w,x,y,z);
    }
*/
    public boolean isInzone(Location location) {
        return selection.contains(location);
    }
    public void setRewardLocation(Location location) {
        this.rewardX = location.getX();
        this.rewardY = location.getY();
        this.rewardZ = location.getZ();
    }
    public Location getRewardLocation() {
        return new Location(Bukkit.getWorld(world),rewardX,rewardY,rewardZ);
    }

    public String getName() {
        return name;
    }
}
