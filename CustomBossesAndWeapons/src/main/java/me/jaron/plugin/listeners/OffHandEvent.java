package me.jaron.plugin.listeners;

import me.jaron.plugin.MainClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.world.entity.player.PlayerInventory;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class OffHandEvent implements Listener {
    MainClass plugin;

    public OffHandEvent(MainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        if (plugin.getConfig().getBoolean("offhand.enable")) {
            if (plugin.getConfig().getBoolean("offhand.offhand-message.enable")) {
                for (String msg : plugin.getConfig().getStringList("offhand.offhand-message.message")) {
                    event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&',
                            msg)));
                }
            }
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onPlayerInteract(InventoryClickEvent event){
        if (plugin.getConfig().getBoolean("offhand-enable")) {

            if (event.getSlot() == PlayerInventory.d) {
                if (event.getClickedInventory() != null) {
                    event.getClickedInventory().clear(PlayerInventory.d);
                    event.setCancelled(true);
                }
            }
        }
    }
}
