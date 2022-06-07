package me.jaron.gamerica.plugin.afk.commands;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.afk.AFKManager;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IsAFKCommand implements CommandExecutor {

    private final AFKManager afkManager;
    private GamericaPlugin main;
    private ChatManager chatManager;

    public IsAFKCommand(AFKManager afkManager, GamericaPlugin main){
        this.afkManager = afkManager;
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        chatManager = new ChatManager(main);

        if (sender instanceof Player p){

            if(args.length == 0){

                if(afkManager.isAFK(p)){
                    p.sendMessage(chatManager.afkprefix + "You are currently AFK.");
                }else{
                    p.sendMessage(chatManager.afkprefix + "You are not currently AFK.");
                }

            }else{
                Player target = Bukkit.getPlayerExact(args[0]);

                if(afkManager.isAFK(target)){
                    p.sendMessage(chatManager.afkprefix + target.getDisplayName() + " is currently AFK.");
                }else{
                    p.sendMessage(chatManager.afkprefix + target.getDisplayName() + " is not currently AFK.");
                }

            }

        }

        return true;
    }
}