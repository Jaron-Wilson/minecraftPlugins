package me.jaron.gamerica.plugin.minigame.commands;

import me.jaron.gamerica.plugin.minigame.countdowns.PreGameTimer;
import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Start implements CommandExecutor {

    private GamericaPlugin main;
    public Start(GamericaPlugin main) {
        this.main = main;
    }
    private int peopleWaiting = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = (Player) sender;

        if (!p.hasPermission("game.start")) {
            p.sendMessage(new ChatManager(main).permission);
            return true;
        } else if (cmd.getName().equalsIgnoreCase("start")) {
            new PreGameTimer(main).startCountdown();
            p.sendMessage(new ChatManager(main).minigameprefix + "You have started the game.");

            for (int i = 0; i < peopleWaiting; i++) {
                main.waiting.get(i).teleport(main.loc);
            }
            p.teleport(main.loc);
            return true;
        }
        return true;
    }
}
