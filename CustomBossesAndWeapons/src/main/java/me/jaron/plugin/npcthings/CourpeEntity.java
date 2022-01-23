//package me.jaron.plugin.npcthings;
//
//import com.mojang.authlib.GameProfile;
//import com.mojang.authlib.properties.Property;
//import me.jaron.plugin.Main;
//import net.minecraft.core.BlockPosition;
//import net.minecraft.network.protocol.game.*;
//import net.minecraft.network.syncher.DataWatcher;
//import net.minecraft.network.syncher.DataWatcherRegistry;
//import net.minecraft.server.level.EntityPlayer;
//import net.minecraft.server.network.PlayerConnection;
//import net.minecraft.world.entity.EntityPose;
//import net.minecraft.world.scores.ScoreboardTeam;
//import net.minecraft.world.scores.ScoreboardTeamBase;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
//import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
//import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
//import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
//import org.bukkit.craftbukkit.v1_18_R1.scoreboard.CraftScoreboard;
//import org.bukkit.entity.Player;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class CourpeEntity {
//
//    public static Map<Integer, EntityPlayer> corpseEntities = new HashMap<>();
//
//    public static void execute(Player player) {
//
//        // Step 1: Create CraftPlayer (NMS player)
//        EntityPlayer craftPlayer = ((CraftPlayer) player).getHandle();
//
//        // Step 2: Create NPC Textures, using Properties and Gameprofile
//        Property textures = (Property) craftPlayer.getProfile().getProperties().get("textures").toArray()[0];
//        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), player.getName());
//        gameProfile.getProperties().put("textures", new Property("textures", textures.getValue(), textures.getSignature()));
//
//        // Step 3: Create Corpse
//        EntityPlayer corpse = new EntityPlayer(
//                ((CraftServer) Bukkit.getServer()).getServer(),
//                ((CraftWorld) player.getWorld()).getHandle(),
//                gameProfile);
//        corpse.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()); // 0.15
//
//        Arrays.stream(player.getInventory().getContents()).forEach(itemStack -> {
//            corpse.getInventory().pickup(CraftItemStack.asNMSCopy(itemStack));
//        });
//
//
//        // Step 4: Spawn fake bed (Makes it look more natural)
//        Location bed = player.getLocation().add(1, 0, 0);
//        corpse.e(new BlockPosition(bed.getX(), bed.getY(), bed.getZ()));
//
//        // Step 5: Hide NameTag
//        ScoreboardTeam team = new ScoreboardTeam(
//                ((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(), player.getName());
//        team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.b);
//
//        PacketPlayOutScoreboardTeam score1 = PacketPlayOutScoreboardTeam.a(team);
//        PacketPlayOutScoreboardTeam score2 = PacketPlayOutScoreboardTeam.a(team, true);
//        PacketPlayOutScoreboardTeam score3 = PacketPlayOutScoreboardTeam.a(
//                team, corpse.getName(), PacketPlayOutScoreboardTeam.a.a);
//
//        // Step 6: Set Pose and Overlays
//        corpse.setPose(EntityPose.d); // .c = sleeping // .d = swimming
//
//        DataWatcher watcher = corpse.getDataWatcher();
//
//        byte b = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40;
//        watcher.set(DataWatcherRegistry.a.a(17), b); //wiki.vg --> Player
//
//        // Step 7: Change Fall Position
//        PacketPlayOutEntity.PacketPlayOutRelEntityMove move = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(
//                corpse.getId(), (byte) 0, (byte) ((player.getLocation().getY() - 1.7 - player.getLocation().getY()) * 32),
//                (byte) 0, false);
//
//        // Step 8: Send packets
//        for (Player onServer : Bukkit.getOnlinePlayers()) {
//            PlayerConnection connection = ((CraftPlayer) onServer).getHandle().b;
//
//            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, corpse));
//            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(corpse));
//
//            connection.sendPacket(score1);
//            connection.sendPacket(score2);
//            connection.sendPacket(score3);
//
//            connection.sendPacket(new PacketPlayOutEntityMetadata(corpse.getId(), watcher, true));
//            connection.sendPacket(move);
//
//            new BukkitRunnable() {
//                public void run() {
//                    connection.sendPacket(new PacketPlayOutPlayerInfo(
//                            PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, corpse));
//                }
//            }.runTaskAsynchronously(Main.getPlugin(Main.class));
//
//            corpseEntities.put(corpse.getId(), corpse);
//
//        }
//
//    }
//}
