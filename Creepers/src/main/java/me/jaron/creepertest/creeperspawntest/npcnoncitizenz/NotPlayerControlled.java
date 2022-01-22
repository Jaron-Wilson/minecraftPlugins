package me.jaron.creepertest.creeperspawntest.npcnoncitizenz;


import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_16_R3.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotPlayerControlled {

    private static final NotPlayerControlled STATIC_INSTANCE = new NotPlayerControlled();
    private final List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();


    public static NotPlayerControlled instance(){
        return STATIC_INSTANCE;
    }

    public void createNPC(Player player) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.GREEN + "" + ChatColor.BOLD + "JA_RON");
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
        npc.setLocation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(),
                player.getLocation().getYaw(), player.getLocation().getPitch());
        npc.canPickUpLoot = true;

        addNPCPacket(npc);
        NPC.add(npc);
    }

    public void addNPCPacket(EntityPlayer npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
        }
    }

    public void addJoinPacket(Player npc) {
        for (EntityPlayer player : NPC) {
            PlayerConnection connection = ((CraftPlayer) npc).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, (EntityPlayer) npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(player));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(player, (byte) (player.yaw * 256 / 360)));
        }
    }

    public List<EntityPlayer> getNPCs() {
        return NPC;
    }
}

