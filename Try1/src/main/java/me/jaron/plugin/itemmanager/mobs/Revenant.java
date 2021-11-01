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

public class Revenant implements Listener {

    static Try1 plugin;

    public Revenant(Try1 plugin) {
        this.plugin = plugin;
    }

    public static void createRevenant(Location location) {
        Zombie zombie = location.getWorld().spawn(location, Zombie.class);

        zombie.setCustomName(ChatColor.DARK_BLUE + "Revenant");
        zombie.setCustomNameVisible(true);
        Attributable zombieAttr = zombie;
        AttributeInstance attributeInstance = zombieAttr.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attributeInstance.setBaseValue(1000);

        zombie.setHealth(1000);

        AttributeInstance attributeSpeed = zombieAttr.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        attributeSpeed.setBaseValue(1);

        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SHOVEL));
        zombie.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
        zombie.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
        zombie.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
        zombie.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));

        new BukkitRunnable() {
            int i = 0;

            public void run() {
                if (!zombie.isDead()) {
                    if (zombie.getTarget() != null) {
                        if (i % 2 == 0) {
                            FallingBlock fallingBlock = zombie.getWorld().spawnFallingBlock(zombie.getLocation().add(0, 2, 0), Material.SNOW_BLOCK, (byte) 0);
                            fallingBlock.setCustomName("Necromancer Orb");
                            fallingBlock.setDropItem(false);
                            fallingBlock.setVelocity(zombie.getTarget().getLocation().add(0, 1, 0).subtract(fallingBlock.getLocation()).toVector().multiply(0.5));
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
                                                    player.damage(4, zombie);
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
                            zombie.getWorld().playSound(zombie.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 5, 5);
                            for (int x = 0; x < 4; x++) {
                                Pig pig = zombie.getWorld().spawn(zombie.getLocation().add(r.nextInt(1 + 1) - 1, 0, r.nextInt(1 + 1) - 1), Pig.class);
                                pig.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                                pig.setTarget(zombie.getTarget());
                                pig.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, pig.getLocation(), 20);
                                pig.setAdult();
                                pig.setCustomName(ChatColor.RED + "Revenant's Pig");
                                pig.setCustomNameVisible(true);

                                Attributable pigAttr = pig;

                                AttributeInstance attributeInstance = pigAttr.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                attributeInstance.setBaseValue(400);

                                pig.setHealth(400);

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
