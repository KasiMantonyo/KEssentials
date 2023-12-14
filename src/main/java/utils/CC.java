package utils;

import org.bukkit.ChatColor;

public class CC {
    public static String translate(String texto) {
        return ChatColor.translateAlternateColorCodes('&', texto);
    }
}
