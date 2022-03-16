package me.jaron.plugin.commands;

import me.jaron.plugin.MainClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    MainClass plugin;

    public VanishCommand(MainClass plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;
            if (plugin.invisible_list.contains(player)){
                //Since they are on the list, remove them and make them visible
                for (Player people : Bukkit.getOnlinePlayers()){
                    people.showPlayer(plugin, player);
                }
                plugin.invisible_list.remove(player);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.BLUE + "You are now visible to other players"));
            }else if (!plugin.invisible_list.contains(player)){
                //Hide yourself from every other player on the server
                for (Player people : Bukkit.getOnlinePlayers()){
                    people.hidePlayer(plugin, player); //Hides you from that player
                }
                plugin.invisible_list.add(player);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You are now invisible to other players"));
            }
        }
        return true;
    }
}
