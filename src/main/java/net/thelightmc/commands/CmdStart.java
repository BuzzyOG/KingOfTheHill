package net.thelightmc.commands;

import net.thelightmc.GameManager;
import net.thelightmc.exceptions.NoGameRunningException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdStart extends Command{
    private final GameManager gameManager;

    public CmdStart(GameManager gameManager) {
        super("start");
        this.gameManager = gameManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        try {
            gameManager.getGame();
        } catch (NoGameRunningException e) {
            e.getSuppressed();
            gameManager.startGame(gameManager.getNewGame());
            try {
                gameManager.getGame().getLocation().isInzone(((Player)sender).getLocation());
            } catch (NoGameRunningException e1) {
                e1.printStackTrace();
            }
        }
    }
}
