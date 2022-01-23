package me.jaron.plugin;

import me.jaron.plugin.commands.BossesCommands;
import me.jaron.plugin.commands.ItemCommands;
import me.jaron.plugin.customRecipies.ItemRecipeManager;
import me.jaron.plugin.itemEvents.*;
import me.jaron.plugin.itemEvents.GrapplingHookFiles.GrapplingHook;
import me.jaron.plugin.itemEvents.GrapplingHookFiles.GrapplingHookCooldown;
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
import me.jaron.plugin.managers.ItemBlocksEventManager;
import me.jaron.plugin.managers.ItemManager;
import me.jaron.plugin.mobManager.mobs.*;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Main extends JavaPlugin implements Listener {


//    private boolean active = false;
    //or maybe if you want listener
    //Bukkit.getPluginManager().registerEvents(new SecondaryClass(this), this);

    @Override
    public void onEnable() {
        System.out.println("§8Plugin started Properly!");

//        Recipes
        ItemRecipeManager.init();

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
        getServer().getPluginManager().registerEvents(new Revenant(this), this);
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


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(ChatColor.GRAY + "Project disabled");
//        System.out.println("------------------SAVED-----------------------");
    }

}