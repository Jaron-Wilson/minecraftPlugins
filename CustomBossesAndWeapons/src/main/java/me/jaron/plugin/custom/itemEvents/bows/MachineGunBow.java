package me.jaron.plugin.custom.itemEvents.bows;

import me.jaron.plugin.MainClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class MachineGunBow implements Listener {

    MainClass plugin;

    public MachineGunBow(MainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore() == null) {
//                System.out.println("not using §9Machine Gun Bow");
            } else {
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§9Machine Gun Bow")) {
                    new BukkitRunnable() {

                        int timer = 25;

                        @Override
                        public void run() {
                            if (event.getPlayer().getInventory().containsAtLeast(new ItemStack(Material.ARROW), 1)) {
                                Location eye = event.getPlayer().getEyeLocation();
                                Location loc = eye.add(eye.getDirection().multiply(1.2));
                                if (loc.getWorld() == null) {
                                    event.getPlayer().sendMessage("YOUR WORLD IS NULL: " + loc.getWorld());
                                }
                                Arrow arrow = (Arrow) loc.getWorld().spawnEntity(loc, EntityType.ARROW);
                                arrow.setVelocity(loc.getDirection().normalize().multiply(2));
                                arrow.setShooter(event.getPlayer());
                                event.getPlayer().getInventory().removeItem(new ItemStack(Material.ARROW));
                            }
                            timer--;
                            if (timer == 0) {
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 4L);
                }
            }
        }
    }
}
