package me.jaron.plugin.listeners;

import me.jaron.plugin.MainClass;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.world.entity.player.PlayerInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;

import static org.bukkit.Material.LIGHT_GRAY_STAINED_GLASS_PANE;

public class OffHandEvent implements Listener {
    MainClass plugin;

    public OffHandEvent(MainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        if (plugin.getConfig().getBoolean("offhand-message.enable")) {
            for (String msg : plugin.getConfig().getStringList("offhand-message.message")) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        msg));
            }
        }
        event.setCancelled(true);

    }


    @EventHandler
    public void onPlayerInteract(InventoryClickEvent event){
        if (event.getSlot() == PlayerInventory.d){
            if (event.getClickedInventory() != null) {
                event.getClickedInventory().clear(PlayerInventory.d);
                event.setCancelled(true);
            }
        }
    }
}
