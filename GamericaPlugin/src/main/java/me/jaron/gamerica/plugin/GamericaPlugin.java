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
import me.jaron.gamerica.plugin.listener.QuestListeners;
import me.jaron.gamerica.plugin.managers.BodyManager;
import me.jaron.gamerica.plugin.managers.NPCManager;
import me.jaron.gamerica.plugin.managers.QuestManager;
import me.jaron.gamerica.plugin.menu.QuestMenu;
import me.jaron.gamerica.plugin.minigame.Gamestates;
import me.jaron.gamerica.plugin.minigame.commands.JoinGameCommand;
import me.jaron.gamerica.plugin.minigame.commands.Start;
import me.jaron.gamerica.plugin.minigame.commands.Vanish;
import me.jaron.gamerica.plugin.minigame.listeners.*;
import me.jaron.gamerica.plugin.tasks.BodyRemover;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class GamericaPlugin extends JavaPlugin   {

    private static GamericaPlugin plugin;

    private NPCManager npcManager;
    private QuestManager questManager;
    private BodyRemover bodyRemover;
    private BodyManager bodyManager;

//    MINIGAMES
    private Gamestates gamestates;
//    Make them all in the on enable so the whole plugin knows about it
    private World world = null;
    public World mainWorldLobby = null;
    public World miniGameLobby = null;
    private int amountOfPlayer = 0;


    public Gamestates getGamestate() {
        return gamestates;
    }
    public void setGamestate(World world, Gamestates gamestate) {
        this.gamestates = gamestate;
        this.world = world;
    }

    public ArrayList<Player> alive = new ArrayList<>();
    public ArrayList<Player> spectating = new ArrayList<>();
    public ArrayList<Player> vanished = new ArrayList<>();
    public ArrayList<Player> waiting = new ArrayList<>();


//    WORLDS
    public Location loc = null;
//teleport command

    @Override
    public void onEnable() {
        // Plugin startup logic

        //Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();


        plugin = this;
        npcManager = new NPCManager();
        questManager = new QuestManager();
        mainWorldLobby = Bukkit.getWorld(getConfig().getString("worlds.main"));
        miniGameLobby = Bukkit.getWorld(getConfig().getString("worlds.minigame"));
        world =  Bukkit.getWorld(getConfig().getString("worlds.name"));
        amountOfPlayer = getConfig().getInt("worlds.numberofplayers");

        if(miniGameLobby == null){
            WorldCreator creator = new WorldCreator(getConfig().getString("worlds.name"));
            creator.environment(World.Environment.NORMAL);
            creator.generateStructures(true);
            world = creator.createWorld();
            world.setDifficulty(Difficulty.HARD);
            miniGameLobby = world;
            setGamestate(world,Gamestates.PREGAME);
            this.loc = miniGameLobby.getSpawnLocation();
        }else if (getConfig().getString("worlds.minigame") != null) {
            setGamestate(miniGameLobby,Gamestates.PREGAME);
            this.loc = miniGameLobby.getSpawnLocation();
        }


//        BODIES
//        this.bodyManager = new BodyManager();
//        getServer().getPluginManager().registerEvents(new DeathListener(this), this);

//        bodyRemover = new BodyRemover(this, bodyManager);
//        bodyRemover.runTaskTimerAsynchronously(this, 20L, 20L);


        // Plugin startup logic
        getCommand("fly").setExecutor(new Fly(this));
        getCommand("questnpc").setExecutor(new QuestNPCCommand(npcManager));
        getCommand("join").setExecutor(new JoinGameCommand(this));
        getCommand("lobby").setExecutor(new JoinGameCommand(this));

        getServer().getPluginManager().registerEvents(new QuestListeners(), this);
        getServer().getPluginManager().registerEvents(new CheckGameEnd(this), this);


        MenuManager.setup(getServer(), this);


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

//        MINIGAMES
        registerCommands();
        registerEvents();

//        if (getMinigameWorld())

        setGamestate(mainWorldLobby,Gamestates.LOBBY);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public World getWorld(final String name) {
        World world = Bukkit.getWorld(name);
        return world == null ? Bukkit.createWorld(new WorldCreator(name)) : world;
    }

    public boolean getMinigameWorld(final String name, final String mininame) {
        World world = Bukkit.getWorld(name);
        World minigame = Bukkit.getWorld(mininame);
        if (world == minigame){
            return true;
        }else {
            return false;
        }
    }

    public boolean getMainWorld(final Player player, final String mainWorld, final String netherWorld, final String endWorld) {
        World world = Bukkit.getWorld(mainWorld);
        World nether = Bukkit.getWorld(netherWorld);
        World end = Bukkit.getWorld(endWorld);

        List<String> mainWorlds = null;
        mainWorlds.add(String.valueOf(world));
        mainWorlds.add(String.valueOf(nether));
        mainWorlds.add(String.valueOf(end));


        int worlds = mainWorlds.size();
        for (int i = 0; i < worlds; i++) {
            if (player.getWorld().equals(mainWorlds.get(i))) {
                setGamestate(mainWorldLobby,Gamestates.LOBBY);
            }else {
                setGamestate(this.world,Gamestates.PREGAME);
            }

        }

        return true;
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

//    MINIGAMES
    private void registerCommands() {
    getCommand("vanish").setExecutor(new Vanish(this));
    getCommand("start").setExecutor(new Start(this));
}

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Join(this), this);
        pm.registerEvents(new Build(this), this);
        pm.registerEvents(new Damage(this), this);
        pm.registerEvents(new Food(this), this);
    }

    public int getPlayerAmount() {
        return amountOfPlayer;
    }
}
