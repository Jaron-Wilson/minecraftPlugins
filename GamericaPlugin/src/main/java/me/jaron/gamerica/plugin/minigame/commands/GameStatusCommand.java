package me.jaron.gamerica.plugin.minigame.commands;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.Gamestates;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameStatusCommand implements CommandExecutor {
    private ChatManager chatManager;
    private GamericaPlugin main;

    public GameStatusCommand(GamericaPlugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender player, Command command, String label, String[] args) {
        chatManager = new ChatManager(main);

        if (player.isOp()) {
            if (args.length == 0) {
                player.sendMessage(chatManager.minigameprefix + "Put in a world, 'minigames' or 'lobby' ");
            }else {
                if (args[0].equalsIgnoreCase("lobby")) {
                    player.sendMessage(chatManager.minigameprefix + main.getGamestate(main.mainWorldLobby));
                } else if (args[0].equalsIgnoreCase("minigames")) {
                    player.sendMessage(chatManager.minigameprefix + main.getGamestate(main.miniGameLobby));
                }
            }
        }


        return true;
    }
}
