package me.jaron.gamerica.plugin.managers;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class NPCManager {

    private ServerPlayer jeffNPC;

    public void spawnJeff(Player p, String name){

        CraftPlayer craftPlayer = (CraftPlayer) p;

        //NMS representation of the MC server
        MinecraftServer server = craftPlayer.getHandle().getServer();
        //NMS representation of the MC world
        ServerLevel level = craftPlayer.getHandle().getLevel();

        //Create a new NPC named Billy Bob, with a new GameProfile to uniquely identify them
        ServerPlayer npc = new ServerPlayer(server, level, new GameProfile(UUID.randomUUID(), name));
        //Set their position. They will be here when we call the packets below to spawn them
        npc.setPos(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());

        ServerGamePacketListenerImpl ps = craftPlayer.getHandle().connection;
        ps.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc));
        ps.send(new ClientboundAddPlayerPacket(npc));

        p.sendMessage(name + " has been spawned! Right click him to talk to him!");

        this.jeffNPC = npc;
    }

    //getter for jeff
    public int getJeffID(){
        //if he is null, return zero. otherwise, return his id
        return this.jeffNPC == null ? 0 : this.jeffNPC.getId();
    }

}
