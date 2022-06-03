package me.jaron.gamerica.plugin.listener;


import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.managers.QuestManager;
import me.jaron.gamerica.plugin.model.ItemQuest;
import me.jaron.gamerica.plugin.model.KillQuest;
import me.jaron.gamerica.plugin.model.Quest;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class QuestListeners implements Listener {

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {

        Player p = event.getEntity().getKiller();

        //see if the player is on a quest
        QuestManager qm = GamericaPlugin.getPlugin().getQuestManager();
        Quest q = qm.getQuest(p);
        if (q != null) {
            //see if the quest has a kill requirement
            if (q instanceof KillQuest killQuest) {
                //see if the entity killed is the same as the quest's target
                if (killQuest.getTarget().equals(event.getEntity().getType())) {
                    //increase the progress count
                    killQuest.setProgress(killQuest.getProgress() + 1);

                    //see if the progress count is equal to the required amount
                    if (killQuest.getProgress() == killQuest.getAmount()) {
                        //remove the quest
                        qm.completeQuest(p);
                    }else{
                        //tell them the progress they have made so far
                        p.sendMessage("You have killed " + killQuest.getProgress() + " " + killQuest.getTarget().name().toLowerCase() + "s.");
                        //tell them how many they need still
                        p.sendMessage("You need to kill " + (killQuest.getAmount() - killQuest.getProgress()) + " more " + killQuest.getTarget().name().toLowerCase() + "s.");
                    }
                }
            }
        }

    }

}
