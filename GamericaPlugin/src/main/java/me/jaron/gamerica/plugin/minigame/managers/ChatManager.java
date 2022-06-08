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

    public String minigameprefix = format("&8[&2Gamerica&eMiniGame&8] &e");
    public String afkprefix = format("&8[&2Gamerica&eAfk&8] &e");
    public String questprefix = format("&8[&2Gamerica&eQuests&8] &e");
    public String flyprefix = format("&8[&2Gamerica&eFly&8] &e");
    public String mainprefix = format("&8[&2Gamerica&ePlugin&8] &e");
    public String permission = format(mainprefix + "&cYou do not have permission to access this command.");
    public String needtobeplayer = format(mainprefix + "&cYou need to be a player to access this command.");

}
