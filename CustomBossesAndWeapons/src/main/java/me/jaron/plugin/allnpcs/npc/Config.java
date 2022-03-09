package me.jaron.plugin.allnpcs.npc;

import me.jaron.plugin.MainClass;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private final FileConfiguration config;

    public Config(MainClass plugin) {
        this.config = plugin.getConfig();
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }
}
