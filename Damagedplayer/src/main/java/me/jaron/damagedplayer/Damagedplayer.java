package me.jaron.damagedplayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Damagedplayer extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("start")) {
            if (!(sender instanceof Player)){
                return true;
            }
            Player player = (Player) sender;
            onPlayerAttackEnabled = true;
            player.sendMessage("Started");
        }
        return false;
    }

    private boolean onPlayerAttackEnabled = false;

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (onPlayerAttackEnabled) {
            Entity defender = event.getEntity();
            Entity offender = event.getDamager();

            if (!(defender instanceof Player)) return; //stops the code if the defender is not a player
            Player defendingPlayer = (Player) defender;//casts defender to a player

            if (!(offender instanceof Player)) return; //stops the code if the offender is not a player
            Player offendingPlayer = (Player) offender;//casts offender to a player

            //your code here, you can do whatever you like with the offender and defender
            defendingPlayer.getWorld().strikeLightning(defendingPlayer.getLocation());
            defendingPlayer.teleport(offendingPlayer.getLocation());
        }
    }
}
