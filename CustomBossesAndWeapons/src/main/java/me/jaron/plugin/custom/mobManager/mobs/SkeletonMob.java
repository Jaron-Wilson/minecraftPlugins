package me.jaron.plugin.custom.mobManager.mobs;

import me.jaron.plugin.MainClass;
import me.jaron.plugin.managers.ItemManager;
import org.bukkit.*;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SkeletonMob implements Listener {

    static MainClass plugin;

    public SkeletonMob(MainClass plugin) {
        SkeletonMob.plugin = plugin;
    }

    public static void createSkeletonMob(Location location) {
        Skeleton skeleton = location.getWorld().spawn(location, Skeleton.class);

        skeleton.setCustomName(ChatColor.DARK_BLUE + "Skeleton Mini Boss");
        skeleton.setCustomNameVisible(true);
        Attributable skeletonAttr = skeleton;
        AttributeInstance attributeInstance = skeletonAttr.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attributeInstance.setBaseValue(1024);

        skeleton.setHealth(1024);

        AttributeInstance attributeSpeed = skeletonAttr.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        attributeSpeed.setBaseValue(0.5);

        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_HOE));
        skeleton.setTarget(skeleton.getTarget());
        skeleton.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
        skeleton.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        skeleton.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        skeleton.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));

        new BukkitRunnable() {
            int i = 0;

            public void run() {
                if (!skeleton.isDead()) {
                    if (skeleton.getTarget() != null) {
                        if (i % 2 == 0) {
                            FallingBlock fallingBlock = skeleton.getWorld().spawnFallingBlock(skeleton.getLocation().add(0, 2, 0), Material.AMETHYST_BLOCK, (byte) 0);
                            fallingBlock.setCustomName("Necromancer Orb");
                            fallingBlock.setDropItem(false);
                            fallingBlock.setVelocity(skeleton.getTarget().getLocation().add(0, 1, 0).subtract(fallingBlock.getLocation()).toVector().multiply(0.5));
                            fallingBlock.getWorld().playSound(fallingBlock.getLocation(), Sound.ENTITY_WITHER_SHOOT, 5, 5);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (!fallingBlock.isDead()) {
                                        fallingBlock.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, fallingBlock.getLocation(), 1);
                                        for (Entity entity : fallingBlock.getNearbyEntities(2, 2, 2)) {
                                            if (entity instanceof Player) {
                                                if (fallingBlock.getLocation().distanceSquared(entity.getLocation()) < 1) {
                                                    Player player = (Player) entity;
                                                    player.damage(4, skeleton);
                                                    fallingBlock.remove();
                                                    cancel();
                                                }
                                            }
                                        }
                                    } else {
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(plugin, 0L, 2L);
                        }
                        if (i % 10 == 0) {
                            int count = 5;
                            Random r = new Random();
                            skeleton.getWorld().playSound(skeleton.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 5, 5);
                            for (int x = 0; x < 4; x++) {
                                if (count >= 0) {
                                    count--;
                                    ZombieMob.createZombieMob(location);

                                    Zombie zombie = skeleton.getWorld().spawn(skeleton.getLocation().add(r.nextInt(1 + 1) - 1, 0, r.nextInt(1 + 1) - 1), Zombie.class);
                                    zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                                    zombie.setTarget(skeleton.getTarget());
                                    zombie.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, zombie.getLocation(), 20);
                                    zombie.setAdult();
                                    zombie.setCustomName(ChatColor.RED + "SkeletonMob's Assistant");
                                    zombie.setCustomNameVisible(true);

                                    Attributable zombieAttr = zombie;

                                    AttributeInstance attributeInstance = zombieAttr.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                    attributeInstance.setBaseValue(900);

                                    zombie.setHealth(900);

                                }
                            }
                            i++;
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    @EventHandler
    public void onChange(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals("Necromancer Orb")) {
                event.setCancelled(true);
                event.getEntity().remove();
            }

        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (event.getClickedBlock() == null ||
                    event.getItem() == null ||
                    event.getItem().getItemMeta() == null ||
                    event.getItem().getItemMeta().getLore() == null ||
                    ItemManager.MiniSkeletonBoss.getItemMeta() == null ||
                    ItemManager.MiniSkeletonBoss.getItemMeta().getLore() == null ||
                    ItemManager.MiniSkeletonBoss.getItemMeta().getLore().get(0) == null
            ) {
                return;
//                System.out.println("was not clicked");
            }

            if (event.getHand() != null && event.getHand().equals(EquipmentSlot.HAND)) {
                if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getLore() != null
                        && event.getItem().getItemMeta().getLore().contains(ItemManager.MiniSkeletonBoss.getItemMeta().getLore().get(0))) {
                    Location spawnLocation;
                    if (event.getClickedBlock().isPassable()) {
                        spawnLocation = event.getClickedBlock().getLocation().add(0.5, 0, 0.5);
                    } else {
                        spawnLocation = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().add(0.5, 0, 0.5);
                    }
                    createSkeletonMob(spawnLocation);
                    if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
}
