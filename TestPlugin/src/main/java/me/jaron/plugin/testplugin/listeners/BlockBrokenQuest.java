package me.jaron.plugin.testplugin.listeners;

import me.jaron.plugin.testplugin.TestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBrokenQuest implements Listener {


    private final TestPlugin plugin;
    public BlockBrokenQuest(TestPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            if (plugin.getBlocksBroken().containsKey(player)) {
//                Zero Scale 0=1
                if (plugin.getBlocksBroken().get(player) == 4) {
                player.sendMessage(ChatColor.RED +  "Be " + ChatColor.GOLD +"Warned " + ChatColor.RED +"if you get to high" + ChatColor.GOLD + "...");
                }
                if (plugin.getBlocksBroken().get(player) >= 9) {
                    player.setHealthScale(15);
                }
                if (plugin.getBlocksBroken().get(player) >= 19) {
                    player.setHealthScale(10);
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
