package me.jaron.plugin.listeners;
import me.jaron.plugin.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

        plugin.tab.addFooter("&bPlayers Online: " + Bukkit.getOnlinePlayers().size());

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                Player player = e.getPlayer();
                player.sendMessage(ChatColor.GREEN + "Welcome " + player.getName() + " to the server!");
            }
        }, 20L);
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent e) {
        //When a player joins, make the players on the invisible list invisible for them
        Player player = e.getPlayer();
        for (int i = 0; i < plugin.invisible_list.size(); i++) {
            player.hidePlayer(plugin, plugin.invisible_list.get(i));
        }
                plugin.tab.addFooter("&bPlayers Online: " + Bukkit.getOnlinePlayers().size());

    }


}
