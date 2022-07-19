package me.jaron.gamerica.plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.jaron.gamerica.plugin.afk.AFKManager;
import me.jaron.gamerica.plugin.afk.commands.AFKCommand;
import me.jaron.gamerica.plugin.afk.commands.IsAFKCommand;
import me.jaron.gamerica.plugin.afk.listeners.AFKListener;
import me.jaron.gamerica.plugin.afk.tasks.MovementChecker;
import me.jaron.gamerica.plugin.commands.Fly;
import me.jaron.gamerica.plugin.minigame.commands.*;
import me.jaron.gamerica.plugin.minigame.countdowns.GameEnd;
import me.jaron.gamerica.plugin.questnpcAndBodys.commands.QuestNPCCommand;
import me.jaron.gamerica.plugin.questnpcAndBodys.listener.QuestListeners;
import me.jaron.gamerica.plugin.questnpcAndBodys.managers.BodyManager;
import me.jaron.gamerica.plugin.questnpcAndBodys.managers.NPCManager;
import me.jaron.gamerica.plugin.questnpcAndBodys.managers.QuestManager;
import me.jaron.gamerica.plugin.questnpcAndBodys.menu.QuestMenu;
import me.jaron.gamerica.plugin.minigame.Gamestates;
import me.jaron.gamerica.plugin.minigame.listeners.*;
import me.jaron.gamerica.plugin.questnpcAndBodys.tasks.BodyRemover;
import me.jaron.gamerica.plugin.updater.Updater;
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
    private AFKManager afkManager;
    private GameEnd gameEnd;

//    MINIGAMES
    private Gamestates minigameGamestate;
    private Gamestates gamestate;
//    Make them all in the on enable so the whole plugin knows about it
    private World newWorld = null;
    private World world = null;
    public World mainWorldLobby = null;
    public World miniGameLobby = null;
    private int amountOfPlayer = 0;

    public ArrayList<Player> alive = new ArrayList<>();
    public ArrayList<Player> spectating = new ArrayList<>();
    public ArrayList<Player> vanished = new ArrayList<>();
    public ArrayList<Player> waiting = new ArrayList<>();

    private Gamestates mainGamestate;

//    WORLDS
    public Location loc = null;
//teleport command

    @Override
    public void onEnable() {
        // Plugin startup logic


        //Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

//        UPDATES
        if (getConfig().getBoolean("auto-update")) {
        Updater updater = new Updater(this, 631106, this.getFile(), Updater.UpdateType.DEFAULT, true);
        }else {
            System.out.println("You have auto-update set to: " + getConfig().getBoolean("auto-update") + ", Set to true if you want updates to automatically update.");
        }

        plugin = this;
        gameEnd = new GameEnd(this);
        npcManager = new NPCManager();
        questManager = new QuestManager(this);
        mainWorldLobby = Bukkit.getWorld(getConfig().getString("worlds.main"));
        miniGameLobby = Bukkit.getWorld(getConfig().getString("worlds.minigame"));
        newWorld =  Bukkit.getWorld(getConfig().getString("worlds.new"));
        amountOfPlayer = getConfig().getInt("worlds.numberofplayers");

        if(miniGameLobby == null){
            WorldCreator creator = new WorldCreator(getConfig().getString("worlds.minigame"));
            creator.environment(World.Environment.NORMAL);
            creator.generateStructures(true);
            miniGameLobby = creator.createWorld();
            miniGameLobby.setDifficulty(Difficulty.HARD);
            setGamestate(miniGameLobby,Gamestates.PREGAME);
            minigameGamestate.equals(Gamestates.PREGAME);
            this.loc = miniGameLobby.getSpawnLocation();
        }
        else if (getConfig().getString("worlds.minigame") != null) {
            setGamestate(miniGameLobby,Gamestates.PREGAME);
            minigameGamestate.equals(Gamestates.PREGAME);
            this.loc = miniGameLobby.getSpawnLocation();
        }
        if (getConfig().getBoolean("worlds.createnew") != false) {
            if (newWorld == null) {
                WorldCreator creator = new WorldCreator(getConfig().getString("worlds.new"));
                creator.environment(World.Environment.NORMAL);
                creator.generateStructures(true);
                newWorld = creator.createWorld();
                newWorld.setDifficulty(Difficulty.HARD);
                setGamestate(newWorld, Gamestates.SURVIVAL);
                this.loc = newWorld.getSpawnLocation();
            } else if (getConfig().getString("worlds.new") != null) {
                setGamestate(newWorld, Gamestates.SURVIVAL);
                this.loc = newWorld.getSpawnLocation();
            }
        }
        else {

            System.out.println("You do not have the worlds.createnew Enabled on config.yml");
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
        getCommand("end").setExecutor(new EndGameCommand(this));
        getCommand("lobby").setExecutor(new JoinGameCommand(this));
        getCommand("status").setExecutor(new GameStatusCommand(this));

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


        setGamestate(mainWorldLobby,Gamestates.LOBBY);
        setGamestate(miniGameLobby,Gamestates.PREGAME);

//        AFK BY KODY SIMPSON
        this.afkManager = new AFKManager();

        getCommand("isafk").setExecutor(new IsAFKCommand(this.afkManager, this));
        getCommand("afk").setExecutor(new AFKCommand(this.afkManager, this));

        getServer().getPluginManager().registerEvents(new AFKListener(this.afkManager), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new MovementChecker(this.afkManager), 0L, 600L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        gameEnd.end(null);
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
        for (String s : mainWorlds) {
            if (player.getWorld().equals(s)) {
                setGamestate(mainWorldLobby, Gamestates.LOBBY);
            } else {
                setGamestate(miniGameLobby, Gamestates.PREGAME);
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

    public Gamestates getGamestate(World world1) {
        Gamestates gamestates = gamestate;
//        get the world they say
//        take that world and get the state of the game
        if (world1.equals(miniGameLobby)) {
            gamestates = minigameGamestate;
        }else if (world1.equals(mainWorldLobby)){
            gamestates = mainGamestate;
        }else {
            gamestates = gamestate;
        }
        return gamestates;
    }


    public void setGamestate(World world, Gamestates gamestate) {
        //        set the world to the world they say
        this.world = world;
        //        set the gamestate to the world they say
        if (world.equals(miniGameLobby)) {
            this.minigameGamestate = gamestate;
        }else if (world.equals(mainWorldLobby)){
            this.mainGamestate = gamestate;
        }else {
            this.gamestate = gamestate;
        }

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
