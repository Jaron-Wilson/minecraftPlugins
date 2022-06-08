package me.jaron.gamerica.plugin.minigame.commands;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.Gamestates;
import me.jaron.gamerica.plugin.minigame.countdowns.GameEnd;
import me.jaron.gamerica.plugin.minigame.countdowns.PreGameTimer;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinGameCommand implements CommandExecutor {

    private GamericaPlugin main;
    private GameEnd gameEnd;
    private String name;
    private ChatManager manager;

    public JoinGameCommand(GamericaPlugin main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            manager = new ChatManager(main);
            if (cmd.getName().equalsIgnoreCase("join")) {

                if (main.waiting.size() > main.getPlayerAmount()) {
                    p.sendMessage(manager.minigameprefix + "The game is full please try again later.");
                    return true;
                }
                else if (main.waiting.size() <= main.getPlayerAmount()) {
                    if (main.getGamestate(main.miniGameLobby) == Gamestates.INGAME) {
                        p.sendMessage(manager.minigameprefix + "There is a game going please be patient you will be notified when game is done.");
                        return true;
                    }
                    else if (main.getGamestate(main.miniGameLobby) != Gamestates.INGAME) {
                        if (!main.waiting.contains(p)) {
                            main.waiting.add(p);
                            p.sendMessage(new ChatManager(main).minigameprefix + "You have joined the queue.");
                            if (main.waiting.size() == main.getPlayerAmount()) {
                                new PreGameTimer(main).startCountdown();
                                p.setGameMode(GameMode.SPECTATOR);
                            }
                            name = p.getDisplayName();
                            p.setCustomName("InQueue " + p.getDisplayName());
                            return true;
                        }
                        else if (main.waiting.contains(p)) {
                            p.sendMessage(manager.minigameprefix + "You are already in the game!");
                            return true;
                        }
                        p.setCustomNameVisible(true);
                    }
                }
            }
            else if (cmd.getName().equalsIgnoreCase("lobby")) {
                gameEnd = new GameEnd(main);
                System.out.println(main.mainWorldLobby);
                if (main.mainWorldLobby != null) {
                    p.teleport(main.mainWorldLobby.getSpawnLocation());
                    p.sendMessage(new ChatManager(main).minigameprefix + "You have joined the lobby.");
                    setPlayerThings(p);
                }
            }
        } else {
            System.out.println("You must be a player.");
        }
        return true;
    }

    private Boolean checkPlayer(Player p) {
        gameEnd.check();
        if (main.waiting.contains(p)) {
            main.waiting.remove(p);
        } else if (main.alive.contains(p)) {
            main.alive.remove(p);
        } else if (main.spectating.contains(p)) {
            main.spectating.remove(p);
        }
        return true;
    }

    private void setPlayerThings(Player p) {
        if (checkPlayer(p)) {
            if (!main.waiting.contains(p) && !main.alive.contains(p) && !main.spectating.contains(p)) {
                p.sendMessage("You can join a minigame by typing " + ChatColor.GOLD + ChatColor.BOLD + "/join");
                if (p.getGameMode() == GameMode.SPECTATOR) p.setGameMode(GameMode.SURVIVAL);
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setCustomNameVisible(false);
            }else {
                checkPlayer(p);
            }
        }
    }
}