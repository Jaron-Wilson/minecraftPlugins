package me.jaron.plugin.itemEvents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class InfiniteBuckets implements Listener {

    @EventHandler
    public void onBucketDrain(PlayerBucketEmptyEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore() == null) {
            System.out.println("not using Water or Lava");
        } else {
            int x = event.getBlockClicked().getX() + event.getBlockFace().getModX();
            int y = event.getBlockClicked().getY() + event.getBlockFace().getModY();
            int z = event.getBlockClicked().getZ() + event.getBlockFace().getModZ();
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§5Infinite Water Bucket")) {
                event.getPlayer().getWorld().getBlockAt(x, y, z).setType(Material.WATER);
                event.setCancelled(true);
            }
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§5Infinite Lava Bucket")) {
                event.getPlayer().getWorld().getBlockAt(x, y, z).setType(Material.LAVA);
                event.setCancelled(true);
            }
        }
    }
}
