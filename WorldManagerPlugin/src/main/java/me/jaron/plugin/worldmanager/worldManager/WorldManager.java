package me.jaron.plugin.worldmanager.worldManager;

import me.jaron.plugin.worldmanager.WorldManagerPlugin;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;

public class WorldManager {
    private WorldManagerPlugin plugin;


    public WorldManager(WorldManagerPlugin plugin) {
        this.plugin = plugin;
    }

    public WorldCreator createDefaultWorld(String worldName, Difficulty difficulty, Command worldCommand, String newWorldConfigLocation, World world, Location spawnLocation, boolean generateStrutures){
        if (world == null) {
            WorldCreator creator = new WorldCreator(worldName);
            creator.environment(World.Environment.NORMAL);
            creator.generateStructures(generateStrutures);
            world = creator.createWorld();
            world.setDifficulty(Difficulty.valueOf(String.valueOf(difficulty)));
            spawnLocation = world.getSpawnLocation();
        } else if (plugin.getConfig().getString(newWorldConfigLocation) != null) {
            spawnLocation = world.getSpawnLocation();
        }


        return null;
    }


}
