package me.jaron.plugin.itemEvents.GrapplingHookFiles;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class GrapplingHookCooldown {

    public static HashMap<UUID, Double> cooldowns;

    public static void setupCooldown() {
        cooldowns = new HashMap<>();
    }

    public static void setCooldown(Player player, int seconds) {
        double delay = (double) (System.currentTimeMillis() + (seconds * 1000));
        cooldowns.put(player.getUniqueId(), delay);
    }

    public static boolean checkCooldown(Player player) {
        if (!cooldowns.containsKey(player.getUniqueId()) || cooldowns.get(player.getUniqueId()) <= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
