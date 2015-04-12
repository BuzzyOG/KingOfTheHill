package net.thelightmc;

import mkremins.fanciful.FancyMessage;
import net.techcable.npclib.NPC;
import net.techcable.npclib.NPCLib;
import net.techcable.npclib.NPCRegistry;
import net.thelightmc.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class KothGame {
    private final String name;
    private final KothLocation location;
    private String capper=null;
    private int reset = (60*1)+5;
    private int capTime = reset;
    private Integer timerID = null;

    public KothGame(String name,KothLocation location) {
        this.name = name;
        this.location = location;
    }


    public KothLocation getLocation() {
        return location;
    }

    public boolean capperChanged(Player player) {
        if (capper == null) {
            if (getLocation().isInzone(player.getLocation())) {
                setCapper(player);
                return true;
            }
            return false;
        }
        if (capper.equalsIgnoreCase(player.getName())) {
            if (getLocation().isInzone(player.getLocation())) {
                return false;
            }
            clearCapper();
            return true;
        }
        Player cap = Bukkit.getPlayer(capper);
        if (location.isInzone(cap.getLocation())) {
            return false;
        }
        clearCapper();
        setCapper(player);
        return true;
    }

    private void clearCapper() {
        if (capTime<=reset-5) {
            FancyMessage fancyMessage = new FancyMessage(capper).color(ChatColor.GOLD).then(" has last control of the KOTH.").color(ChatColor.DARK_AQUA)
                    .suggest("/msg " + capper).tooltip(StringUtils.format("Cap Time: " + getTime(reset-capTime)));
            for (Player player : Bukkit.getOnlinePlayers()) {
                fancyMessage.send(player);
            }
        }
        capper = null;
        cancelTimer();
    }

    private String name() {
        return name;
    }

    private void setCapper(Player player) {
        capper = player.getName();
        startTimer();
    }
    private void cancelTimer() {
        Bukkit.getScheduler().cancelTask(timerID);
        capTime=reset;
        capTime--;
    }
    private void startTimer() {
        timerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getProvidingPlugin(KOTH.class), new Runnable() {
            @Override
            public void run() {
                if (capTime <= 0) {
                    winGame();
                    return;
                }
                if (capTime == reset-5) {
                    Bukkit.broadcastMessage(StringUtils.format("&9A player has taken control of &6%s.",name()));
                    capTime--;
                }
                if (capTime % 30 == 0) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',String.format("&9%s has control of koth (%s)",capper,getTime())));
                }
                capTime--;
            }
        }, 20, 20);
    }

    private void winGame() {
        Player player = Bukkit.getPlayer(capper);
        player.teleport(getLocation().getRewardLocation());
        //ToDo Fix skins and make into villager
        NPCRegistry registry = NPCLib.getNPCRegistry("TacoRegistry", JavaPlugin.getProvidingPlugin(KOTH.class));
        NPC npc = registry.createNPC(EntityType.PLAYER, "TheLightMC");
        npc.setProtected(true);
        npc.setName(ChatColor.DARK_AQUA + "Rewards Guru");
        npc.spawn(getLocation().getRewardLocation());
    }

    private String getTime() {
        long min = TimeUnit.SECONDS.toMinutes(capTime);
        long seconds = capTime-(min*60);
        if (seconds<10) {
            return min + ":0" + seconds;
        }
        return min + ":" + seconds;
    }
    private String getTime(int i) {
        long min = TimeUnit.SECONDS.toMinutes(i);
        long seconds = i-(min*60);
        if (seconds<10) {
            return min + ":0" + seconds;
        }
        return min + ":" + seconds;
    }
    public void removeIfCapper(Player player) {
        if (capper.equalsIgnoreCase(player.getName())) {
            clearCapper();
        }
    }
}
