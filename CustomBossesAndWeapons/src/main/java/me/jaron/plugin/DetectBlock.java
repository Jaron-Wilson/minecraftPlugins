package me.jaron.plugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DetectBlock implements Listener {
    MainClass plugin;
//    List<Material> materialList = new ArrayList<>();
//    Material.STONE || event.getBlock().getType() == Material.COBBLESTONE ||
//            event.getBlock().getType() == Material.DIRT || event.getBlock().getType() == Material.GRASS_BLOCK


    public DetectBlock(MainClass plugin) {
        this.plugin = plugin;
    }


//    @EventHandler
//    public void onInteract(PlayerInteractEvent event) {
//
//        Block block = event.getClickedBlock();
//
//        event.getPlayer().sendMessage(ChatColor.GREEN + "You have clicked the block: " + block.getType());
//
//    }


    @EventHandler
    public void startTimer(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("nodes.enable") == true) {

            Material breakmaterial = Material.valueOf(plugin.getConfig().getString("nodes.breakmaterial").toUpperCase());
            Material dirtlist = Material.valueOf(String.valueOf(plugin.getConfig().getStringList("nodes.dirttypes")));
            
            if (!event.getPlayer().isSneaking()) {
                Location blockLocation = event.getBlock().getLocation();
                if (event.getBlock().getLocation().getWorld() != null) {
                    Material beforeClick = event.getBlock().getLocation().getWorld().getType(blockLocation);


//                    for (String item : plugin.getConfig().getStringList("nodes.dirttype")) {
//                        try {
//                            Material material = Material.valueOf(item);
//                            int count = 1;
//                            if (material == Material.DIRT) {
//
//                            } else if (material == Material.COBBLESTONE) {
//                                count = 16;
////                } else if (material == Material.BIRCH_WOOD) {
////                    count = 32;
//                            } else if (material == Material.GOLD_BLOCK) {
//                                count = 6;
//                            }
//                            this.rareItems.add(new ItemStack(material, count));
//                        } catch (Exception ex) {
//                            plugin.getInstance().getLogger().severe();
//                        }
//                    }
//                }
                    if (event.getBlock().getType() == Material.BRICK) {

                        setCancelled(event);
                        sendMessage(event);
                        event.getBlock().getLocation().getWorld().setType(blockLocation, breakmaterial);

                        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                            event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
                        }

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
//                        System.out.println("Replaced");
                            }
                        }, plugin.getConfig().getInt("nodes.time-delays.dirt/stone/cobblestone"));

                    } else if (event.getBlock().getType() == Material.END_STONE) {

                        setCancelled(event);
                        sendMessage(event);
                        event.getBlock().getLocation().getWorld().setType(blockLocation, breakmaterial);

                        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                            event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
                        }

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
//                        System.out.println("Replaced");
                            }
                        }, plugin.getConfig().getInt("nodes.time-delays.endstone"));

                    } else if (event.getBlock().getType() == Material.GRAVEL || event.getBlock().getType() == Material.SAND) {
                        setCancelled(event);
                        sendMessage(event);
                        event.getBlock().getLocation().getWorld().setType(blockLocation, breakmaterial);

                        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                            event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
                        }

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
//                        System.out.println("Replaced");
                            }
                        }, plugin.getConfig().getInt("nodes.time-delays.gravel/sand"));
                    }
                }

//                plugin.getConfig().getConfigurationSection("block").getKeys(false).forEach(key -> {
//                    if (key.equalsIgnoreCase(event.getBlock().getType().toString())) {
//                        for (String i : plugin.getConfig().getStringList("block" + key)) {
//                    if (key == event.getBlock().getType().toString()){
//
//                        }
//                    }
//                });

//                if (event.getBlock().getType().equals(Material.valueOf("block"))) {
//                    event.setCancelled(true);
//                    event.getBlock().getLocation().getWorld().setType(blockLocation, Material.BEDROCK);
//
//                    if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
//                        event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
//                    }
//
//                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//                        public void run() {
//                            event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
////                        System.out.println("Replaced");
//                        }
//                    }, 5L);
//                }


            } else {
                event.setCancelled(false);
            }
        }
    }
    private void sendMessage(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("nodes.messages") == true) {
            event.getPlayer().sendMessage(plugin.getConfig().getString("nodes.message"));
        }
    }

    private void setCancelled(BlockBreakEvent event) {
        event.setCancelled(true);
    }


}
