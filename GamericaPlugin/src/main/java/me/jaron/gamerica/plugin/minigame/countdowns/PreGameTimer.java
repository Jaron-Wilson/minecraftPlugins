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
    private ChatManager manager;
    public PreGameTimer(GamericaPlugin main) {
        this.main = main;
    }

    public void startCountdown() {
        new BukkitRunnable() {

            int waitPlayers = main.waiting.size();
            int time = 30;
            

            @Override
            public void run() {
                manager = new ChatManager(main);

                for (int i = 0; i < waitPlayers; i++) {
                    if (time > 0) {
                        main.setGamestate(main.miniGameLobby, Gamestates.PREGAME);
                        if (time == 30) {
//                            Check Before sending messages
                            checkPlayers(i, time);
//                            main.waiting.get(i).sendMessage(manager.prefix + "Game starting in 30 seconds.");
                        }
                        if (time == 20) {
//                            Check Before sending messages
                            checkPlayers(i, time);
//                            main.waiting.get(i).sendMessage(manager.prefix + "Game starting in 20 seconds.");
                        }
                        if (time == 10) {
//                            Check Before sending messages
                            checkPlayers(i, time);
//                            main.waiting.get(i).sendMessage(manager.prefix + "Game starting in 10 seconds.");
                        }
                        if (time == 5) {
//                            Check Before sending messages
                            checkPlayers(i, time);
//                            main.waiting.get(i).sendMessage(manager.prefix + "Game starting in 5 seconds.");
                        }
                        if (time == 4) {
//                            Check Before sending messages
                            checkPlayers(i, time);
//                            main.waiting.get(i).sendMessage(manager.prefix + "Game starting in 4 seconds.");
                        }
                        if (time == 3) {
//                            Check Before sending messages
                            checkPlayers(i, time);
//                            main.waiting.get(i).sendMessage(manager.prefix + "Game starting in 3 seconds.");
                        }
                        if (time == 2) {
//                            Check Before sending messages
                            checkPlayers(i, time);
//                            main.waiting.get(i).sendMessage(manager.prefix + "Game starting in 2 seconds.");
                        }
                        if (time == 1) {
//                            Check Before sending messages
                            checkPlayers(i, time);
//                            main.waiting.get(i).sendMessage(manager.prefix + "Game starting in 1 second.");

                            if (main.waiting.size() == main.getPlayerAmount()) {
                                for (int p = 0; p < waitPlayers; p++) {
                                    main.waiting.get(p).teleport(main.loc);
                                    main.waiting.get(p).setGameMode(GameMode.SURVIVAL);
                                    main.alive.addAll(main.waiting);
                                    main.waiting.remove(p);
                                    main.alive.get(i).setCustomName("InGame " + main.alive.get(i).getDisplayName());
                                    main.alive.get(i).setCustomNameVisible(true);

                                }

                                // DO PREGAME THINGS, SCATTER, KITS, ETC
                            }else {
                                checkPlayers(i,time);
                            }
                        }
                        time--;
                    } else {
                        if (main.alive.size() != 0) {

                            main.alive.get(i).sendMessage(manager.prefix + "The game has now started!");
                            main.setGamestate(main.miniGameLobby, Gamestates.INGAME);
                        }

                        cancel();
                    }
                }
            }
            }.

            runTaskTimer(main,20L,20L);
        }

        private void checkPlayers(int i, int times) {
            if (main.waiting.size() < main.getPlayerAmount()){
                main.setGamestate(main.miniGameLobby, Gamestates.PREGAME);
                broadcastMessage(manager.prefix + "Game Is Done, No Players Joined");
            }else if (main.waiting.size() >= main.getPlayerAmount()) {
                main.waiting.get(i).sendMessage(manager.prefix + "Game starting in " + times + " seconds.");
            }
        }

}
