package me.jaron.plugin.itemEvents.bows;

import me.jaron.plugin.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class HomingBow implements Listener {

    Main plugin;

    public HomingBow(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        Entity arrow = event.getProjectile();
        Player player = (Player) event.getEntity();

        if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getLore() != null || player.getInventory().getItemInOffHand().getItemMeta() != null && player.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
//           System.out.println("not using Homing Bow");
//        } else {
            if (player.getInventory().getItemInMainHand().getItemMeta().getLore().contains("§6Homing Arrows:")) {

                new BukkitRunnable() {

                    public void run() {

                        if (arrow.isOnGround() || arrow.isDead()) {
                            cancel();
                        } else {
                            List<Entity> nearest = arrow.getNearbyEntities(5, 5, 5);

                            for (Entity target : nearest) {
                                if (player.hasLineOfSight(target) && target instanceof LivingEntity && !target.isDead() && target != player) {
                                    arrow.setVelocity(target.getLocation().toVector().subtract(arrow.getLocation().toVector()));
                                }
                            }
                        }
                    }
                }.runTaskTimer(plugin, 0L, 1L);
            }
        }
    }
}