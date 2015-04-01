package net.thelightmc.listeners;

import net.thelightmc.GameManager;
import net.thelightmc.KothGame;
import net.thelightmc.exceptions.NoGameRunningException;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class KothListener implements Listener {
    private final GameManager manager;

    public KothListener(GameManager manager) {
        this.manager = manager;
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!playedMoved(event.getFrom(),event.getTo())) {
            return;
        }
        KothGame game;
        try {
            game = manager.getGame();
        } catch (NoGameRunningException e) {
            e.getSuppressed();
            return;
        }
        game.capperChanged(event.getPlayer());
    }
    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        try {
            manager.getGame().removeIfCapper(event.getPlayer());
        } catch (NoGameRunningException e) {
            e.getSuppressed();
        }
    }
    private boolean playedMoved(Location from, Location to) {
        return !(from.getBlockX() == to.getBlockX() && to.getBlockZ() == from.getBlockZ());
    }
}
