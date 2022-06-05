package me.jaron.gamerica.plugin.minigame.countdowns;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.ChatColor;

public class GameEnd {

    private GamericaPlugin main;

    private ChatManager chatManager = null;
    public GameEnd(GamericaPlugin main) {
        this.main = main;
    }

    public void check(){
        chatManager = new ChatManager(main);
        int getPlayers = main.spectating.size();
        if (main.alive.size() <= 1){
            for (int i = 0; i < getPlayers; i++) {
                main.spectating.get(i).sendMessage(chatManager.prefix + ChatColor.BOLD + ChatColor.ITALIC + ChatColor.DARK_PURPLE + "\n\n-----------Game is done!-----------\n\n");
                main.spectating.get(i).sendMessage(chatManager.prefix + ChatColor.BLUE + "The Player who won is: " + ChatColor.GOLD + main.alive.get(i).getDisplayName() + "!");
                main.spectating.get(i).teleport(main.mainWorldLobby.getSpawnLocation());
                main.spectating.get(i).sendMessage(chatManager.prefix + "You can type in " + ChatColor.BOLD + ChatColor.GOLD + "/lobby" + ChatColor.RESET +
                        " If you did not get teleported");
                main.spectating.remove(i);
                if (main.spectating.size() <= 0){
                    break;
                }
            }
        }

    }


}
