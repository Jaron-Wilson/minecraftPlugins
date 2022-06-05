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
import org.jetbrains.annotations.NotNull;

public class JoinGameCommand implements CommandExecutor {

    private GamericaPlugin main;

    public JoinGameCommand(GamericaPlugin main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("join")) {
                main.waiting.add(p);
                p.setGameMode(GameMode.ADVENTURE);

                p.sendMessage(new ChatManager(main).prefix + "You have joined the queue.");
                if (Bukkit.getOnlinePlayers().size() >= main.getPlayerAmount()) {
                    new PreGameTimer(main).startCountdown();
                    p.setGameMode(GameMode.SPECTATOR);
                }
            }
            else if (cmd.getName().equalsIgnoreCase("lobby")) {
                System.out.println(main.mainWorldLobby);
                if (main.mainWorldLobby != null) {
                    p.teleport(main.mainWorldLobby.getSpawnLocation());
                    p.sendMessage(new ChatManager(main).prefix + "You have joined the lobby.");
                    if (!main.waiting.contains(p) || !main.alive.contains(p)|| !main.spectating.contains(p)){
                        if (p.getGameMode() == GameMode.SPECTATOR) p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage("You can join a minigame by typing " + ChatColor.GOLD + ChatColor.BOLD + "/join");
                    }else if (main.waiting.contains(p) || main.alive.contains(p)|| main.spectating.contains(p)) {
                        main.waiting.remove(p);
                        main.alive.remove(p);
                        main.spectating.remove(p);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.setHealth(20);
                        p.setFoodLevel(20);
                    }
                }
            }

        }else {
            System.out.println("You must be a player.");
        }
        return true;
    }
}