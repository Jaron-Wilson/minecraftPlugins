package me.jaron.plugin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.jaron.plugin.allnpcs.npc.*;
import me.jaron.plugin.allnpcs.npcs.FPCommand;
import me.jaron.plugin.allnpcs.npcs.NPCManager;
import me.jaron.plugin.commands.BossesCommands;
import me.jaron.plugin.commands.ItemCommands;
import me.jaron.plugin.commands.VanishCommand;
import me.jaron.plugin.custom.itemEvents.bows.*;
import me.jaron.plugin.custom.itemEvents.clickWeapons.*;
import me.jaron.plugin.custom.mobManager.mobs.*;
//import me.jaron.plugin.custom.mobManager.wolf.SpawnEntity;
import me.jaron.plugin.custom.recipies.ItemRecipeManager;
import me.jaron.plugin.guis.CustomInventory;
import me.jaron.plugin.guis.GUICommand;
import me.jaron.plugin.guis.GUIMoveItem;
import me.jaron.plugin.guis.ban.commands.BanGUICommand;
import me.jaron.plugin.guis.ban.listener.BanInventoryListener;
import me.jaron.plugin.guis.vaults.commands.OpenCommand;
import me.jaron.plugin.guis.vaults.listeners.Listeners;
import me.jaron.plugin.custom.itemEvents.GrapplingHookFiles.GrapplingHook;
import me.jaron.plugin.custom.itemEvents.GrapplingHookFiles.GrapplingHookCooldown;
import me.jaron.plugin.custom.itemEvents.InfiniteBuckets;
import me.jaron.plugin.custom.itemEvents.armor.AutoShootChestplate;
import me.jaron.plugin.custom.itemEvents.armor.BomberElytra;
import me.jaron.plugin.custom.itemEvents.axesAndSwords.DamageMultiplierSword;
import me.jaron.plugin.custom.itemEvents.axesAndSwords.LightningAxe;
import me.jaron.plugin.custom.itemEvents.axesAndSwords.TeleportSword;
import me.jaron.plugin.custom.itemEvents.axesAndSwords.ThrowingAxe;
import me.jaron.plugin.custom.itemEvents.eggs.ZombieKnightSpawnEgg;
import me.jaron.plugin.custom.itemEvents.pickaxes.AutoSmeltPickaxe;
import me.jaron.plugin.custom.itemEvents.pickaxes.ChunkMinerPickaxe;
import me.jaron.plugin.custom.itemEvents.pickaxes.MidasPickaxe;
import me.jaron.plugin.custom.itemEvents.pickaxes.MultibreakPickaxe;
import me.jaron.plugin.listeners.LaunchPadFallDamageListener;
import me.jaron.plugin.listeners.JoinEvent;
import me.jaron.plugin.listeners.OffHandEvent;
import me.jaron.plugin.listeners.LaunchPadPlayerMoveListener;
import me.jaron.plugin.managers.ItemBlocksEventManager;
import me.jaron.plugin.managers.ItemManager;
import me.jaron.plugin.minigames.tag.listeners.TaggedEvent;
import me.jaron.plugin.minigames.tag.models.TagGame;
import me.jaron.plugin.minigames.tag.tagcommand.TagCommand;
import me.jaron.plugin.privateChests.listeners.ChestOpen;
import me.jaron.plugin.privateChests.listeners.ChestPlace;
import me.jaron.plugin.guis.tabManagers.TabManager;
import me.jaron.plugin.custom.mobManager.money.MobKillEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public final class MainClass extends JavaPlugin implements Listener {



    public static DataManager data;
    public static FileConfiguration getData() {
        return data.getConfig();
    }
    public static void saveData() {
        data.saveConfig();
    }

    public ArrayList<Player> jumping_players = new ArrayList<>();
    public ArrayList<Player> invisible_list = new ArrayList<>();
    public TabManager tab;
    public CustomInventory inventory;
    public NPCManager npcManager;

    private static MainClass instance;
    public static MainClass getInstance() {
        return instance;
    }
    private void setInstance(MainClass instance) {
        this.instance = instance;
    }

    public TagGame tagGame = new TagGame();

    public Economy eco;
//    private ConfigManager configManager;
    public PluginManager pm = getServer().getPluginManager();

    @Override
    public void onEnable() {
//        pm.registerEvents(new SpawnEntity(), this);
//        loadConfigurationManager();
        loadMobsRegister();
        loadItemsRegister();
        tabManager();
        loadNPCListeners();
        loadLaunchPad();

        if (!setUpEconomy()) {
        System.out.println(ChatColor.RED +
                "MUST HAVE VAULT AND AN ECONOMY PLUGIN INSTALLED");
        getServer().getPluginManager().disablePlugin(this);
        return;
    }

        System.out.println("§8Plugin started Properly!");

        pm.registerEvents(new MobKillEvent(this), this);
//              GUI'a
        pm.registerEvents(new CustomInventory(), this);
        this.getCommand("gui").setExecutor(new GUICommand());
        pm.registerEvents(new GUIMoveItem(), this);
////        Vault
        this.getCommand("vault").setExecutor(new OpenCommand());
        pm.registerEvents(new Listeners(), this);
//        Ban Gui
        this.getCommand("bangui").setExecutor(new BanGUICommand());
        pm.registerEvents(new BanInventoryListener(), this);

//        Recipes, Items
        ItemRecipeManager.init();
        ItemManager.init();

        //load config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        /*Item EventsManager*/
        pm.registerEvents(new ItemBlocksEventManager(), this);
        pm.registerEvents(new DetectBlock(this), this);
        
        pm.registerEvents(this, this);
        
        getCommand("vanish").setExecutor(new VanishCommand(this));
        pm.registerEvents(new JoinEvent(this), this);
        pm.registerEvents(new OffHandEvent(this), this);
        
        
        pm.registerEvents(new ChestOpen(), this);
        pm.registerEvents(new ChestPlace(), this);

//        TAG
        pm.registerEvents(new TaggedEvent(this), this);
        getCommand("tag").setExecutor(new TagCommand(this));

        BukkitTask AutoShootChestplate = new AutoShootChestplate(this).runTaskTimer(this, 0, 40);
        GrapplingHookCooldown.setupCooldown();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Project disabled");
        if (tagGame.isPlaying()) {
            tagGame.end();
        }

//       pm.registerEvents(new OnQuit(), this);
//        for (Player player : Bukkit.getOnlinePlayers()) {
//            PacketReader reader = new PacketReader();
//            reader.unInject(player);
//            for (EntityPlayer npc : NPC.getNpcs())
//                NPC.removeNPC(player, npc);
//        }

//        System.out.println("------------------SAVED-----------------------");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("config")) {
            if (args.length == 0) {
                //randomblcok
                sender.sendMessage("Usage: /config reload");
                return true;
            }
            if (args.length > 0) {
                //reload
                if (args[0].equalsIgnoreCase("reload")) {
                    for (String msg : this.getConfig().getStringList("reload.message")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                msg));
                    }

                    this.reloadConfig();
                }
            }

                return true;
            }


        return true;
    }

    private void loadNPCListeners() {


//        Fake Player
        this.setInstance(this);
        this.getCommand("fakeplayer").setExecutor(new FPCommand());
        this.npcManager = new NPCManager();

//                NPCS
        data = new DataManager(this);
        if (data.getConfig().contains("data")) {
            loadNPC();
        }


        for (Player player : Bukkit.getOnlinePlayers()) {
            PacketReader reader = new PacketReader();
            reader.inject(player);
        }
        pm.registerEvents(new OnJoin(), this);
        pm.registerEvents(new ClickNPC(this), this);
        this.getCommand("createnpc").setExecutor(new AddNPC());
        this.getCommand("destroynpc").setExecutor(new DestroyNPC());
        pm.registerEvents(new MovementListener(), this);
        this.getCommand("destroynpc").setTabCompleter(new DestroyNpcTab());
        this.getCommand("createnpc").setTabCompleter(new SkinTab());
    }

//    public void loadConfigurationManager() {
//        configManager = new ConfigManager();
//        configManager.setUpPlayerConfig();
//        configManager.savePlayers();
//        configManager.reloadPlayers();
//    }

    private void tabManager() {

        this.tab = new TabManager(this);
        this.tab.addHeader("&c&l JARON");
//        tab.addHeader("&a&6 HELLO\n Join the &bDiscord");
        this.tab.showTab();

    }

    private void loadMobsRegister() {

//          MOBS Commands
        getCommand("necromancer").setExecutor(new BossesCommands());
        getCommand("revenant").setExecutor(new BossesCommands());
        getCommand("zombieboss").setExecutor(new BossesCommands());
        getCommand("skeletonboss").setExecutor(new BossesCommands());
        getCommand("necromancerapprentice").setExecutor(new BossesCommands());
        getCommand("spawnBosses").setExecutor(new BossesCommands());
        getCommand("spawn").setExecutor(new BossesCommands());
//        Mobs Events
        getServer().getPluginManager().registerEvents(new Necromancer(this), this);
        getServer().getPluginManager().registerEvents(new Reverent(this), this);
        getServer().getPluginManager().registerEvents(new ZombieMob(this), this);
        getServer().getPluginManager().registerEvents(new SkeletonMob(this), this);
        getServer().getPluginManager().registerEvents(new NecromancersApprentice(this), this);
        getServer().getPluginManager().registerEvents(new CoolBeamBoss(this), this);
        //Corpse
//        getServer().getPluginManager().registerEvents(new CorpseInteract(), this);
//        getServer().getPluginManager().registerEvents(new DeathEventCorpse(), this);

    }

    private void loadItemsRegister() {
        /*ITEMS!*/
        this.getCommand("giveall").setExecutor(new ItemCommands());
        this.getCommand("givegrapplinghook").setExecutor(new ItemCommands());
        this.getCommand("giveteleportsword").setExecutor(new ItemCommands());
        this.getCommand("givethegiftingfish").setExecutor(new ItemCommands());
        this.getCommand("giveexplosivebow").setExecutor(new ItemCommands());
        this.getCommand("giveinfinitewaterbucket").setExecutor((new ItemCommands()));
        this.getCommand("giveinfinitelavabucket").setExecutor((new ItemCommands()));
        this.getCommand("givemachinegunbow").setExecutor(new ItemCommands());
        this.getCommand("givemultibreakpickaxe").setExecutor(new ItemCommands());
        this.getCommand("givemidaspickaxe").setExecutor(new ItemCommands());
        this.getCommand("giveboomerang").setExecutor(new ItemCommands());
        this.getCommand("givehomingbow").setExecutor(new ItemCommands());
        this.getCommand("giverocketlauncher").setExecutor(new ItemCommands());
        this.getCommand("givethrowingaxe").setExecutor(new ItemCommands());
        this.getCommand("giveundeadsword").setExecutor(new ItemCommands());
        this.getCommand("givethrowabletnt").setExecutor(new ItemCommands());
        this.getCommand("givelightningaxe").setExecutor(new ItemCommands());
        this.getCommand("giveautosmeltpickaxe").setExecutor(new ItemCommands());
        this.getCommand("givesmokebow").setExecutor(new ItemCommands());
        this.getCommand("givefireball").setExecutor(new ItemCommands());
        this.getCommand("givetripleshotbow").setExecutor(new ItemCommands());
        this.getCommand("givebomberelytra").setExecutor(new ItemCommands());
        this.getCommand("giveautoshootchestplate").setExecutor(new ItemCommands());
        this.getCommand("giveairstrikebow").setExecutor(new ItemCommands());
        this.getCommand("givechunkminerpickaxe").setExecutor(new ItemCommands());
        this.getCommand("giveorecompass").setExecutor(new ItemCommands());
        this.getCommand("givezombieknightspawnegg").setExecutor(new ItemCommands());

       pm.registerEvents(new TeleportSword(), this);
       pm.registerEvents(new GrapplingHook(), this);
       pm.registerEvents(new TheGiftingFish(), this);
       pm.registerEvents(new ExplosiveBow(), this);
       pm.registerEvents(new InfiniteBuckets(), this);
       pm.registerEvents(new MachineGunBow(this), this);
       pm.registerEvents(new MultibreakPickaxe(), this);
       pm.registerEvents(new MidasPickaxe(), this);
       pm.registerEvents(new Boomerang(this), this);
       pm.registerEvents(new HomingBow(this), this);
       pm.registerEvents(new RocketLauncher(this), this);
       pm.registerEvents(new ThrowingAxe(this), this);
       pm.registerEvents(new DamageMultiplierSword(this), this);
       pm.registerEvents(new ThrowableTNT(), this);
       pm.registerEvents(new LightningAxe(), this);
       pm.registerEvents(new AutoSmeltPickaxe(), this);
       pm.registerEvents(new SmokeBow(this), this);
       pm.registerEvents(new Fireballs(this), this);
       pm.registerEvents(new TripleShotBow(), this);
       pm.registerEvents(new BomberElytra(this), this);
       pm.registerEvents(new AirStrikeBow(), this);
       pm.registerEvents(new ChunkMinerPickaxe(), this);
       pm.registerEvents(new OreCompass(), this);
       pm.registerEvents(new ZombieKnightSpawnEgg(), this);

    }
    private void loadLaunchPad() {
        getServer().getPluginManager().registerEvents(new LaunchPadPlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new LaunchPadFallDamageListener(this), this);
    }

    public void loadNPC() {
        FileConfiguration file = data.getConfig();
        if (file.getConfigurationSection("data") != null) {
            file.getConfigurationSection("data").getKeys(false).forEach(npc -> {

                Location location = new Location(Bukkit.getWorld(file.getString("data." + npc + ".world")),
                        file.getInt("data." + npc + ".x"),
                        file.getInt("data." + npc + ".y"),
                        file.getInt("data." + npc + ".z"));
                location.setPitch((float) file.getDouble("data." + npc + ".p"));
                location.setYaw((float) file.getDouble("data." + npc + ".yaw"));

                String name = file.getString("data." + npc + ".name");
                GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.DARK_AQUA + "" + name);
                gameProfile.getProperties().put("textures", new Property("textures",
                        file.getString("data." + npc + ".text"),
                        file.getString("data." + npc + ".signature")));

                NPC.loadNPC(location, gameProfile);
            });
        }
    }

    private boolean setUpEconomy() {
        RegisteredServiceProvider<Economy> economy = getServer().
                getServicesManager().getRegistration(
                        net.milkbowl.vault.economy.Economy.class);
        if (economy != null)
            eco = economy.getProvider();
        return (eco != null);
    }


}