package me.jaron.plugin.testplugin.commands;

import me.jaron.plugin.testplugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HealCommand implements TabExecutor{

    TestPlugin plugin;

    public HealCommand(TestPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        // This method is called, when somebody uses our command
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(
                        "You need to type:" + ChatColor.BLUE + " /player " + ChatColor.GOLD + "check" + ChatColor.DARK_PURPLE +
                                "\nOR"+ ChatColor.RESET + " you can do:" + ChatColor.BLUE + " /player " + ChatColor.GOLD + "h/heal" +
                                "\nOR"+ ChatColor.RESET + " you can do:" + ChatColor.BLUE + " /player " + ChatColor.GOLD + "reset-health/r"
                );
            } else {
                if (Objects.equals(args[0], "heal") || Objects.equals(args[0], "h")) {
                    if (args.length == 1) {
//Player only typed '/heal' so let's heal them back!
                        player.setHealth(20);
                    } else {
//Player typed something more
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target == null) {
//Target is not online
                            player.sendMessage("Your target " + args[1] + " is not online!");
                        } else {
//Targets online
                            player.sendMessage("You've healed " + args[1]);
                            target.setHealth(20);
                        }
                    }
                }
                else if (Objects.equals(args[0],"reset-health") || Objects.equals(args[0], "r")) {
                    if (args.length == 1) {
                        player.sendMessage("Please Send the correct Things!");
                        return true;
                    }
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target == null) {
//Target is not online
                        player.sendMessage("Your target " + args[1] + " is not online!");
                    } else {
//Targets online
                        player.sendMessage("You've fixed " + args[1]);
                        target.setHealthScale(20);
                    }

                }
                else if (Objects.equals(args[0],"check")) {
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("blocks")) {
//Player only typed '/player check blocks' so let's check them.
//                    sender.sendMessage("You only typed " + alias + " " + args[1] + " so Lets check you!");
                            sender.sendMessage( ChatColor.GOLD + player.getDisplayName() + ChatColor.BLUE + " has only " + ChatColor.DARK_GREEN +
                                    plugin.getBlocksBroken().get(player.getPlayer())
                                    + " Blocks");
                        }
                    } else if (args.length == 1) {
                        player.sendMessage(
                                "You need to type: " + ChatColor.GOLD + "/player check blocks" + ChatColor.DARK_PURPLE +
                                        "\nOR"+ ChatColor.RESET + " you can do:" + ChatColor.GOLD + " /player check blocks " + ChatColor.UNDERLINE + "Player name."
                        );
                    }else {
//Player typed something more
                        Player target = Bukkit.getPlayerExact(args[2]);
                        if (target == null) {
//Target is not online
                            player.sendMessage("Your target " + args[2] + " is not online!");
                        } else {

                            if (args[1].equalsIgnoreCase("blocks")) {
//Targets online
                                sender.sendMessage( ChatColor.GOLD + args[2] + ChatColor.BLUE + " has only " + ChatColor.DARK_GREEN +
                                        plugin.getBlocksBroken().get(target.getPlayer())
                                        + " Blocks");
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> type = new ArrayList<>();
            type.add("r");
            type.add("reset-health");
            type.add("h");
            type.add("heal");
            type.add("check");
            return type;
        }

        if (args.length == 2){
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (Player player : players) {
                    playerNames.add(player.getName());
                }
            playerNames.add("blocks");

            return playerNames;
        }

        if (args.length == 3){
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++){
                playerNames.add(players[i].getName());
            }

            return playerNames;
        }
//        else if (args.length == 3){
//            List<String> arguments = new ArrayList<>();
//            arguments.add("");
//            arguments.add("");
//
//            return arguments;
//        }
        return null;
    }
}