package me.jaron.plugin.managers;

import me.jaron.plugin.MainClass;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class MoneyManager {
    private final MainClass plugin;
    private FileConfiguration moneyConfig = null;
    private File configFile = null;

    public MoneyManager(MainClass plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        this.moneyConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("money.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.moneyConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.moneyConfig == null) {
            reloadConfig();
        }
        return this.moneyConfig;
    }

    public void saveConfig() {
        if (this.moneyConfig == null || this.configFile == null) {
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save Config to" + this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "money.yml");

        if (!this.configFile.exists()) {
            this.plugin.saveResource("money.yml", false);
        }

    }
}
