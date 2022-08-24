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

import java.util.List;

public class TimeCheckerEvents implements Listener {

    private final TestPlugin plugin;
    public TimeCheckerEvents(TestPlugin plugin) {
        this.plugin = plugin;
    }


//    @EventHandler
//    public void onJoinEvent(PlayerJoinEvent event) {
//        Player player = event.getPlayer();
//        if (!plugin.getConfig().contains(player.getDisplayName())){
//            plugin.getConfig().createSection(player.getDisplayName());
//            List<String> words = plugin.getConfig().getStringList(player.getDisplayName());
//
////            words.add(System.currentTimeMillis());
//            words.add(evaluateTime(System.currentTimeMillis()));
//            plugin.getTimeOn().put(player, System.currentTimeMillis());
//            plugin.getConfig().set(String.valueOf(player), words);
//
//
//            player.sendMessage(firstTime(player.getFirstPlayed()));
//        }
//
//
//    }

//    @EventHandler
//    public void onLeaveEvent(PlayerQuitEvent event) {
//        Player player = event.getPlayer();
//        if (plugin.getConfig().contains(player.getDisplayName())){
//            plugin.getTimeOn().remove(player);
//        }
//    }

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


}
