package me.jaron.gamerica.plugin.afk;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
public class AFKManager {

//    CODE BY KODY SIMPSON
    private final long MOVEMENT_THRESHOLD = 60000L;

    private ChatManager chatManager;
    private GamericaPlugin main;

    private final HashMap<Player, Long> lastMovement = new HashMap<>();
    private final HashMap<Player, Boolean> previousData = new HashMap<>();

    public void playerJoined(Player player){
        lastMovement.put(player, System.currentTimeMillis());
    }

    public void playerLeft(Player player){
        lastMovement.remove(player);
    }

    public boolean toggleAFKStatus(Player player){

        if (isAFK(player)){
            previousData.put(player, false);
            lastMovement.put(player, System.currentTimeMillis());
            return false;
        }else{
            previousData.put(player, true);
            lastMovement.put(player, -1L);
            return true;
        }

    }

    public void playerMoved(Player player){

        lastMovement.put(player, System.currentTimeMillis());

        checkPlayerAFKStatus(player);

    }

    public boolean isAFK(Player player){

        if(lastMovement.containsKey(player)){
            if(lastMovement.get(player) == -1L){
                return true;
            }else{
                long timeElapsed = System.currentTimeMillis() - lastMovement.get(player);

                //see if they have moved since 1 minute
                //60000
                if(timeElapsed >= MOVEMENT_THRESHOLD){
                    return true;
                }
            }
        }else{
            lastMovement.put(player, System.currentTimeMillis());
        }

        return false;
    }

    public void checkAllPlayersAFKStatus(){

        for (Map.Entry<Player, Long> entry : lastMovement.entrySet()){
            checkPlayerAFKStatus(entry.getKey());
        }

    }

    public void checkPlayerAFKStatus(Player player){
        chatManager = new ChatManager(main);
        if (lastMovement.containsKey(player)){

            boolean nowAFK = isAFK(player);

            if (previousData.containsKey(player)){

                boolean wasAFK = previousData.get(player);

                if(wasAFK && !nowAFK){
                    player.sendMessage(chatManager.afkprefix + "You are no longer AFK");
                    previousData.put(player, false);

                    announceToOthers(player, false);

                }else if(!wasAFK && nowAFK){
                    player.sendMessage(chatManager.afkprefix + "You are now AFK!");
                    previousData.put(player, true);

                    announceToOthers(player, true);
                }

            }else{
                previousData.put(player, nowAFK);
            }

        }
    }

    public void announceToOthers(Player target, boolean isAFK){

        Bukkit.getServer().getOnlinePlayers().stream()
                .forEach(player -> {
                    chatManager = new ChatManager(main);
//                    if(!player.equals(target)){
                    if (isAFK){
                        player.sendMessage(chatManager.afkprefix + target.getDisplayName() + " is now AFK.");
                    }else{
                        player.sendMessage(chatManager.afkprefix + target.getDisplayName() + " is no longer AFK.");
                        //}
                    }
                });

    }
}
