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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class CoolBeamBoss implements Listener {

    static Try1 plugin;

    public CoolBeamBoss(Try1 plugin) {
        this.plugin = plugin;
    }

    public static void createNecromancer(Location location) {
        WitherSkeleton witherSkeleton = location.getWorld().spawn(location, WitherSkeleton.class);

        witherSkeleton.setCustomName(ChatColor.RED + "Cool");
        witherSkeleton.setCustomNameVisible(true);
        witherSkeleton.setCanPickupItems(true);
        Attributable witherSkeletonAttr = witherSkeleton;
        AttributeInstance attributeInstance = witherSkeletonAttr.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attributeInstance.setBaseValue(900);

        witherSkeleton.setHealth(900);

        AttributeInstance attributeSpeed = witherSkeletonAttr.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        attributeSpeed.setBaseValue(1.8);

        witherSkeleton.getEquipment().setItemInOffHand(new ItemStack(Material.DIAMOND_AXE));
        witherSkeleton.getEquipment().setItemInMainHand(new ItemStack(Material.ARROW));
        witherSkeleton.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_BLOCK));

        new BukkitRunnable() {
            int i = 0;

            public void run() {
                if (!witherSkeleton.isDead()) {
                    if (witherSkeleton.getTarget() != null) {
                        if (i % 2 == 0) {
                            FallingBlock fallingBlock = witherSkeleton.getWorld().spawnFallingBlock(witherSkeleton.getLocation().add(0, 2, 0), Material.SNOW_BLOCK, (byte) 0);
                            fallingBlock.setCustomName("Necromancer Orb");
                            fallingBlock.setDropItem(false);
                            fallingBlock.setVelocity(witherSkeleton.getTarget().getLocation().add(0, 1, 0).subtract(fallingBlock.getLocation()).toVector().multiply(0.5));
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
                                                    player.damage(4, witherSkeleton);
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
                            witherSkeleton.getWorld().playSound(witherSkeleton.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 5, 5);
                            for (int x = 0; x < 4; x++) {
                                Arrow arrow = witherSkeleton.getWorld().spawn(witherSkeleton.getLocation().add(r.nextInt(1 + 1) - 1, 0, r.nextInt(1 + 1) - 1), Arrow.class);
                                arrow.setCustomName(ChatColor.RED + "Cool Beam!");
                                arrow.setCustomNameVisible(true);

                                arrow.doesBounce();
                                arrow.hasCustomEffect(PotionEffectType.BLINDNESS);

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
