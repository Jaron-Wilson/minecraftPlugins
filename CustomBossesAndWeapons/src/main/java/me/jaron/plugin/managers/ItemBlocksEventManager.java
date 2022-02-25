package me.jaron.plugin.managers;

import me.jaron.plugin.customRecipies.ItemRecipeManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public class ItemBlocksEventManager implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (event.getClickedBlock() != null ||
                    event.getItem() != null ||
                    event.getItem().getItemMeta() != null ||
                    event.getItem().getItemMeta().getLore() != null ||
                    ItemRecipeManager.HardenedDiamondBlock.getItemMeta() != null ||
                    ItemRecipeManager.HardenedDiamondBlock.getItemMeta().getLore() != null ||
                    ItemRecipeManager.HardenedDiamondBlock.getItemMeta().getLore().get(0) != null ||
                    ItemRecipeManager.RefinedBlock.getItemMeta() != null ||
                    ItemRecipeManager.RefinedBlock.getItemMeta().getLore() != null ||
                    ItemRecipeManager.RefinedBlock.getItemMeta().getLore().get(0) != null
            ) {

                if (event.getHand() != null && event.getHand().equals(EquipmentSlot.HAND)){
                    if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null
                            && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Hardened Diamond Block")) {
                        event.setCancelled(true);
                    }

                    if (event.getHand() != null && event.getHand() == EquipmentSlot.HAND) {
                        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null
                                && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Refined Block")) {
                            event.setCancelled(true);
                        }
                    }
                    if (event.getHand() != null && event.getHand() == EquipmentSlot.OFF_HAND) {
                        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null) {
                            if (event.getItem().getItemMeta().getDisplayName().equals("§4Refined Block")) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
