package me.jaron.plugin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.jaron.plugin.allnpcs.npc.*;
import me.jaron.plugin.allnpcs.npcs.FPCommand;
import me.jaron.plugin.allnpcs.npcs.NPCManager;
import me.jaron.plugin.commands.BossesCommands;
import me.jaron.plugin.commands.ItemCommands;
import me.jaron.plugin.commands.VanishCommand;
import me.jaron.plugin.customRecipies.ItemRecipeManager;
import me.jaron.plugin.guis.GUICommand;
import me.jaron.plugin.guis.GUIMoveItem;
import me.jaron.plugin.guis.ban.commands.BanGUICommand;
import me.jaron.plugin.guis.ban.listener.BanInventoryListener;
import me.jaron.plugin.guis.vaults.commands.OpenCommand;
import me.jaron.plugin.guis.vaults.listeners.Listeners;
import me.jaron.plugin.itemEvents.GrapplingHookFiles.GrapplingHook;
import me.jaron.plugin.itemEvents.GrapplingHookFiles.GrapplingHookCooldown;
import me.jaron.plugin.itemEvents.InfiniteBuckets;
import me.jaron.plugin.itemEvents.armor.AutoShootChestplate;
import me.jaron.plugin.itemEvents.armor.BomberElytra;
import me.jaron.plugin.itemEvents.axesAndSwords.DamageMultiplierSword;
import me.jaron.plugin.itemEvents.axesAndSwords.LightningAxe;
import me.jaron.plugin.itemEvents.axesAndSwords.TeleportSword;
import me.jaron.plugin.itemEvents.axesAndSwords.ThrowingAxe;
import me.jaron.plugin.itemEvents.bows.*;
import me.jaron.plugin.itemEvents.clickWeapons.*;
import me.jaron.plugin.itemEvents.eggs.ZombieKnightSpawnEgg;
import me.jaron.plugin.itemEvents.pickaxes.AutoSmeltPickaxe;
import me.jaron.plugin.itemEvents.pickaxes.ChunkMinerPickaxe;
import me.jaron.plugin.itemEvents.pickaxes.MidasPickaxe;
import me.jaron.plugin.itemEvents.pickaxes.MultibreakPickaxe;
import me.jaron.plugin.listeners.FallDamageListener;
import me.jaron.plugin.listeners.JoinEvent;
import me.jaron.plugin.listeners.OffHandEvent;
import me.jaron.plugin.listeners.PlayerMoveListener;
import me.jaron.plugin.managers.ItemBlocksEventManager;
import me.jaron.plugin.managers.ItemManager;
import me.jaron.plugin.mobManager.mobs.*;
import me.jaron.plugin.tabManagers.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class MainClass extends JavaPlugin implements Listener {

    public static DataManager data;
    private Config config;
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
    private static MainClass instance;
    public NPCManager npcManager;
    public static MainClass getInstance() {
        return instance;
    }
    private void setInstance(MainClass instance) {
        this.instance = instance;
    }



    @Override
    public void onEnable() {
        System.out.println("§8Plugin started Properly!");

        this.tab = new TabManager(this);
        tab.addHeader("&c&l JARON");
//        tab.addHeader("&a&6 HELLO\n Join the &bDiscord");
        tab.showTab();


//        Fake Player
        setInstance(this);
        this.getCommand("fp").setExecutor(new FPCommand());
        this.npcManager = new NPCManager();
//              GUI'a
        getServer().getPluginManager().registerEvents(new CustomInventory(), this);
        getCommand("gui").setExecutor(new GUICommand());
        getServer().getPluginManager().registerEvents(new GUIMoveItem(), this);
////        Vault
        getCommand("vault").setExecutor(new OpenCommand());
        getServer().getPluginManager().registerEvents(new Listeners(), this);
//        Ban Gui
        getCommand("bangui").setExecutor(new BanGUICommand());
        getServer().getPluginManager().registerEvents(new BanInventoryListener(), this);
//        NPCS
        data = new DataManager(this);

        if (data.getConfig().contains("data")) {
            loadNPC();
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            PacketReader reader = new PacketReader();
            reader.inject(player);
        }

        this.getServer().getPluginManager().registerEvents(new OnJoin(), this);
        this.getServer().getPluginManager().registerEvents(new ClickNPC(this), this);
        this.getCommand("createnpc").setExecutor(new AddNPC());
        this.getCommand("destroynpc").setExecutor(new DestroyNPC());
        this.getServer().getPluginManager().registerEvents(new MovementListener(), this);
        this.getCommand("destroynpc").setTabCompleter(new DestroyNpcTab());
        this.getCommand("createnpc").setTabCompleter(new SkinTab());


//        Recipes
        ItemRecipeManager.init();

        //load config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Register listeners
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new FallDamageListener(this), this);

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

        /*Item EventsManager*/
        getServer().getPluginManager().registerEvents(new ItemBlocksEventManager(), this);
        /*ITEMS!*/
        ItemManager.init();

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

        this.getServer().getPluginManager().registerEvents(new TeleportSword(), this);
        this.getServer().getPluginManager().registerEvents(new GrapplingHook(), this);
        this.getServer().getPluginManager().registerEvents(new TheGiftingFish(), this);
        this.getServer().getPluginManager().registerEvents(new ExplosiveBow(), this);
        this.getServer().getPluginManager().registerEvents(new InfiniteBuckets(), this);
        this.getServer().getPluginManager().registerEvents(new MachineGunBow(this), this);
        this.getServer().getPluginManager().registerEvents(new MultibreakPickaxe(), this);
        this.getServer().getPluginManager().registerEvents(new MidasPickaxe(), this);
        this.getServer().getPluginManager().registerEvents(new Boomerang(this), this);
        this.getServer().getPluginManager().registerEvents(new HomingBow(this), this);
        this.getServer().getPluginManager().registerEvents(new RocketLauncher(this), this);
        this.getServer().getPluginManager().registerEvents(new ThrowingAxe(this), this);
        this.getServer().getPluginManager().registerEvents(new DamageMultiplierSword(this), this);
        this.getServer().getPluginManager().registerEvents(new ThrowableTNT(), this);
        this.getServer().getPluginManager().registerEvents(new LightningAxe(), this);
        this.getServer().getPluginManager().registerEvents(new AutoSmeltPickaxe(), this);
        this.getServer().getPluginManager().registerEvents(new SmokeBow(this), this);
        this.getServer().getPluginManager().registerEvents(new Fireballs(this), this);
        this.getServer().getPluginManager().registerEvents(new TripleShotBow(), this);
        this.getServer().getPluginManager().registerEvents(new BomberElytra(this), this);
        this.getServer().getPluginManager().registerEvents(new AirStrikeBow(), this);
        this.getServer().getPluginManager().registerEvents(new ChunkMinerPickaxe(), this);
        this.getServer().getPluginManager().registerEvents(new OreCompass(), this);
        this.getServer().getPluginManager().registerEvents(new ZombieKnightSpawnEgg(), this);

        BukkitTask AutoShootChestplate = new AutoShootChestplate(this).runTaskTimer(this, 0, 40);

        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        GrapplingHookCooldown.setupCooldown();

        this.getServer().getPluginManager().registerEvents(new DetectBlock(this), this);
        this.getServer().getPluginManager().registerEvents(this, this);


        getCommand("vanish").setExecutor(new VanishCommand(this));

        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new OffHandEvent(this), this);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Project disabled");


//        this.getServer().getPluginManager().registerEvents(new OnQuit(), this);
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
//            if (!sender.hasPermission("randomblock.reload")) {
//                sender.sendMessage("NOPE");
//                return true;
//            }
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
        }
        return false;
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

    //Provide a player and return a menu system for that player
    //create one if they don't already have one
//    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
//        PlayerMenuUtility playerMenuUtility;
//        if (!(playerMenuUtilityMap.containsKey(p))) { //See if the player has a playermenuutility "saved" for them
//
//            //This player doesn't. Make one for them add add it to the hashmap
//            playerMenuUtility = new PlayerMenuUtility(p);
//            playerMenuUtilityMap.put(p, playerMenuUtility);
//
//            return playerMenuUtility;
//        } else {
//            return playerMenuUtilityMap.get(p); //Return the object by using the provided player
//        }
//    }


}