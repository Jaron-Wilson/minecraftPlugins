package me.jaron.plugin.listeners;

import me.jaron.plugin.MainClass;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class LaunchPadFallDamageListener implements Listener {

    MainClass plugin;

    public LaunchPadFallDamageListener(MainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e){
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL) && plugin.getConfig().getBoolean("launchpads.disable-fall-damage")){
            if (plugin.jumping_players.contains(e.getEntity())){
                plugin.jumping_players.remove(e.getEntity());
                e.setCancelled(true);
            }
        }
    }

}
