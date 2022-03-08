//package me.jaron.plugin;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import com.mojang.authlib.properties.Property;
//
//
//import net.minecraft.network.protocol.game.PacketPlayOutEntity;
//import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
//import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
//import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.server.level.EntityPlayer;
//import net.minecraft.server.level.PlayerInteractManager;
//import net.minecraft.server.level.WorldServer;
//import net.minecraft.server.network.PlayerConnection;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.Location;
//import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
//import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
//import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.world.ChunkLoadEvent;
//import org.bukkit.plugin.java.JavaPlugin;
//
//import com.mojang.authlib.GameProfile;
//
//import org.bukkit.scheduler.BukkitRunnable;
//
//public class mainnpc extends JavaPlugin implements Listener {
//
//    private EntityPlayer npc;
//
//
//
//    @Override
//    public void onEnable() {
//
//        getConfig().options().copyDefaults();
//        saveDefaultConfig();
//
//        Bukkit.getServer().getPluginManager().registerEvents(this, this);
//
//        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
//        WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
//        GameProfile en = new GameProfile(UUID.fromString("00d2bb63-9b11-4c4e-a2e1-93670e0f2097"),null);
//        String skin = "ewogICJ0aW1lc3RhbXAiIDogMTY0NjI2ODc1OTYyMiwKICAicHJvZmlsZUlkIiA6ICJmMjc0YzRkNjI1MDQ0ZTQxOGVmYmYwNmM3NWIyMDIxMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIeXBpZ3NlbCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iMGYyNWFhZjQ3OThiYzgzMGE5M2M4Yzg4NzI3MjY0ODViZTM4ZDAzZDg1Y2M1NDM2MDk3MGJhYWFkYjY1YTdmIgogICAgfQogIH0KfQ==";
//        String sign = "UTwZ5QDIjD7aQ4SjlEDkMPorzR3YVE6ub3xTStBQONHTGLckcd9PLsC5JzbssbdY2P8c/biPSzbyjG3soj8fFV7nS5rv/6rcIjvd4FNVgz8bPqeewbvRrnkU5ZLz8fpaJVP2EdygIghL0ollTXbgUhXayP3GFeROOOgBKJf0H45sHE+HMwl4D+ADhs2y4iZ1d6ZDytxauJ3MCK7iuEyAe6FdSffIe31dYwYWwp01UWbgvkFJWd9PCmD4Hgy0bwl8PIWcdgZdHKzeMA/lQmRBedrlOs0M6SMjcS8tkeE8Cocyt5cZ7sNB7ZPCQ6dV17WdrSk9OdF6/mPoKCXyCSSHA6kBJeR+gYnw2CNczonqrpsoes4za26fg6/19VIG+NXDUww8G+eUGThY4aUB6lOXneKdBiIu2Z5xYB2hh4yy/4DgbIUprpHnbZkusKTkorhWQS6DPQSAZ3CJg0gDBjXzriiHCOW1wBCP9Ba7H1rrbvPW0pwMUE5DbjNokJlDlj3i79kB5MEvSrYZc+ilLhNAbT/EECxBRvjBNnJA1p+gnj+fiFRXsTtbZ8vC+uw+Tq+RkMzhtcif+yw0gB7pY6R1oe1EK53GKgpiV0I3C6UHzpKxmKcnaxzvn5S6IHnYFr+/GDo2VAKR2U2AR785E5uMO34VQdChXEnchS1xSbpAbXo=";
//        en.getProperties().put("textures", new Property("textures", skin, sign));
//        npc = new EntityPlayer(nmsServer, nmsWorld,en, new PlayerInteractManager(nmsWorld));
//        Location loc = new Location(getServer().getWorld("world"), getConfig().getDouble("xyz.x"), getConfig().getDouble("xyz.y")+3, getConfig().getDouble("xyz.z"), getConfig().getInt("xyz.y-rot"), getConfig().getInt("xyz.x-rot"));
//        npc.setLocation(getConfig().getDouble("xyz.x"), getConfig().getDouble("xyz.y"), getConfig().getDouble("xyz.z"), getConfig().getInt("xyz.y-rot"), getConfig().getInt("xyz.x-rot"));
//        npc.getBukkitEntity().setRemoveWhenFarAway(false);
//        Player  player = npc.getBukkitEntity().getPlayer();
//        player.setPlayerListName(ChatColor.DARK_GRAY + "[NPC] L1-media");
//    }
//
//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent e) {
//        Player p = e.getPlayer();
//
//        PlayerConnection connection = ((CraftPlayer) e.getPlayer()).getHandle().playerConnection;
//        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
//        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
//        connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), (byte) ((getConfig().getInt("xyz.x-rot"))*256/360), (byte) ((getConfig().getInt("xyz.y-rot"))*256/360), false));
//        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) ((getConfig().getInt("xyz.head-rot"))*256/360)));
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
//            }
//        }.runTaskTimer(this, 10, 10);
//
//    }
//
//    @EventHandler
//    public void onChunkLoad(ChunkLoadEvent e) {
//        for (Player p : Bukkit.getOnlinePlayers()) {
//            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
//            connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
//            connection.a(new PacketPlayOutNamedEntitySpawn(npc));
//            connection.a(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), (byte) ((getConfig().getInt("xyz.x-rot")) * 256 / 360), (byte) ((getConfig().getInt("xyz.y-rot")) * 256 / 360), false));
//            connection.a(new PacketPlayOutEntityHeadRotation(npc, (byte) ((getConfig().getInt("xyz.head-rot")) * 256 / 360)));
//            new BukkitRunnable() {
//                @Override
//                public void run() {
//                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
//                }
//            }.runTaskTimer(this, 10, 10);
//        }
//    }
//}