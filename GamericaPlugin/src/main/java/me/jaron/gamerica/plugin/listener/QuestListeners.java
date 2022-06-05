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
                    } else {
                        //tell them the progress they have made so far
                        p.sendMessage("You have killed " + killQuest.getProgress() + " " + killQuest.getTarget().name().toLowerCase() + "s.");
                        //tell them how many they need still
                        p.sendMessage("You need to kill " + (killQuest.getAmount() - killQuest.getProgress()) + " more " + killQuest.getTarget().name().toLowerCase() + "s.");
                    }
                }
            }
        }

    }

    @EventHandler
    public void onItemCollected(EntityPickupItemEvent event) {
        Player p = (Player) event.getEntity();

        //see if the player is on a quest
        QuestManager qm = GamericaPlugin.getPlugin().getQuestManager();
        Quest q = qm.getQuest(p);
        if (q != null) {
            //see if the quest has a kill requirement
            if (q instanceof ItemQuest itemQuest) {
                //see if the entity killed is the same as the quest's target
                if (event.getItem().getType().toString().equalsIgnoreCase(itemQuest.getItemType().toString())) {
                    //increase the progress count
                    itemQuest.setProgress(itemQuest.getProgress() + 1);

                    //see if the progress count is equal to the required amount
                    if (itemQuest.getProgress() != itemQuest.getItemAmount()) {
                        //tell them the progress they have made so far
                        p.sendMessage("You have got " + itemQuest.getProgress() + " " + itemQuest.getItemType().name().toLowerCase() + "s.");
                        //tell them how many they need still
                        p.sendMessage("You need to get " + (itemQuest.getItemAmount() - itemQuest.getProgress()) + " more " + itemQuest.getItemType().name().toLowerCase() + "s.");

                    } else {
                        //remove the quest
                        qm.completeQuest(p);
                       }
                }
            }
        }

        //    @EventHandler
//    public void onItemCollected(EntityPickupItemEvent event) {
//
//        Player p = (Player) event.getEntity();
//
//        //see if the player is on a quest
//        QuestManager qm = GamericaPlugin.getPlugin().getQuestManager();
//        if (p instanceof Player) {
//            Quest q = qm.getQuest(p);
//            if (q != null) {
//                //see if the quest has a kill requirement
//                if (q instanceof ItemQuest itemQuest) {
//                    //see if the entity killed is the same as the quest's target
//                    if (event.getItem().equals(itemQuest.getItemType())) {
//                        qm.completeQuest(p);
//                    } else {
//                        //tell them the progress they have made so far
//                        //tell them how many they need still
//
//                        p.sendMessage("You need to get " + (itemQuest.getItemAmount()) + " and the item is " + itemQuest.getItemType().toString().toLowerCase() + ".");
//                    }
//                }
//            }
//        } else {
//            return;
//        }
//    }
    }
}
