package me.jaron.plugin.itemmanager.mobs;

import me.jaron.plugin.Try1;
import org.bukkit.*;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class NecromancersApprentice implements Listener {

    static Try1 plugin;

    public NecromancersApprentice(Try1 plugin) {
        this.plugin = plugin;
    }

    public static void createNecromancerApprentice(Location location) {
        Skeleton skeleton = location.getWorld().spawn(location, Skeleton.class);

        skeleton.setCustomName(ChatColor.YELLOW + "Necromancer's Apprentice");
        skeleton.setCustomNameVisible(true);
        Attributable skeletonAttr = skeleton;
        AttributeInstance attributeInstance = skeletonAttr.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attributeInstance.setBaseValue(1024);

        skeleton.setHealth(1024);

        AttributeInstance attributeSpeed = skeletonAttr.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        attributeSpeed.setBaseValue(0.1);

        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
        skeleton.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
        skeleton.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
        skeleton.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
        skeleton.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));

        new BukkitRunnable() {
            int i = 0;

            public void run() {
                if (!skeleton.isDead()) {
                    if (skeleton.getTarget() != null) {
                        if (i % 2 == 0) {
                            FallingBlock fallingBlock = skeleton.getWorld().spawnFallingBlock(skeleton.getLocation().add(0, 2, 0), Material.DIAMOND_BLOCK, (byte) 0);
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
                            Random r = new Random();
                            skeleton.getWorld().playSound(skeleton.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 5, 5);
                            for (int x = 0; x < 4; x++) {
                                Necromancer.createNecromancer(location);

                            }
                        }
                        i++;
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
            if (event.getEntity().getCustomName() != null  && event.getEntity().getCustomName().equals("Necromancer Orb")){
                event.setCancelled(true);
                event.getEntity().remove();
            }

        }
    }

}
