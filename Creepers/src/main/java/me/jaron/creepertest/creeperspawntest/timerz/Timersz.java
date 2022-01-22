package me.jaron.creepertest.creeperspawntest.timerz;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

public class Timersz {

    public void startTimer(CommandSender sender) {
        TimerTask task = new TimerTask() {
            public void run() {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.isOnline()) {

                    }
                } else {
                    System.out.println(ChatColor.RED + "You need to be a player to do this command!");
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 4, 60 * 1000);
    }

}