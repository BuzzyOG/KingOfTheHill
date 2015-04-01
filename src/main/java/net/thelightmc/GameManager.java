package net.thelightmc;

import net.thelightmc.exceptions.NoGameRunningException;
import net.thelightmc.util.LocationUtil;

public class GameManager {
    protected GameManager() {}
    private KothGame game;
    public KothGame getGame() throws NoGameRunningException {
        if (game == null) {
            throw new NoGameRunningException("No game available currently");
        }
        return game;
    }

    public void startGame(KothGame game) {
        this.game = game;
    }

    public KothGame getNewGame() {
        return new KothGame("VTest", LocationUtil.getList().values().iterator().next());
    }
}
