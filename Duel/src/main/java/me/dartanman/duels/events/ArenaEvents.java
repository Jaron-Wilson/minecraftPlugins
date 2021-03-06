package me.dartanman.duels.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.dartanman.duels.Duels;
import me.dartanman.duels.arenas.Arena;
import me.dartanman.duels.utils.PlayerRestorationInfo;
import net.md_5.bungee.api.ChatColor;
/**
 * ArenaEvents - Listens to multiple events relating to Duels in Arenas
 * @author Austin Dart (Dartanman)
 */
public class ArenaEvents implements Listener{
	
	private Duels plugin;
	
	public ArenaEvents(Duels pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event)
	{
		Player player = event.getPlayer();
		if(plugin.getArenaManager().getArena(player) != null)
		{
			Arena arena = plugin.getArenaManager().getArena(player);
			if(!arena.getActive())
			{
				return;
			}
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You cannot drop items in a duel!");
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		// Currently unused, but will be used if/when I support Bungeecord.
		boolean bungee = false;
		if(bungee) {
			// blank for now
		}else {
			// Bukkit/Spigot
			if(plugin.getArenaManager().getArena(player) != null) {
				Arena arena = plugin.getArenaManager().getArena(player);
				if(!arena.getActive()) {
					// They joined the arena but the duel has not started yet
					return;
				}
				event.getDrops().clear();
				if(arena.getPlayer1().getName().equals(player.getName())) {
					arena.end(arena.getPlayer2(), arena.getPlayer1());
				}else {
					arena.end(arena.getPlayer1(), arena.getPlayer2());
				}
			}
		}
	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		// Currently unused, but will be used if/when I support Bungeecord.
		boolean bungee = false;
		if(bungee) {
			// blank for now
		}else {
			// Bukkit/Spigot
			if(plugin.getArenaManager().getArena(player) != null) {
				Arena arena = plugin.getArenaManager().getArena(player);
				if(!arena.getActive()) {
					// They joined the arena but the duel has not started yet
					return;
				}
				if(arena.getPlayer1().getName().equals(player.getName())) {
					arena.end(arena.getPlayer2(), arena.getPlayer1());
				}else {
					arena.end(arena.getPlayer1(), arena.getPlayer2());
				}
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		PlayerRestorationInfo pri = null;
		for(PlayerRestorationInfo priL : PlayerRestorationInfo.pris) {
			if(priL.getPlayer().getName().equals(player.getName())) {
				pri = priL;
			}
		}
		if(pri == null) {
			return;
		}
		final PlayerRestorationInfo priF = pri;
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
        		priF.apply();
        		PlayerRestorationInfo.pris.remove(priF);
            }
        }, 10L);
	}

}
