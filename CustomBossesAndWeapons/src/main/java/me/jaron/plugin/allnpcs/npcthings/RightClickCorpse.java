//package me.jaron.plugin.npcthings;
//
//import net.minecraft.server.level.EntityPlayer;
//import org.bukkit.entity.Player;
//import org.bukkit.event.Event;
//import org.bukkit.event.HandlerList;
//
//public class RightClickCorpse extends Event {
//
//    private static final HandlerList HANDLERS = new HandlerList();
//    private final Player player;
//    private final EntityPlayer corpse;
//
//    public RightClickCorpse(Player player, EntityPlayer corpse) {
//        this.player = player;
//        this.corpse = corpse;
//    }
//
//    public static HandlerList getHandlerList() {
//        return HANDLERS;
//    }
//
//    @Override
//    public HandlerList getHandlers() {
//        return HANDLERS;
//    }
//
//    public Player getPlayer() {
//        return player;
//    }
//
//    public EntityPlayer getCorpse() {
//        return corpse;
//    }
//}
