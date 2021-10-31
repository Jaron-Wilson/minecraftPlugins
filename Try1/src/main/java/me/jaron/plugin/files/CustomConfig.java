package me.jaron.plugin.files;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    private static File file;
    private static FileConfiguration customFile;

    //find or create custom config file
    public static void setup() {

//        file = new File(Try1.getInstance().getDataFolder(), "customconfig.yml");

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        }catch (IOException e){
            System.out.println(ChatColor.AQUA + "Could not save file");
        }
    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }


}
