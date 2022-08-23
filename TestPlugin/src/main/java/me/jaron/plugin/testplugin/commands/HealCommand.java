package me.jaron.plugin.testplugin.commands;

import me.jaron.plugin.testplugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    TestPlugin plugin;

    public HealCommand(TestPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        // This method is called, when somebody uses our command
            Player player = (Player) sender;
            if (alias.equalsIgnoreCase("heal")) {
                if (args.length == 0) {
//Player only typed '/heal' so let's heal them back!
                    player.setHealth(20);
                } else {
//Player typed something more
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target == null) {
//Target is not online
                        player.sendMessage("Your target " + args[0] + " is not online!");
                    } else {
//Targets online
                        player.sendMessage("You've healed " + args[0]);
                        target.setHealth(20);
                    }
                }
            } else if (alias.equalsIgnoreCase("reset-health")) {
                player.setHealthScale(20);

            }
        return true;
    }
}