//package me.jaron.plugin.managers;
//
//import me.jaron.plugin.MainClass;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.configuration.file.YamlConfiguration;
//
//import java.io.File;
//import java.io.IOException;
//
//public class ConfigManager {
//
//    private MainClass plugin = MainClass.getPlugin(MainClass.class);
//
////    Files and file configs
//    public FileConfiguration playerscfg;
//    public File playersFile;
//
//    public void setUpPlayerConfig(){
//        if (!plugin.getDataFolder().exists()) {
//            plugin.getDataFolder().mkdir();
//        }
//
//        playersFile = new File(plugin.getDataFolder(), "players.yml");
//
//        if (!playersFile.exists()) {
//            try {
//                playersFile.createNewFile();
//            }catch (IOException e) {
//                Bukkit.getServer().getConsoleSender().sendMessage("COUld not create the players.yml file");
//            }
//        }
//
//        playerscfg = YamlConfiguration.loadConfiguration(playersFile);
//        Bukkit.getServer().getConsoleSender().sendMessage("CREATED--------------------------");
//
//    }
//
//    public FileConfiguration getPlayers() {
//        return playerscfg;
//    }
//
//    public void savePlayers() {
//        try {
//            playerscfg.save(playersFile);
//            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "the players.yml file");
//
//        } catch (IOException e) {
//            Bukkit.getServer().getConsoleSender().sendMessage("COUld not save the players.yml file");
//        }
//    }
//
//    public void reloadPlayers() {
//        playerscfg = YamlConfiguration.loadConfiguration(playersFile);
//        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "file");
//
//    }
//
//}
