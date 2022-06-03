package me.jaron.gamerica.plugin.commands;

import me.jaron.gamerica.plugin.managers.NPCManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestNPCCommand implements CommandExecutor {

    private final NPCManager npcManager;

    public QuestNPCCommand(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //make sure its a player
        if(sender instanceof Player p){

            if (args.length == 0){
                npcManager.spawnJeff(p, ChatColor.DARK_GREEN + "Quest" + ChatColor.GOLD + " Master");
            }else if (args.length == 1) {
                npcManager.spawnJeff(p, args[0]);
            }


        }else{
            System.out.println("Be a player, dummy.");
        }

        return true;
    }
}
