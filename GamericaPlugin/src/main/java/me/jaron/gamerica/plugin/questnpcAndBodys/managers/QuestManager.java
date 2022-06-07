package me.jaron.gamerica.plugin.questnpcAndBodys.managers;

import me.jaron.gamerica.plugin.GamericaPlugin;
import me.jaron.gamerica.plugin.minigame.managers.ChatManager;
import me.jaron.gamerica.plugin.questnpcAndBodys.model.ItemQuest;
import me.jaron.gamerica.plugin.questnpcAndBodys.model.KillQuest;
import me.jaron.gamerica.plugin.questnpcAndBodys.model.Quest;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//responsible for managing(and storing) all quests
public class QuestManager {

    private final HashMap<Player, Quest> quests;
    private ChatManager chatManager;
    private GamericaPlugin main;

    public QuestManager(GamericaPlugin main) {
        this.main = main;
        this.quests = new HashMap<>();
    }

    //Load the quests from config.yml
    public List<Quest> getAvailableQuests() {
        chatManager = new ChatManager(main);
        List<Quest> availableQuests = new ArrayList<>();


        GamericaPlugin.getPlugin().getConfig().getConfigurationSection("quests.kill").getKeys(false).forEach(questName -> {
            //create a Quest object from each of these

            String name = GamericaPlugin.getPlugin().getConfig().getString("quests.kill." + questName + ".name");
            String description = GamericaPlugin.getPlugin().getConfig().getString("quests.kill." + questName + ".description");
            double reward = GamericaPlugin.getPlugin().getConfig().getDouble("quests.kill." + questName + ".reward");
            String entityType = GamericaPlugin.getPlugin().getConfig().getString("quests.kill." + questName + ".target.type");
            int count = GamericaPlugin.getPlugin().getConfig().getInt("quests.kill." + questName + ".target.count");

            EntityType entityType1 = EntityType.valueOf(entityType);

            Quest quest = new KillQuest(name, description, reward, entityType1, count);
            availableQuests.add(quest);
            System.out.println(quest);
        });
        GamericaPlugin.getPlugin().getConfig().getConfigurationSection("quests.item").getKeys(false).forEach(questName1 -> {
            //create a Quest object from each of these

            String name = GamericaPlugin.getPlugin().getConfig().getString("quests.item." + questName1 + ".name");
            String description = GamericaPlugin.getPlugin().getConfig().getString("quests.item." + questName1 + ".description");
            double reward = GamericaPlugin.getPlugin().getConfig().getDouble("quests.item." + questName1 + ".reward");
            String material = GamericaPlugin.getPlugin().getConfig().getString("quests.item." + questName1 + ".target.type");
            int count = GamericaPlugin.getPlugin().getConfig().getInt("quests.item." + questName1 + ".target.count");

            Material material1 = Material.valueOf(material);

            Quest quest1 = new ItemQuest(name, description, reward, material1, count);
            availableQuests.add(quest1);
            System.out.println(quest1);
        });

        return availableQuests;
    }

    //Get the Quest that a Player is on, null if they aren't on one
    public Quest getQuest(Player player) {
        return this.quests.get(player);
    }

    public void giveQuest(Player p, Quest q){
        q.setWhenStarted(System.currentTimeMillis());
        this.quests.put(p, q);
    }

    public void completeQuest(Player p){

        Quest quest = this.quests.get(p);

        //tell them they completed the quest
        long timeTook = System.currentTimeMillis() - quest.getWhenStarted();
        //convert to a string with minutes and seconds
        String timeTookString = String.format("%02d:%02d", timeTook / 60000, (timeTook % 60000) / 1000);
        p.sendMessage(ColorTranslator.translateColorCodes(chatManager.questprefix + quest.getName() + "\"&a in " + timeTookString));

        //give them the reward
        p.sendMessage(chatManager.questprefix + "You received " + this.quests.get(p).getReward() + " coins!");

        //remove the quest from the HashMap
        this.quests.remove(p);
    }

}
