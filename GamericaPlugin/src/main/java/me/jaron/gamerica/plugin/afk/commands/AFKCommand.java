package me.jaron.gamerica.plugin.afk.commands;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.afk.AFKManager;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {

    private GamericaPlugin main;
    private final AFKManager afkManager;
    private ChatManager chatManager;

    public AFKCommand(AFKManager afkManager, GamericaPlugin main) {
        this.afkManager = afkManager;
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        chatManager = new ChatManager(main);

        if (sender instanceof Player p){

            if(afkManager.toggleAFKStatus(p)){

                p.sendMessage(chatManager.afkprefix + "You are now AFK.");

                afkManager.announceToOthers(p, true);

            }else{
                p.sendMessage(chatManager.afkprefix + "You are no longer AFK.");

                afkManager.announceToOthers(p, false);
            }

        }

        return true;
    }
}