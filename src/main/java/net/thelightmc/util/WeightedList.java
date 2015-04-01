package net.thelightmc.util;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class WeightedList<E> {
    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random = ThreadLocalRandom.current();
    private double total = 0;

    public WeightedList() {}



    public void add(double weight, E result) {
        if (weight <= 0) return;
        total += weight;
        map.put(total, result);
    }

    public E get() {
        double value = random.nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }
}
