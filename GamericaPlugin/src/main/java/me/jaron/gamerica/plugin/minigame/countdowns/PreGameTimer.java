package me.jaron.gamerica.plugin.minigame.countdowns;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.Gamestates;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import net.minecraft.network.protocol.status.ServerStatus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static org.bukkit.Bukkit.*;

public class PreGameTimer {

    private GamericaPlugin main;
    public PreGameTimer(GamericaPlugin main) {
        this.main = main;
    }

    public void startCountdown() {
        new BukkitRunnable() {

            int waitPlayers = main.waiting.size();


            int number = 30;

            @Override
            public void run() {
                for (int i = 0; i < waitPlayers; i++) {
                    if (number > 0) {

                        if (number == 30) {
                            main.setGamestate(main.getWorld(main.getConfig().getString("worlds.minigame")), Gamestates.PREGAME);

                            main.waiting.get(i).sendMessage(new ChatManager(main).prefix + "Game starting in 30 seconds.");
                        }
                        if (number == 20) {
                            main.waiting.get(i).sendMessage(new ChatManager(main).prefix + "Game starting in 20 seconds.");
                        }
                        if (number == 10) {
                            main.waiting.get(i).sendMessage(new ChatManager(main).prefix + "Game starting in 10 seconds.");
                        }
                        if (number == 5) {
                            main.waiting.get(i).sendMessage(new ChatManager(main).prefix + "Game starting in 5 seconds.");
                        }
                        if (number == 4) {
                            main.waiting.get(i).sendMessage(new ChatManager(main).prefix + "Game starting in 4 seconds.");
                        }
                        if (number == 3) {
                            main.waiting.get(i).sendMessage(new ChatManager(main).prefix + "Game starting in 3 seconds.");
                        }
                        if (number == 2) {
                            main.waiting.get(i).sendMessage(new ChatManager(main).prefix + "Game starting in 2 seconds.");
                        }
                        if (number == 1) {
                            main.waiting.get(i).sendMessage(new ChatManager(main).prefix + "Game starting in 1 second.");

                            for (int p = 0; p < waitPlayers; p++) {
                                main.waiting.get(p).teleport(main.loc);
                                main.waiting.get(p).setGameMode(GameMode.SURVIVAL);
                                main.alive.addAll(main.waiting);
                                main.waiting.remove(p);

                            }

                            // DO PREGAME THINGS, SCATTER, KITS, ETC
                        }
                        number--;
                    } else {
                        if (main.alive.size() != 0) {
                            main.alive.get(i).sendMessage(new ChatManager(main).prefix + "The game has now started!");
                            main.setGamestate(main.getWorld(main.getConfig().getString("worlds.minigame")), Gamestates.INGAME);
                        }

                        cancel();
                    }
                }
            }
            }.

            runTaskTimer(main,20L,20L);
        }

}
