package me.jaron.creepertest.creeperspawntest.commands;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.Skin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class NPCZCOMMAND implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {

            Player player = (Player) sender;
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "BOT");
            player.sendMessage("NPCZ CREATED");
            npc.spawn(player.getLocation());
            npc.getNavigator().setTarget(player.getLocation());

            npc.setName("Test_BOT");
        }
        return false;
    }

}

