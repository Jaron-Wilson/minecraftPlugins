package me.jaron.gamerica.plugin.minigame.listeners;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.countdowns.PreGameTimer;
import me.jaron.gamerica.plugin.minigame.Gamestates;
import me.jaron.gamerica.plugin.minigame.managers.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    private final GamericaPlugin main;
    public Join(GamericaPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (p.getWorld().equals(main.miniGameLobby)) {
            event.setJoinMessage("");
            System.out.println("Welcome to the server, " + p.getName());

            new PlayerManager(main).handle(p);
            if (main.waiting.size() >= main.getPlayerAmount()) {
                new PreGameTimer(main).startCountdown();
            } else if (main.getGamestate(main.miniGameLobby) == Gamestates.PREGAME) {
                main.waiting.add(p);
            }
        }else {
            event.setJoinMessage("Welcome to the server, " + p.getName());
            if (!p.isOp()) {
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage("You can join a minigame by typing " + ChatColor.GOLD + ChatColor.BOLD + "/join");
            }else {
                if (p.getGameMode() == GameMode.SPECTATOR){
                    p.setGameMode(GameMode.CREATIVE);
                }
                p.sendMessage("You can join a minigame by typing " + ChatColor.GOLD + ChatColor.BOLD + "/join");
            }
        }
    }
}
