package me.jaron.plugin;

import me.jaron.plugin.Commands.Commands;
import me.jaron.plugin.itemmanager.mobs.*;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Try1 extends JavaPlugin implements Listener {



//    private boolean active = false;
        //or maybe if you want listener
        //Bukkit.getPluginManager().registerEvents(new SecondaryClass(this), this);

    @Override
    public void onEnable() {


        System.out.println(ChatColor.GREEN + "Plugin started Properly!");


        getCommand("necromancer").setExecutor(new Commands());
        getCommand("revenant").setExecutor(new Commands());
        getCommand("zombieboss").setExecutor(new Commands());
        getCommand("skeletonboss").setExecutor(new Commands());
        getCommand("necromancerapprentice").setExecutor(new Commands());
        getCommand("spawnBosses").setExecutor(new Commands());
        getCommand("spawn").setExecutor(new Commands());


        getServer().getPluginManager().registerEvents(new Necromancer(this), this);
        getServer().getPluginManager().registerEvents(new Revenant(this), this);
        getServer().getPluginManager().registerEvents(new ZombieMob(this), this);
        getServer().getPluginManager().registerEvents(new SkeletonMob(this), this);
        getServer().getPluginManager().registerEvents(new NecromancersApprentice(this), this);
        getServer().getPluginManager().registerEvents(new CoolBeamBoss(this), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Project disabled");
        System.out.println("------------------SAVED-----------------------");
    }

}