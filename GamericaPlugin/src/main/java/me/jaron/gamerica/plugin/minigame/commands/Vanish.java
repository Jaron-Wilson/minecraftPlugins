package me.jaron.gamerica.plugin.minigame.commands;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vanish implements CommandExecutor {

    private GamericaPlugin main;
    public Vanish(GamericaPlugin main) {
        this.main = main;
    }

    public void toggleVanish(Player player) {
        if (!main.vanished.contains(player)) {
            main.vanished.add(player);
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.hidePlayer(player);
            }
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(new ChatManager(main).minigameprefix + "You have been vanished.");
        } else if (main.vanished.contains(player)) {
            main.vanished.remove(player);
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.showPlayer(player);
            }
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(new ChatManager(main).minigameprefix + "You have been un-vanished.");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = (Player) sender;

        if (!p.hasPermission("server.vanish")) {
            p.sendMessage(new ChatManager(main).permission);
            return true;
        } else if (cmd.getName().equalsIgnoreCase("vanish")) {
            toggleVanish(p);
            return true;
        }
        return true;
    }
}
