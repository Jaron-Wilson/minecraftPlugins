package me.jaron.creepertest.creeperspawntest.npcnoncitizenz;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!NotPlayerControlled.instance().getNPCs().isEmpty()) {
            NotPlayerControlled.instance().addJoinPacket(event.getPlayer());

        }
    }
}
