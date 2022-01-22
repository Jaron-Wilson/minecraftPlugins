package me.jaron.creepertest.creeperspawntest;

import me.jaron.creepertest.creeperspawntest.commands.NPCZCOMMAND;
import me.jaron.creepertest.creeperspawntest.npcnoncitizenz.Join;
import me.jaron.creepertest.creeperspawntest.npcnoncitizenz.NotPlayerControlled;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Creeperspawntest extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        getCommand("npcz").setExecutor(new NPCZCOMMAND());
            this.getServer().getPluginManager().registerEvents(new Join(), this);
        System.out.println(ChatColor.AQUA + "ready");
//        getServer().getPluginManager().registerEvents(new Creeperspawnz(), this);
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED + "Disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("createnpc")) {
            if (!(sender instanceof Player)){
                return true;
            }
            Player player = (Player) sender;
            NotPlayerControlled.instance().createNPC(player);
            player.sendMessage("NPC CREATED");
        }
        return false;
    }
}