package me.jaron.plugin.allnpcs.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.jaron.plugin.MainClass;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class NPC {
    private static final List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();
    private final MainClass main;
    public static DataManager data;

    public NPC(MainClass main) {
        this.main = main;

    }

    public static void createNPC(Player player, String skin) {

        int min = 1;
        int max = 100;

        Random random = new Random();

        int value = random.nextInt(max + min) + min;
        String npcname = "RenameMe" + String.valueOf(value);

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(Bukkit.getWorld(player.getWorld().getName()))).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.DARK_AQUA + npcname);
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile);
        npc.b(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(),
                player.getLocation().getYaw(), player.getLocation().getPitch());


        String[] name = getSkin(player, skin);
        gameProfile.getProperties().put("textures", new Property("textures", name[0], name[1]));
        addNPCPacket(npc);
        NPC.add(npc);

        // variable for data custom message
        String message = "Type a new message in the data.yml. Exit/Restart Server";

        int var = 1;
        if (MainClass.getData().contains("data")) {
            var = MainClass.getData().getConfigurationSection("data").getKeys(false).size() + 1;
        }

        MainClass.getData().set("data." + var + ".x", (int) player.getLocation().getX());
        MainClass.getData().set("data." + var + ".y", (int) player.getLocation().getY());
        MainClass.getData().set("data." + var + ".z", (int) player.getLocation().getZ());
        MainClass.getData().set("data." + var + ".p", player.getLocation().getPitch());
        MainClass.getData().set("data." + var + ".yaw", player.getLocation().getYaw());
        MainClass.getData().set("data." + var + ".world", player.getLocation().getWorld().getName());
        MainClass.getData().set("data." + var + ".name", npcname);
        MainClass.getData().set("data." + var + ".text", name[0]);
        MainClass.getData().set("data." + var + ".signature", name[1]);
        // added for npc to have their own messages
        MainClass.getData().set("data." + var + ".message", message);
        MainClass.saveData();

    }

    public static void loadNPC(Location location, GameProfile profile) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile gameProfile = profile;
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile);

        npc.b(location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch());

        addNPCPacket(npc);
        NPC.add(npc);

    }

    private static String[] getSkin(Player player, String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new String[]{texture, signature};
        } catch (Exception e) {
            EntityPlayer p = ((CraftPlayer) player).getHandle();
            GameProfile profile = p.getBukkitEntity().getProfile();
            Property property = profile.getProperties().get("textures").iterator().next();
            String texture = property.getValue();
            String signature = property.getSignature();
            return new String[]{texture, signature};

        }
    }

    public static void removeNPC(Player player, EntityPlayer npc) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().b;
        connection.a(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId()));
    }

    public static void addNPCPacket(EntityPlayer npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().b;
            connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
            connection.a(new PacketPlayOutNamedEntitySpawn(npc));
        }
    }

    public static void addJoinPacket(Player player) {

        for (EntityPlayer npc : NPC) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().b;
            connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
            connection.a(new PacketPlayOutNamedEntitySpawn(npc));
        }
    }

    public static List<EntityPlayer> getNpcs(){
        return NPC;
    }
}
