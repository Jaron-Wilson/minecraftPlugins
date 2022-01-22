package me.jaron.plugin.itemEvents;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class MidasPickaxe implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore() == null) {
            System.out.println("not using MidasPickaxe");
        } else {
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains("§6Golden Touch:")) {
                Collection<ItemStack> drops = event.getBlock().getDrops();
                if (!(drops.size() == 0)) {
                    event.setDropItems(false);
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
                }
            }
        }
    }
}