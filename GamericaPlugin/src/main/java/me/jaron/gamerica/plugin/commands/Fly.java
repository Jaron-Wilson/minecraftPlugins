package me.jaron.gamerica.plugin.commands;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Fly implements CommandExecutor {

    private ArrayList<Player> list_of_flying_people = new ArrayList<>();
    private GamericaPlugin plugin;
    private ChatManager chatManager;

    public Fly(GamericaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        chatManager = new ChatManager(plugin);

        if(sender instanceof Player){
            Player player = (Player) sender;
            String onMessage = plugin.getConfig().getString("on-message");
            String offMessage = plugin.getConfig().getString("off-message");
            if(player.hasPermission("gamericaperms.fly")){
                if (list_of_flying_people.contains(player)){
                    list_of_flying_people.remove(player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',chatManager.flyprefix + offMessage));
                    player.setAllowFlight(false);
                }else if(!list_of_flying_people.contains(player)){
                    list_of_flying_people.add(player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', chatManager.flyprefix + onMessage));
                    player.setAllowFlight(true);
                }
            }else{
                player.sendMessage(chatManager.flyprefix + chatManager.permission);
            }
        }else{
            System.out.println(chatManager.flyprefix + "You must be a player to fly.");
        }

        return true;
    }
}
