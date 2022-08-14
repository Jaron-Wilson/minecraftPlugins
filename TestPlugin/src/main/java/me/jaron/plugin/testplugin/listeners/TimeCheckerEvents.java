package me.jaron.plugin.testplugin.listeners;

import me.jaron.plugin.testplugin.TestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TimeCheckerEvents implements Listener {

    private final TestPlugin plugin;
    public TimeCheckerEvents(TestPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getTimeOn().put(event.getPlayer(), System.currentTimeMillis());


        player.sendMessage(firstTime(player.getFirstPlayed()));

    }

    @EventHandler
    public void onLeaveEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getTimeOn().remove(event.getPlayer());
    }

    public String evaluateTime(Long joinNumber) {
    Long now = System.currentTimeMillis();
    Long date = now - joinNumber;

    long seconds = date / 1000 % 60;
    long minutes = date / (60 * 1000) % 60;
    long hours = date / (60 * 60 * 1000) % 24;
    long days = date / (24 * 60 * 60 * 1000);

    String fulldate = days + " d " + hours + " h " + minutes + " m " + seconds + " s";
    return fulldate;
}

    public String firstTime(Long joindate) {
        return evaluateTime(joindate);
    }
    public String justJoined(Long jointime) {
        if (jointime != null) {
            return evaluateTime(jointime);
        }else {
           System.out.println("Error " + jointime);
        }
        return "Error Server has reloaded when you were on, Please re-log to restart the counter.";
    }

//    @EventHandler
//    public void onCmd(PlayerCommandPreprocessEvent e) {
//        Long value = plugin.getTimeOn().get(e.getPlayer());
//        String simplified = justJoined(value);
//        e.getPlayer().sendMessage(simplified);
////        time.replace(e.getPlayer(), value + 1);
//    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            if (plugin.getBlocksBroken().containsKey(player)) {
                if (plugin.getBlocksBroken().get(player) == 3) {
                    player.sendMessage("You have joined " + ChatColor.BLUE + ChatColor.UNDERLINE +firstTime(player.getFirstPlayed()) + ChatColor.RESET + " ago.");

                    player.sendMessage("Your time on the server so far is: " + ChatColor.GOLD + ChatColor.UNDERLINE + justJoined(plugin.getTimeOn().get(event.getPlayer())));
                }
                plugin.getBlocksBroken().put(player, plugin.getBlocksBroken().get(player) + 1);
//                player.sendMessage("You have broken " + plugin.getBlocksBroken().get(player) + " blocks.");
                player.sendTitle("Blocks Broken", ChatColor.RED + String.valueOf(plugin.getBlocksBroken().get(player)), 5, 20, 6);
            } else {
                plugin.getBlocksBroken().put(player, 1);
                player.sendMessage("You have broken your first block. Nice.");

            }
        }
    }
}
