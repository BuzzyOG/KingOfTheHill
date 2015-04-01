package net.thelightmc;

import net.thelightmc.exceptions.NoGameRunningException;
import net.thelightmc.util.LocationUtil;
import net.thelightmc.util.WeightedList;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameManager {
    private final WeightedList<ItemStack> weights;

    protected GameManager(WeightedList<ItemStack> weights) {
        this.weights = weights;
    }
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
    public Collection<? extends ItemStack> getReward() {
        return this.getRewards(1);
    }
    public Collection<? extends ItemStack> getRewards(int amount) {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            list.add(weights.get());
        }
        return list;
    }

    public KothGame getNewGame() {
        return new KothGame("VTest", LocationUtil.getList().values().iterator().next());
    }
}
