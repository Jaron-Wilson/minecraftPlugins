package me.jaron.plugin.testplugin.commands;

import me.jaron.plugin.testplugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {

    TestPlugin plugin;

    public TimeCommand(TestPlugin plugin) {
        this.plugin = plugin;
    }
        // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (command.getName().equalsIgnoreCase("check")) {
                if (args.length == 0) { //Sender only typed '/check' and nothing else
                    sender.sendMessage("You only typed check," + " so Lets check you!");
                    player.sendMessage(String.valueOf(plugin.getBlocksBroken().get(player.getPlayer())));

                } else { //Sender typed more then 1 argument, so args[0] can't be null.
                    if (args[0].equalsIgnoreCase(player.getName())) { //Sender typed '/check name'
                        player.sendMessage(String.valueOf(plugin.getBlocksBroken().get(player.getPlayer())));
                    } else { //Sender had attest 1 argument, but didn't type sir as a second one!
//args[0] also returns string, so lets send the message right back to them!
                        sender.sendMessage("Your first argument was: " + args[0]);
                        if (Bukkit.getOnlinePlayers().contains(args[0])) {
                            player.sendMessage(String.valueOf(plugin.getBlocksBroken().get((args[0]))));
                        }else {
                            player.sendMessage("Player is not online!");
                        }

                    }
//                if (args.length == 1) {
//                    player.sendMessage(String.valueOf(plugin.getBlocksBroken().get(args)));
//                }else {
//                    player.sendMessage(String.valueOf(plugin.getBlocksBroken().get(player.getPlayer())));
//                }
                }
            }
        }
        return true;
    }
}