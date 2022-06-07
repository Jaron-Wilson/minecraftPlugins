package me.jaron.gamerica.plugin.minigame.listeners;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.commands.Vanish;
import me.jaron.gamerica.plugin.minigame.countdowns.GameEnd;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.*;
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
                p.teleport(main.miniGameLobby.getSpawnLocation());
                event.setDeathMessage(chatManager.minigameprefix + p.getName() + " You have died and are now spectator!");
//                Set them to spectator
                new Vanish(main).toggleVanish(p);
                p.sendMessage(chatManager.minigameprefix + " If you want to go to lobby then type " +
                        ChatColor.GOLD + ChatColor.BOLD + "/lobby");
                p.sendMessage(chatManager.minigameprefix + " or you can just stay! And watch");
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
                p.sendMessage(chatManager.minigameprefix + " You lost your stuff at: " + ChatColor.BOLD + ChatColor.RED +
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
