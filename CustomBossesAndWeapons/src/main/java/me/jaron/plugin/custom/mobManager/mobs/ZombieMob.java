package me.jaron.plugin.custom.mobManager.mobs;

import me.jaron.plugin.MainClass;
import me.jaron.plugin.managers.ItemManager;
import org.bukkit.*;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class ZombieMob implements Listener {

    static MainClass plugin;

    public ZombieMob(MainClass plugin) {
        ZombieMob.plugin = plugin;
    }

    public static void createZombieMob(Location location) {
        Zombie zombie = location.getWorld().spawn(location, Zombie.class);

        zombie.setCustomName(ChatColor.DARK_BLUE + "Zombie Mini Boss");
        zombie.setCustomNameVisible(true);
        Attributable zombieAttr = zombie;
        AttributeInstance attributeInstance = zombieAttr.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attributeInstance.setBaseValue(1024);

        zombie.setHealth(1024);

        AttributeInstance attributeSpeed = zombieAttr.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        attributeSpeed.setBaseValue(0.5);

        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_HOE));
        zombie.setTarget(zombie.getTarget());
        zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
        zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        zombie.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        zombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));

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
                            int count = 5;
                            Random r = new Random();
                            zombie.getWorld().playSound(zombie.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 5, 5);
                            for (int x = 0; x < 4; x++) {
                                if (count >= 0) {
                                    count--;
                                    Necromancer.createNecromancer(location);
//                               Pig pig = zombie.getWorld().spawn(zombie.getLocation().add(r.nextInt(1 + 1) - 1, 0, r.nextInt(1 + 1) - 1), Pig.class);


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
                    ItemManager.MiniZombieBoss.getItemMeta() == null ||
                    ItemManager.MiniZombieBoss.getItemMeta().getLore() == null ||
                    ItemManager.MiniZombieBoss.getItemMeta().getLore().get(0) == null
            ) {
                return;
//                System.out.println("was not clicked");
            }

            if (event.getHand() != null && event.getHand().equals(EquipmentSlot.HAND)) {
                if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getLore() != null
                        && event.getItem().getItemMeta().getLore().contains(ItemManager.MiniZombieBoss.getItemMeta().getLore().get(0))) {
                    Location spawnLocation;
                    if (event.getClickedBlock().isPassable()) {
                        spawnLocation = event.getClickedBlock().getLocation().add(0.5, 0, 0.5);
                    } else {
                        spawnLocation = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().add(0.5, 0, 0.5);
                    }
                    createZombieMob(spawnLocation);
                    if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
}
