package me.jaron.gamerica.plugin.minigame.listeners;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.countdowns.GameEnd;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CheckGameEnd implements Listener {

    private GamericaPlugin main;
    private ChatManager chatManager = null;
    private GameEnd gameEnd = null;

    public CheckGameEnd(GamericaPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        gameEnd = new GameEnd(main);
        chatManager = new ChatManager(main);
        Player p = event.getEntity().getPlayer();
        if (p.getWorld() != null) {
            if (p.getWorld() == (main.miniGameLobby)) {
                Block playerDeathLocation = p.getWorld().getBlockAt(p.getLocation());
                p.teleport(playerDeathLocation.getLocation());

                event.setDeathMessage(chatManager.prefix + p.getName() + "You have died and are now spectator!");
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(chatManager.prefix + " If you want to go to lobby then type " +
                        ChatColor.GOLD + ChatColor.BOLD + "/lobby");
                p.sendMessage(chatManager.prefix + " or you can just stay! And watch");
//                Hide from tab list
                p.hidePlayer(main, p);
                if (main.waiting.contains(p)){
                    main.waiting.remove(p);
                    main.spectating.add(p);
                    gameEnd.check();
                }else {
                    main.spectating.add(p);
                    gameEnd.check();
                }



            } else if (p.getWorld() == main.mainWorldLobby) {
                p.sendMessage(chatManager.prefix + " You lost your stuff at: " + ChatColor.BOLD + ChatColor.RED +
                        p.getLocation().getBlockX() + ", " +
                        p.getLocation().getBlockY() + ", " +
                        p.getLocation().getBlockZ() + ", " +
//                        NEW line = \n
                        p.getLocation().getWorld()  + ",\n " +
                        ChatColor.RESET +" You have " + ChatColor.BLUE + "5 " + ChatColor.RESET + "minutes till it despawns");
            }
        }else {
            System.out.println(event.getEntity().getWorld());
        }
    }


}
