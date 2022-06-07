package me.jaron.gamerica.plugin.minigame.managers;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.commands.Vanish;
import me.jaron.gamerica.plugin.minigame.Gamestates;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerManager {

    private GamericaPlugin main;
    public PlayerManager(GamericaPlugin main) {
        this.main = main;
    }

    public void handle(Player player) {
        if (player.getWorld().equals(main.getConfig().getString("worlds.minigame"))) {
            if (main.getGamestate() == Gamestates.LOBBY) {
                main.alive.remove(player);
                main.spectating.remove(player);
                main.alive.add(player);
                player.setExp(0);
                player.setTotalExperience(0);
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setGameMode(GameMode.SURVIVAL);
                player.setAllowFlight(false);
                player.sendMessage(new ChatManager(main).minigameprefix + "Welcome to the MiniGame!");
                Bukkit.broadcastMessage(new ChatManager(main).minigameprefix + player.getDisplayName() + " has joined the minigame.");
            } else if (main.getGamestate() == Gamestates.INGAME || main.getGamestate() == Gamestates.ENDGAME || main.getGamestate() == Gamestates.PREGAME) {
                main.alive.remove(player);
                main.spectating.remove(player);
                main.spectating.add(player);
                new Vanish(main).toggleVanish(player);
                Bukkit.broadcastMessage(new ChatManager(main).minigameprefix + player.getDisplayName() + " has joined as a spectator.");
            }
        }
    }
}
