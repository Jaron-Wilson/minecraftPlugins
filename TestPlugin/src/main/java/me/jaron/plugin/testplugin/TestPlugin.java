package me.jaron.plugin.testplugin;

import me.jaron.plugin.testplugin.commands.HealCommand;
import me.jaron.plugin.testplugin.commands.BlocksBrokenCommand;
import me.jaron.plugin.testplugin.listeners.BlockBrokenQuest;
import me.jaron.plugin.testplugin.listeners.TimeCheckerEvents;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class TestPlugin extends JavaPlugin {
    private HashMap<Player, Integer> blocksBroken = new HashMap<>();
    private HashMap<Player, Long> timeOn = new HashMap<>();
    private Object list = new ArrayList<>();

//    FileConfiguration config = getConfig();


    @Override
    public void onEnable() {
        //Config
//        config.options().copyDefaults(true);
//        saveConfig();


//        LISTENERS & COMMANDS
        getCommand("check").setExecutor(new BlocksBrokenCommand(this));
        getCommand("heal").setExecutor(new HealCommand(this));
        getServer().getPluginManager().registerEvents(new TimeCheckerEvents(this), this);
        getServer().getPluginManager().registerEvents(new BlockBrokenQuest(this), this);


    }

    @Override
    public void onDisable() {


    }

    public HashMap<Player, Integer> getBlocksBroken() {
        return blocksBroken;
    }

    public HashMap<Player, Long> getTimeOn() {
        return timeOn;
    }

}
