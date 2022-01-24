package me.jaron.plugin.itemEvents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.permissions.Permission;

public class InfiniteBuckets implements Listener {

    @EventHandler
    public void onBucketDrain(PlayerBucketEmptyEvent event) {

        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore() == null) {
            System.out.println("Debug info: InfiniteBuckets Class, DO NOT WORRY This Means nothing.");
        } else {
            int x1 = event.getBlockClicked().getX() + event.getBlockFace().getModX();
            int y1 = event.getBlockClicked().getY() + event.getBlockFace().getModY();
            int z1 = event.getBlockClicked().getZ() + event.getBlockFace().getModZ();

            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§5Infinite Water Bucket")) {
                event.getPlayer().getWorld().getBlockAt(x1, y1, z1).setType(Material.WATER);
                event.setCancelled(true);
            }
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§5Infinite Lava Bucket")) {
                event.getPlayer().getWorld().getBlockAt(x1, y1, z1).setType(Material.LAVA);
                event.setCancelled(true);
            }
        }
        /*Off hand not working*/
//
//        if (event.getPlayer().getInventory().getItemInOffHand().getItemMeta() == null || event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getLore() == null) {
//            System.out.println();
//        } else {
//            int x = event.getBlockClicked().getX() + event.getBlockFace().getModX();
//            int y = event.getBlockClicked().getY() + event.getBlockFace().getModY();
//            int z = event.getBlockClicked().getZ() + event.getBlockFace().getModZ();
//            if (event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals("§5Infinite Water Bucket")) {
//                event.getPlayer().getWorld().getBlockAt(x, y, z).setType(Material.WATER);
//                event.setCancelled(true);
//            }
//            if (event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals("§5Infinite Lava Bucket")) {
//                event.getPlayer().getWorld().getBlockAt(x, y, z).setType(Material.LAVA);
//                event.setCancelled(true);
//            }
//        }

    }
}
