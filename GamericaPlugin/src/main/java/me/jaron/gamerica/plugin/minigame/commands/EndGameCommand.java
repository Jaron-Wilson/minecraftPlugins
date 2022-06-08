package me.jaron.gamerica.plugin.minigame.commands;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.Gamestates;
import me.jaron.gamerica.plugin.minigame.countdowns.GameEnd;
import me.jaron.gamerica.plugin.minigame.countdowns.PreGameTimer;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndGameCommand implements CommandExecutor {

    private GamericaPlugin main;
    private GameEnd gameEnd;
    private String name;
    private ChatManager manager;

    public EndGameCommand(GamericaPlugin main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (sender instanceof Player p) {
            gameEnd = new GameEnd(main);
            manager = new ChatManager(main);
            gameEnd.end(p);
        }
        return true;
    }
}