package me.jaron.plugin.testplugin;

import me.jaron.plugin.testplugin.commands.HealCommand;
import me.jaron.plugin.testplugin.listeners.BlockBrokenQuest;
import me.jaron.plugin.testplugin.listeners.TimeCheckerEvents;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class TestPlugin extends JavaPlugin implements Listener {
    private final HashMap<Player, Integer> blocksBroken = new HashMap<>();
    private final HashMap<Player, Long> timeOn = new HashMap<>();
    private final Object list = new ArrayList<>();

    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {

        config.addDefault("youAreAwesome", true);
        config.options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);

//        LISTENERS & COMMANDS
        getCommand("player").setExecutor(new HealCommand(this));
        getServer().getPluginManager().registerEvents(new TimeCheckerEvents(this), this);
        getServer().getPluginManager().registerEvents(new BlockBrokenQuest(this), this);


    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean("youAreAwesome")) {
            player.sendMessage("You are awesome!");
        } else {
            player.sendMessage("You are not awesome...");
        }
    }

    public HashMap<Player, Integer> getBlocksBroken() {
        return blocksBroken;
    }

    public HashMap<Player, Long> getTimeOn() {
        return timeOn;
    }

}
