package me.jaron.plugin.privateChests.listeners;

import me.jaron.plugin.MainClass;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ChestPlace  implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.CHEST)
        return;
        if (!(event.getBlock().getState() instanceof TileState))
        return;

        TileState state = (TileState) event.getBlock().getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(MainClass.getPlugin(MainClass.class),
        "private-chests");

        container.set(key, PersistentDataType.STRING, event.getPlayer().getUniqueId().toString());

        state.update();

        event.getPlayer().sendMessage("Chest locked!");
    }
   
}