//package me.jaron.plugin.presents.listeners;
//
//import me.jaron.plugin.MainClass;
//import me.jaron.plugin.presents.utils.*;
//import org.bukkit.*;
//import org.bukkit.block.Block;
//import org.bukkit.block.Chest;
//import org.bukkit.block.DoubleChest;
//import org.bukkit.block.TileState;
//import org.bukkit.entity.ArmorStand;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.Firework;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.block.BlockPlaceEvent;
//import org.bukkit.event.inventory.InventoryOpenEvent;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.RecipeChoice;
//import org.bukkit.inventory.meta.FireworkMeta;
//import org.bukkit.persistence.PersistentDataContainer;
//import org.bukkit.persistence.PersistentDataType;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.util.EulerAngle;
//
//import java.io.IOException;
//
//public class PresentListener implements Listener {
//
//    @EventHandler
//    public void onPresentPlace(BlockPlaceEvent event) {
//
//        ItemStack itemPlaced = event.getItemInHand();
//
//        if (itemPlaced.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(MainClass.getInstance(), "presents"), PersistentDataType.STRING)) {
//
//            //Bukkit.getServer().getWorld("world").spawnEntity(event.getBlockPlaced().getLocation(), EntityType.FIREBALL);
//
//            Chest chest = (Chest) event.getBlockPlaced().getState();
//            try {
//
//                ItemStack[] items = PresentMaker.getItemsFromPresent(itemPlaced.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(MainClass.getInstance(), "presents"), PersistentDataType.STRING));
//                chest.getSnapshotInventory().setContents(items);
//
//                PersistentDataContainer container = chest.getPersistentDataContainer();
//                container.set(new NamespacedKey(MainClass.getInstance(), "present"), PersistentDataType.INTEGER, 0);
//
//                if (itemPlaced.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(MainClass.getInstance(), "presentMessage"), PersistentDataType.STRING)) {
//                    container.set(new NamespacedKey(MainClass.getInstance(), "presentMessage"), PersistentDataType.STRING, itemPlaced.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(MainClass.getInstance(), "presentMessage"), PersistentDataType.STRING));
//                }
//
//                if (itemPlaced.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(MainClass.getInstance(), "presentRecipient"), PersistentDataType.STRING)) {
//                    container.set(new NamespacedKey(MainClass.getInstance(), "presentRecipient"), PersistentDataType.STRING, itemPlaced.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(MainClass.getInstance(), "presentRecipient"), PersistentDataType.STRING));
//                }
//
//                chest.update();
//
//            } catch (IOException | ClassNotFoundException ioException) {
//                ioException.printStackTrace();
//            }
//        }
//
//    }
//
//    @EventHandler
//    public void onPresentOpen(InventoryOpenEvent event) {
//
//        if (event.getInventory().getHolder() instanceof Chest || event.getInventory().getHolder() instanceof DoubleChest) {
//
//            if (event.getInventory().getHolder() instanceof TileState) {
//
//                Player player = (Player) event.getPlayer();
//
//                TileState state = (TileState) event.getInventory().getHolder();
//
//                PersistentDataContainer container = state.getPersistentDataContainer();
//
//                if (container.has(new NamespacedKey(MainClass.getInstance(), "present"), PersistentDataType.INTEGER)) {
//
//                    if (container.has(new NamespacedKey(MainClass.getInstance(), "presentRecipient"), PersistentDataType.STRING)){
//
//                        if (!container.get(new NamespacedKey(MainClass.getInstance(), "presentRecipient"), PersistentDataType.STRING).equalsIgnoreCase(player.getDisplayName())){
//
//                            event.setCancelled(true);
//
//                            event.getPlayer().sendMessage(ColorUtils.translateColorCodes("&4You are not the recipient of this present! Keep trying and you will end up on the naughty list."));
//
//                            return;
//                        }
//                    }
//
//                    Chest c = (Chest) event.getInventory().getHolder();
//                    Block chest = state.getBlock();
//
//                    ItemStack[] items = c.getInventory().getContents();
//                    event.setCancelled(true);
//
//                    container.remove(new NamespacedKey(MainClass.getInstance(), "present"));
//                    state.update();
//
//                    Chest newChest = (Chest) chest.getState();
//                    newChest.getSnapshotInventory().clear();
//                    newChest.update();
//
//                    ArmorStand merryChristmas = (ArmorStand) chest.getLocation().getWorld().spawnEntity(chest.getLocation().add(0.5, -0.5, 0.5), EntityType.ARMOR_STAND);
//                    merryChristmas.setGravity(false);
//                    merryChristmas.setCanPickupItems(false);
//                    merryChristmas.setCustomName(ColorUtils.translateColorCodes("&c&l&k||&c&lM&a&le&c&lr&a&lr&c&ly &a&lC&c&lh&a&lr&a&li&c&ls&a&lt&c&lm&a&la&c&ls&b&l!!!&c&l&k||"));
//                    merryChristmas.setCustomNameVisible(true);
//                    merryChristmas.setVisible(false);
//
//                    ArmorStand message = (ArmorStand) chest.getLocation().getWorld().spawnEntity(chest.getLocation().add(0.5, -1, 0.5), EntityType.ARMOR_STAND);
//                    message.setGravity(false);
//                    message.setCanPickupItems(false);
//                    if (container.has(new NamespacedKey(MainClass.getInstance(), "presentMessage"), PersistentDataType.STRING)) {
//                        message.setCustomName(ColorUtils.translateColorCodes("\" &f&o" + container.get(new NamespacedKey(MainClass.getInstance(), "presentMessage"), PersistentDataType.STRING) + "&r\""));
//                        message.setCustomNameVisible(true);
//                    } else {
//                        message.setCustomName(" ");
//                        message.setCustomNameVisible(false);
//                    }
//                    message.setVisible(false);
//
//                    ArmorStand as = (ArmorStand) event.getPlayer().getWorld().spawnEntity(chest.getLocation().add(0.5, -0.5, 0.5), EntityType.ARMOR_STAND);
//                    as.setSmall(true);
//                    as.setMarker(true);
//                    as.setSilent(true);
//                    as.setVisible(false);
//                    as.setHeadPose(new EulerAngle(0, 0, 0));
//                    as.setHelmet(new ItemStack(Material.SPRUCE_SAPLING, 1));
//
//
//                    new BukkitRunnable() {
//                        @Override
//                        public void run() {
//
//                            if ((as.getLocation().getY() - chest.getLocation().getY()) >= 3.5){
//
//                                final Firework fw = (Firework) chest.getWorld().spawnEntity(as.getLocation().add(0.5, 0, 0.5), EntityType.FIREWORK);
//                                FireworkMeta fm = fw.getFireworkMeta();
//                                fm.addEffect(FireworkEffect.builder()
//                                        .flicker(true)
//                                        .trail(true)
//                                        .with(FireworkEffect.Type.BURST)
//                                        .withColor(Color.RED, Color.GREEN, Color.LIME)
//                                        .build());
//                                fm.setPower(0);
//                                fw.setFireworkMeta(fm);
//
//                                fw.detonate();
//
//                                as.remove();
//
//
//                                Bukkit.getServer().getWorld("world").playSound(chest.getLocation(), Sound.BLOCK_BELL_RESONATE, 2.0f, 2.0f);
//
//                                Location location = chest.getLocation().add(0.5, 0, 0.5);
//                                for (ItemStack item : items) {
//                                    if (item != null) {
//                                        chest.getWorld().dropItem(location.add(0, 1, 0), item);
//                                    }
//                                }
//
//                                this.cancel();
//                            }else{
//                                as.teleport(as.getLocation().add(0, 0.25, 0));
//                                as.setRotation(as.getLocation().getYaw() + 25f, as.getLocation().getPitch() + 25f);
//                            }
//
//
//                        }
//                    }.runTaskTimer(MainClass.getInstance(), 0, 5);
//
//                    new BukkitRunnable() {
//                        @Override
//                        public void run() {
//
//                            merryChristmas.remove();
//                            message.remove();
//
//                        }
//                    }.runTaskLater(MainClass.getInstance(), 200L);
//
//
//                }
//
//            }
//
//
//        }
//
//    }
//
//}
