package me.jaron.gamerica.plugin.minigame.listeners;

import me.jaron.gamerica.plugin.minigame.Gamestates;
import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Build implements Listener {

    private GamericaPlugin main;
    public Build(GamericaPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
            if (p.getWorld().equals(main.miniGameLobby)){
    //        if (main.getMinigameWorld(p.getWorld().toString(), main.getConfig().getString("worlds.minigame"))) {
            if (main.getGamestate() == Gamestates.PREGAME) {
                event.setCancelled(true);
                p.sendMessage(new ChatManager(main).prefix + "You cannot build before the game!");
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (p.getWorld().equals(main.miniGameLobby)){
//        if (main.getMinigameWorld(p.getWorld().toString(), main.getConfig().getString("worlds.minigame"))) {
            if (main.getGamestate() == Gamestates.PREGAME) {
                event.setCancelled(true);
                p.sendMessage(new ChatManager(main).prefix + "You cannot build before the game!");
            }
        }
    }
}
