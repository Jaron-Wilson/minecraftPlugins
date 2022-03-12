package me.jaron.plugin.minigames.tag.tagcommand;

import me.jaron.plugin.MainClass;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommand implements CommandExecutor {

    private MainClass plugin;

    public TagCommand(MainClass plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch(args.length) {
                case 1:
                    //  /tag start/end
                    if (args[0].equalsIgnoreCase("start")) {
                        plugin.game.tagged(plugin.game.pickFirstIt());
                        player.sendMessage(ChatColor.RED + "Game Started");
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("end")) {
                        plugin.game.end();
                        player.sendMessage(ChatColor.RED + "Game Ended");
                        return true;
                    }
                default:
                    player.sendMessage(ChatColor.RED + "Try /tag <start/end>");
            }

            return true;
        }
        sender.sendMessage("no");
        return true;
    }
}