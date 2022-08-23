package me.jaron.plugin.testplugin.commands;

import me.jaron.plugin.testplugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlocksBrokenCommand implements CommandExecutor {

    TestPlugin plugin;

    public BlocksBrokenCommand(TestPlugin plugin) {
        this.plugin = plugin;
    }
        // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;
        if (alias.equalsIgnoreCase("check")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("blocks")) {
//Player only typed '/heal' so let's heal them back!
                    sender.sendMessage("You only typed " + alias + " " + args[0] + " so Lets check you!");
                    player.sendMessage(ChatColor.DARK_GREEN +
                            String.valueOf(plugin.getBlocksBroken().get(player.getPlayer()))
                            + " Blocks");
                }
            } else if (args.length == 0) {
                player.sendMessage(
                        "You need to type: " + ChatColor.GOLD + "/check blocks" + ChatColor.DARK_PURPLE +
                                "\nOR"+ ChatColor.RESET + " you can do:" + ChatColor.GOLD + " /check blocks " + ChatColor.UNDERLINE + "Player name."
                );
            }else {
//Player typed something more
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
//Target is not online
                    player.sendMessage("Your target " + args[1] + " is not online!");
                } else {

                    if (args[0].equalsIgnoreCase("blocks")) {
//Targets online
                        sender.sendMessage( ChatColor.GOLD + args[1] + ChatColor.BLUE + " has only " + ChatColor.DARK_GREEN +
                                plugin.getBlocksBroken().get(target.getPlayer())
                                + " Blocks");
                    }
                }
            }
        }
        return true;
    }
}