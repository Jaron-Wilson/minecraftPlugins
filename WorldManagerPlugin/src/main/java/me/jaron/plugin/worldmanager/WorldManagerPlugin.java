package me.jaron.plugin.worldmanager;

import me.jaron.plugin.worldmanager.worldManager.WorldManager;
import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldManagerPlugin extends JavaPlugin {

    private final Difficulty difficulty = Difficulty.valueOf(getConfig().getString("WorldsProject.worlds.settings.difficulty"));
    private final Boolean generateStructures = getConfig().getBoolean("WorldsProject.worlds.settings.generateStructures");
    private final String configLocation = getConfig().getString("WorldsProject.worlds.worldname");

    @Override
    public void onEnable() {
        // Plugin startup logic

//        INITIALIZE PLUGIN/MAIN CLASS
        WorldManagerPlugin plugin = this;
        WorldManager worldManager = new WorldManager(this);

//        CONFIG
        getConfig().options().copyDefaults();
        saveDefaultConfig();


//        LOAD EXTRA WORLDS

        if (getConfig().getBoolean("WorldsProject.worlds.createnew") == true) {
//                                    Also the name ⬇/🔻
            worldManager.createDefaultWorld(configLocation, difficulty,null,configLocation, null,null, generateStructures);
        }
        else {
            System.out.println("You do not have the WorldsProject.worlds.createnew Enabled on config.yml");
        }



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
