package net.thelightmc.util;

import org.bukkit.ChatColor;

public class StringUtils {
    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&',string);
    }
    public static String format(String string,Object... objects) {
        return colorize(String.format(string,objects));
    }
}
