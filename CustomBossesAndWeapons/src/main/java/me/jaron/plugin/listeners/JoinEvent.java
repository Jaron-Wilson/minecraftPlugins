package me.jaron.plugin.listeners;

import me.jaron.plugin.MainClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.KeybindComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class JoinEvent implements Listener {

    MainClass plugin;

    public JoinEvent(MainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        //When a player joins, make the players on the invisible list invisible for them
        Player player = e.getPlayer();
        for (int i = 0; i < plugin.invisible_list.size(); i++) {
            player.hidePlayer(plugin, plugin.invisible_list.get(i));
        }

//        plugin.tab.addFooter("&bPlayers Online: " + Bukkit.getOnlinePlayers().size());

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                Player player = e.getPlayer();
                player.sendMessage(ChatColor.GREEN + "Welcome " + player.getName() + " to the server!");
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("You have: $" +
                        plugin.eco.getBalance(player) + " money in your bank"));
            }
        }, 35L);
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent e) {
        //When a player joins, make the players on the invisible list invisible for them
        Player player = e.getPlayer();
        for (int i = 0; i < plugin.invisible_list.size(); i++) {
            player.hidePlayer(plugin, plugin.invisible_list.get(i));
        }
//                plugin.tab.addFooter("&bPlayers Online: " + Bukkit.getOnlinePlayers().size());

    }


}
