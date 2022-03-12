package me.jaron.plugin.privateChests.listeners;

import me.jaron.plugin.MainClass;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestOpen implements Listener {

    @EventHandler
    public void onOpen(PlayerInteractEvent event) {
    if (!event.hasBlock()) return;
    if (event.getClickedBlock().getType() != Material.CHEST) return;
        if (!(event.getClickedBlock().getState() instanceof TileState)) return;

        TileState state = (TileState) event.getClickedBlock().getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(MainClass.getPlugin(MainClass.class),
        "private-chests");

        if (!container.has(key, PersistentDataType.STRING)) return;

        if (event.getPlayer().getUniqueId().toString().equalsIgnoreCase(
            container.get(key, PersistentDataType.STRING)))
            return;
            else {
                event.setCancelled(true);
                event.getPlayer().sendMessage("You cannot open this chest!");
            }

    }

}