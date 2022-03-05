package me.jaron.plugin.mobManager.mobs;

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

import java.util.Objects;
import java.util.Random;

public class CoolBeamBoss implements Listener {

    static MainClass plugin;

    public CoolBeamBoss(MainClass plugin) {
        CoolBeamBoss.plugin = plugin;
    }

    public static void createNecromancer(Location location) {
        ItemStack diamond = new ItemStack(Material.DIAMOND);
        WitherSkeleton witherSkeleton = Objects.requireNonNull(location.getWorld()).spawn(location, WitherSkeleton.class);

        witherSkeleton.setCustomName(ChatColor.RED + "Cool");
        witherSkeleton.setCustomNameVisible(true);
        witherSkeleton.setCanPickupItems(true);
        witherSkeleton.setAbsorptionAmount(90);
        witherSkeleton.setGlowing(true);
        plugin.getServer().getWorld("world").dropItem(witherSkeleton.getLocation(), diamond);

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
                            FallingBlock fallingBlock = witherSkeleton.getWorld().spawnFallingBlock(witherSkeleton.getLocation().add(0, 2, 0), Material.COMMAND_BLOCK, (byte) 0);
                            fallingBlock.setCustomName("Orb");
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
                            int count = 5;
                            Random r = new Random();
                            witherSkeleton.getWorld().playSound(witherSkeleton.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 5, 5);
                            for (int x = 0; x < 4; x++) {
                                if (count >= 0) {
                                    count--;
                                    Guardian guardian = witherSkeleton.getWorld().spawn(witherSkeleton.getLocation().add(r.nextInt(1 + 1) - 1, 0, r.nextInt(1 + 1) - 1), Guardian.class);
                                    guardian.setCustomName(ChatColor.RED + "Cool Beam!");
                                    guardian.setCustomNameVisible(true);
                                    guardian.setLaser(true);
                                    guardian.setCollidable(false);
                                    guardian.setAbsorptionAmount(90);
                                    guardian.setAI(true);
                                    guardian.setRemoveWhenFarAway(true);
                                    guardian.setGlowing(true);
                                    guardian.setMaxHealth(10);

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
            if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals("Orb")) {
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
                    ItemManager.CoolBeamSpawnEgg.getItemMeta() == null ||
                    ItemManager.CoolBeamSpawnEgg.getItemMeta().getLore() == null ||
                    ItemManager.CoolBeamSpawnEgg.getItemMeta().getLore().get(0) == null
            ) {
                return;
//                System.out.println("was not clicked");
            }

            if (event.getHand() != null && event.getHand().equals(EquipmentSlot.HAND)) {
                if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getLore() != null
                        && event.getItem().getItemMeta().getLore().contains(ItemManager.CoolBeamSpawnEgg.getItemMeta().getLore().get(0))) {
                    Location spawnLocation;
                    if (event.getClickedBlock().isPassable()) {
                        spawnLocation = event.getClickedBlock().getLocation().add(0.5, 0, 0.5);
                    } else {
                        spawnLocation = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().add(0.5, 0, 0.5);
                    }
                    createNecromancer(spawnLocation);
                    if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

}
