package me.jaron.plugin.commands;


import me.jaron.plugin.MainClass;
import me.jaron.plugin.custom.mobManager.mobs.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class BossesCommands implements CommandExecutor {

    static MainClass plugin;
//    Try1 plugin = new Try1();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {
            Player player = (Player) sender;

//            if (!player.hasPermission("op")) {
            if (player.isOp()) {

                if (command.getName().equalsIgnoreCase("necromancer")) {
                    Necromancer.createNecromancer(player.getLocation());
                    return true;
                }
                if (command.getName().equalsIgnoreCase("revenant")) {
                    Reverent.createRevenant(player.getLocation());
                    return true;
                }
                if (command.getName().equalsIgnoreCase("zombieboss")) {
                    ZombieMob.createZombieMob(player.getLocation());
                    return true;
                }
                if (command.getName().equalsIgnoreCase("skeletonboss")) {
                    SkeletonMob.createSkeletonMob(player.getLocation());
                    return true;
                }
                if (command.getName().equalsIgnoreCase("necromancerapprentice")) {
                    NecromancersApprentice.createNecromancerApprentice(player.getLocation());
                    return true;
                }
                if (command.getName().equalsIgnoreCase("spawnBosses")) {
                    Necromancer.createNecromancer(player.getLocation());
                    Reverent.createRevenant(player.getLocation());
                    ZombieMob.createZombieMob(player.getLocation());
                    SkeletonMob.createSkeletonMob(player.getLocation());
                    NecromancersApprentice.createNecromancerApprentice(player.getLocation());
                    return true;
                }
                if (command.getName().equalsIgnoreCase("spawn")) {
                    CoolBeamBoss.createNecromancer(player.getLocation());
                    return true;
                }

            } else {
                sender.sendMessage(ChatColor.GOLD + "Can't use this command");
            }
        }
        return false;
    }
}