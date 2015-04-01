package net.thelightmc.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public class ItemLoader {
    public static void loadFromString(String s,WeightedList<ItemStack> list) {
        String[] split = s.split(":");
        ItemStack itemStack = new ItemStack(Material.getMaterial(split[0]));
        ItemMeta meta = itemStack.getItemMeta();
        double weight = Double.parseDouble(split[1]);
        if (split.length > 1) {
            itemStack.setAmount(Integer.valueOf(split[2]));
        }
        if (split.length > 2) {
            String[] strings = split[3].split("\n");
            ArrayList<String> arrayList = new ArrayList<>();
            Collections.addAll(arrayList,strings);
            meta.setLore(arrayList);
        }
        itemStack.setItemMeta(meta);
        list.add(weight,itemStack);
    }
}
