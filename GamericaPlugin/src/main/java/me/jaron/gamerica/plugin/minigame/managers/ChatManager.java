package me.jaron.gamerica.plugin.minigame.managers;

import me.jaron.gamerica.plugin.GamericaPlugin;
import org.bukkit.ChatColor;

public class ChatManager {

    private GamericaPlugin main;
    public ChatManager(GamericaPlugin main) {
        this.main = main;
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String prefix = format("&8[&2Gamerica&eMiniGame&8] &e");
    public String permission = format(prefix + "&cYou do not have permission to access this command.");

}
