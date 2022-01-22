//package me.jaron.plugin.npcthings;
//
//import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
//import net.minecraft.server.network.PlayerConnection;
//import net.minecraft.world.entity.EntityPose;
//import net.minecraft.world.item.ItemStack;
//import org.bukkit.Bukkit;
//import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
//import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.inventory.Inventory;
//
//public class CorpseInteract implements Listener {
//
//    @EventHandler
//    public void onInteract(RightClickCorpse event) {
//        if (event.getCorpse().getPose != EntityPose.d) // c for sleeping d for swimming
//            return;
//        if (event.getCorpse().getInventory().isEmpty())
//            return;
//
//        //should be out npc :)
//        Inventory inventory = Bukkit.createInventory(null, 554, "Dead Player");
//        for (ItemStack itemStack : event.getCorpse().getInventory().getContents())
//            inventory.addItem(CraftItemStack.asBukkitCopy(itemStack));
//
//        event.getPlayer().openInventory(inventory);
//
//        for (Player online : Bukkit.getOnlinePlayers()) {
//            PlayerConnection p = ((CraftPlayer) online).getHandle().b;
//            p.sendPacket(new PacketPlayOutEntityDestroy(event.getCorpse().getId()));
//        }
//        CourpeEntity.corpseEntities.remove(event.getCorpse().getId());
//    }
//
//    @EventHandler
//    public void onJoin(PlayerJoinEvent event) {
//        PacketReader pr = new PacketReader((event.getPlayer()));
//        pr.inject();
//    }
//}
