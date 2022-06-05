package me.jaron.gamerica.plugin.menu;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.managers.QuestManager;
import me.jaron.gamerica.plugin.model.ItemQuest;
import me.jaron.gamerica.plugin.model.KillQuest;
import me.jaron.gamerica.plugin.model.Quest;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class QuestMenu extends Menu {

    private final QuestManager questManager;

    public QuestMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.questManager = GamericaPlugin.getPlugin().getQuestManager();
    }

    @Override
    public String getMenuName() {
        return "My Quests";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = playerMenuUtility.getOwner();

        //see what they clicked
        if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD || e.getCurrentItem().getType() == Material.DEBUG_STICK){
            //get the quest
            String questName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

            for (Quest quest : questManager.getAvailableQuests()) {
                if (quest.getName().equalsIgnoreCase(questName)) {

                    //see if the player already has a quest
                    Quest playersQuest = questManager.getQuest(p);
                    if (playersQuest != null) {
                        //see if the quest is the same one they clicked on
                        if (playersQuest.getName().equalsIgnoreCase(questName)) {
                            //tell them they already have the quest
                            p.sendMessage(ColorTranslator.translateColorCodes("&cYou already have this quest!"));
                        }else{
                            //tell them they are already on a quest
                            p.sendMessage(ColorTranslator.translateColorCodes("&cYou are already on a different quest!"));
                        }
                    }else{
                        //give the quest to the player
                        questManager.giveQuest(p, quest);
                        //tell them they have the quest and tell them what to do
                        p.sendMessage(ColorTranslator.translateColorCodes("&aYou have been given the quest &e\"" + quest.getName() + "\"&a!"));
                        p.sendMessage(ColorTranslator.translateColorCodes("&aTo complete this quest, you must &e" + quest.getDescription() + "&a!"));
                    }

                    p.closeInventory();

                    break;
                }
            }
        }

    }

    @Override
    public void setMenuItems() {

        //Get all of the quests
        questManager.getAvailableQuests().forEach(quest -> {
            ItemStack item;
            if (quest instanceof KillQuest killQuest){
                Player p = playerMenuUtility.getOwner();

                //see if this is a quest he is on
                boolean isOnQuest = false;
                Quest playersQuest = questManager.getQuest(p);
                if (playersQuest != null) {
                    //see if the quest is the same one he is on
                    if (playersQuest.getName().equalsIgnoreCase(quest.getName())) {
                        isOnQuest = true;
                    }
                }

                item = makeItem(Material.DIAMOND_SWORD,
                        ColorTranslator.translateColorCodes("&6&l" + killQuest.getName()),
                        ColorTranslator.translateColorCodes("&5" + killQuest.getDescription()),
                        " ",
                        "&7Reward: &a$" + killQuest.getReward(),
                        " ",
                        (isOnQuest ? "&cYou are on this quest!" : "&aClick to accept!"));

                inventory.addItem(item);
            }

            if (quest instanceof ItemQuest itemQuest){
                Player p = playerMenuUtility.getOwner();

                //see if this is a quest he is on
                boolean isOnQuest = false;
                Quest playersQuest = questManager.getQuest(p);
                if (playersQuest != null) {
                    //see if the quest is the same one he is on
                    if (playersQuest.getName().equalsIgnoreCase(quest.getName())) {
                        isOnQuest = true;
                    }
                }

                item = makeItem(Material.DEBUG_STICK,
                        ColorTranslator.translateColorCodes("&6&l" + itemQuest.getName()),
                        ColorTranslator.translateColorCodes("&5" + itemQuest.getDescription()),
                        " ",
                        "&7Reward: &a$" + itemQuest.getReward(),
                        " ",
                        (isOnQuest ? "&cYou are on this quest!" : "&aClick to accept!"));

                inventory.addItem(item);
            }
        });

    }
}
