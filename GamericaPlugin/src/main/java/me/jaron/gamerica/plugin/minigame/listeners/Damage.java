package me.jaron.gamerica.plugin.minigame.listeners;

import me.jaron.gamerica.plugin.minigame.Gamestates;
import me.jaron.gamerica.plugin.GamericaPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Damage implements Listener {

    private GamericaPlugin main;
    public Damage(GamericaPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            Player p = (Player) event.getEntity();
//            if (main.getMinigameWorld(p.getWorld().toString(), main.getConfig().getString("worlds.minigame"))) {
            if (p.getWorld().equals(main.miniGameLobby)) {
                if (main.getGamestate() == Gamestates.PREGAME) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
