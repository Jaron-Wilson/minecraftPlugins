//package me.jaron.plugin.npcthings;
//
//import org.bukkit.ChatColor;
//import org.bukkit.GameMode;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.entity.EntityDamageEvent;
//import org.bukkit.inventory.ItemFlag;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DeathEventCorpse implements Listener {
//
//    public class PlayerDeath implements Listener {
//
//        @EventHandler
//        public void onDamage(EntityDamageEvent event) {
//            if (event.getEntity() instanceof Player) {
//                Player player = (Player) event.getEntity();
//                if ((player.getHealth() - event.getDamage()) <= 0) {
//                    // cancel damage event
//                    event.setCancelled(true);
//
//                    // Spawn corpse
//                    CourpeEntity.execute(player);
//
//                    // Spawn revive potion
//                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), getRespawnItem(player));
//
//                    // Change player stats
//                    player.setHealth(20.0);
//                    player.getInventory().clear();
//                    player.setGameMode(GameMode.SPECTATOR);
//
//                }
//            }
//        }
//
//        /***
//         * Returns a respawn potion for dead player
//         * @param player - dead player
//         * @return - ItemStack potion
//         */
//        private ItemStack getRespawnItem(Player player) {
//            ItemStack potion = new ItemStack(Material.POTION);
//            ItemMeta meta = potion.getItemMeta();
//            meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName());
//
//            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
//
//            List<String> lore = new ArrayList<>();
//            lore.add(ChatColor.GRAY + "Use at a Respawn tower!");
//            meta.setLore(lore);
//            potion.setItemMeta(meta);
//            return potion;
//        }
//
//    }
//}
