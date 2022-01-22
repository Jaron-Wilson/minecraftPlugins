package me.jaron.creepertest.creeperspawntest.listenerz;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Timer;
import java.util.TimerTask;

public class Creeperspawnz implements Listener{

    private static Creeperspawnz instance = new Creeperspawnz();

    public static Creeperspawnz getInstance() {
        return instance;
    }

    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent event, Player sender) {

        if (event.getEntityType() == EntityType.CREEPER) {

            Player player = (Player) sender;
            Creeper creeper = (Creeper) event.getEntity();
            Zombie zombie =(Zombie) event.getEntity();

            zombie.attack(player);

            zombie.setCanPickupItems(true);
            creeper.setExplosionRadius(3);
        }
    }

    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            int px = p.getLocation().getBlockX();
            int py = p.getLocation().getBlockY();
            int pz = p.getLocation().getBlockZ();

            Entity ent = Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world")
                    , px, py, pz), EntityType.COW);
        }
    }
    public void startTimer(CommandSender sender){
        TimerTask task = new TimerTask() {
            public void run() {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.isOnline())
                    {
                       //code here
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
