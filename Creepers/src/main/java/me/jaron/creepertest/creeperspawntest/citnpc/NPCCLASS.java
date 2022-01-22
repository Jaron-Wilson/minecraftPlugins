package me.jaron.creepertest.creeperspawntest.citnpc;

import me.jaron.creepertest.creeperspawntest.Creeperspawntest;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCCLASS extends Trait {


    public NPCCLASS() {
        super("npcclass");
        plugin = JavaPlugin.getPlugin(Creeperspawntest.class);
    }

    Creeperspawntest plugin = null;


}
