package me.jaron.plugin.Commands;


import me.jaron.plugin.Try1;
import me.jaron.plugin.itemmanager.mobs.*;
import me.jaron.plugin.listeners.Menu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commands implements CommandExecutor {

    static Try1 plugin;
//    Try1 plugin = new Try1();
    Menu menu = new Menu(plugin);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {
            Player player = (Player) sender;

//            if (player.hasPermission("op")) {
            if (player.isOp()) {

                if (command.getName().equalsIgnoreCase("necromancer")) {
                    Necromancer.createNecromancer(player.getLocation());
                }
                if (command.getName().equalsIgnoreCase("revenant")) {
                    Revenant.createRevenant(player.getLocation());
                }
                if (command.getName().equalsIgnoreCase("zombieboss")) {
                    ZombieMob.createZombieMob(player.getLocation());
                }
                if (command.getName().equalsIgnoreCase("skeletonboss")) {
                    SkeletonMob.createSkeletonMob(player.getLocation());
                }
                if (command.getName().equalsIgnoreCase("necromancerapprentice")) {
                    NecromancersApprentice.createNecromancerApprentice(player.getLocation());
                }
                if (command.getName().equalsIgnoreCase("spawnBosses")) {
                    Necromancer.createNecromancer(player.getLocation());
                    Revenant.createRevenant(player.getLocation());
                    ZombieMob.createZombieMob(player.getLocation());
                    SkeletonMob.createSkeletonMob(player.getLocation());
                    NecromancersApprentice.createNecromancerApprentice(player.getLocation());
                }

            }else {
                    sender.sendMessage(ChatColor.GOLD + "Can't use this command");
                }
            }
            return false;
    }
}