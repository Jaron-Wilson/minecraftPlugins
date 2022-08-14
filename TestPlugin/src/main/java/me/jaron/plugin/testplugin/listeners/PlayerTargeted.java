package me.jaron.plugin.testplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerTargeted implements Listener {
    @EventHandler
    public void onPlayer(PlayerEvent event) {
        Player player = event.getPlayer();

        String fistTimePlayed = String.valueOf(player.getFirstPlayed());
        String lastTimePlayed = String.valueOf(player.getLastPlayed());

    }
}
