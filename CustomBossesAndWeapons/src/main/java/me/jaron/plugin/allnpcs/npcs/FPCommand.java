package me.jaron.plugin.allnpcs.npcs;

import me.jaron.plugin.MainClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FPCommand implements CommandExecutor {

//    private FakePlayersMain plugin = FakePlayersMain.getInstance();
    private MainClass plugin = MainClass.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("fp") || command.getName().equalsIgnoreCase("fakeplayer")) {

                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("create")) {
                        String npcName = args[1];
                        plugin.npcManager.createNPC(player, npcName);
                    }
                }
            }
        }
        return true;
    }
}
