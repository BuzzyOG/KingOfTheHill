package net.thelightmc.timers;

import org.bukkit.scheduler.BukkitRunnable;

public class KothCapTimer extends BukkitRunnable {
    private int i;

    public KothCapTimer(int i) {
        this.i = i;
    }
    @Override
    public void run() {
        i++;
    }
}
