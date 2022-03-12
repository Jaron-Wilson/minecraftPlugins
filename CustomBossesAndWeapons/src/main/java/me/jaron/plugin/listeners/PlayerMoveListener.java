package me.jaron.plugin.listeners;

import me.jaron.plugin.MainClass;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    MainClass plugin;

    public PlayerMoveListener(MainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent e){
        if (plugin.getConfig().getBoolean("launchpads.enable")){
            Player p = e.getPlayer();
            Location blockUnder = p.getLocation();
            blockUnder.setY(blockUnder.getY() - 1);
            if (p.getLocation().getBlock().getType().equals(Material.valueOf(plugin.getConfig().getString("launchpads.top-block"))) && blockUnder.getBlock().getType().equals(Material.valueOf(plugin.getConfig().getString("launchpads.under-block")))){
                p.setVelocity(p.getLocation().getDirection().multiply(3).setY(2));
                plugin.jumping_players.add(p);
                if (plugin.getConfig().getBoolean("launchpads.message")){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("launchpads.launch-message")));
                }
            }
        }
    }

}
