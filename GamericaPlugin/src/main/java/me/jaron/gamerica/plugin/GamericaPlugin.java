package me.jaron.gamerica.plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.jaron.gamerica.plugin.commands.Fly;
import me.jaron.gamerica.plugin.commands.QuestNPCCommand;
import me.jaron.gamerica.plugin.listener.DeathListener;
import me.jaron.gamerica.plugin.listener.QuestListeners;
import me.jaron.gamerica.plugin.listener.QuestListeners;
import me.jaron.gamerica.plugin.managers.BodyManager;
import me.jaron.gamerica.plugin.managers.NPCManager;
import me.jaron.gamerica.plugin.managers.QuestManager;
import me.jaron.gamerica.plugin.menu.QuestMenu;
import me.jaron.gamerica.plugin.managers.QuestManager;
import me.jaron.gamerica.plugin.menu.QuestMenu;
import me.jaron.gamerica.plugin.tasks.BodyRemover;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GamericaPlugin extends JavaPlugin   {

    private static GamericaPlugin plugin;

    private NPCManager npcManager;
    private QuestManager questManager;
    private BodyRemover bodyRemover;
    private BodyManager bodyManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        npcManager = new NPCManager();
        questManager = new QuestManager();

//        BODIES
        this.bodyManager = new BodyManager();
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);

        bodyRemover = new BodyRemover(this, bodyManager);
        bodyRemover.runTaskTimerAsynchronously(this, 20L, 20L);


        // Plugin startup logic
        getCommand("fly").setExecutor(new Fly(this));
        getCommand("questnpc").setExecutor(new QuestNPCCommand(npcManager));

        getServer().getPluginManager().registerEvents(new QuestListeners(), this);


        MenuManager.setup(getServer(), this);


        //Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //make a packet listener for entity interaction
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(this, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                //figure out if the entity interacted with is Jeff the quest NPC
                int entityID = packet.getIntegers().read(0);
                if (npcManager.getJeffID() == entityID){

                    //the packet will happen 4 times, twice for each hand and again twice for INTERACT AND INTERACT_AT
                    //lets choose one to listen for specifically
                    EnumWrappers.Hand hand = packet.getEnumEntityUseActions().read(0).getHand();
                    EnumWrappers.EntityUseAction action = packet.getEnumEntityUseActions().read(0).getAction();

                    if (hand == EnumWrappers.Hand.MAIN_HAND && action == EnumWrappers.EntityUseAction.INTERACT) {
                        //open the menu in a synchronous bukkit task
                        getServer().getScheduler().runTask(plugin, () -> {
                            //open the quest menu to display the available quests
                            try {
                                MenuManager.openMenu(QuestMenu.class, event.getPlayer());
//                                MenuManager.openMenu(QuestMenu.class, player);
                            } catch (MenuManagerException | MenuManagerNotSetupException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static GamericaPlugin getPlugin() {
        return plugin;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public NPCManager getNpcManager() {
        return npcManager;
    }

    public BodyRemover getBodyRemover() {
        return bodyRemover;
    }

    public BodyManager getBodyManager() {
        return bodyManager;
    }

}
