package me.jaron.plugin.minigames.tag.listeners;

import me.jaron.plugin.MainClass;
import net.minecraft.network.chat.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TaggedEvent implements Listener {

    private MainClass plugin;

    public TaggedEvent(MainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTag(EntityDamageByEntityEvent event) {
        if (!plugin.tagGame.isPlaying()) return;
        if (! (event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Player)) return;

        event.setCancelled(true);

        if ((Player) event.getDamager() != plugin.tagGame.getIt()) return;
        ((Player) event.getDamager()).setGlowing(false);

        plugin.tagGame.tagged((Player) event.getEntity());

        event.getEntity().getWorld().spawnParticle(Particle.FLASH, event.getEntity().getLocation(), 0);

        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + ((Player) event.getEntity()).getName() +
        " is it!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.tagGame.isPlaying()) return;

        event.getPlayer().setScoreboard(plugin.tagGame.getBoard());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!plugin.tagGame.isPlaying()) return;

        if (plugin.tagGame.getIt() == player) {
            player.setGlowing(false);
            plugin.tagGame.end();
            Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD +
            player.getName() + " has left the game... sore loser");
            plugin.eco.withdrawPlayer(player, 100);
        }
    }


}