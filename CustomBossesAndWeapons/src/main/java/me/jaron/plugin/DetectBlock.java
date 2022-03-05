package me.jaron.plugin;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DetectBlock implements Listener {
    MainClass plugin;

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
        Location blockLocation = event.getBlock().getLocation();
        if (!event.getPlayer().isSneaking()) {
            if (event.getBlock().getLocation().getWorld() != null) {
                Material beforeClick = event.getBlock().getLocation().getWorld().getType(blockLocation);
                boolean messages = false;
                if (messages == true) {
                    event.getPlayer().sendMessage("If you want to not have it stay there, then press shift and destroy it");
                }

                if (event.getBlock().getType() == Material.STONE || event.getBlock().getType() == Material.COBBLESTONE || event.getBlock().getType() == Material.DIRT
                ) {
                    event.setCancelled(true);
                    event.getBlock().getLocation().getWorld().setType(blockLocation, Material.BEDROCK);

                    if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                            event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
                    }

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
//                        System.out.println("Replaced");
                            }
                    }, plugin.getConfig().getInt("time-delays.dirt/stone/cobblestone"));

                }else if (event.getBlock().getType() == Material.END_STONE) {

                    event.setCancelled(true);
                    event.getBlock().getLocation().getWorld().setType(blockLocation, Material.BEDROCK);

                    if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                        event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
                    }

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
//                        System.out.println("Replaced");
                        }
                    }, plugin.getConfig().getInt("time-delays.endstone"));

                }else if (event.getBlock().getType() == Material.GRAVEL || event.getBlock().getType() == Material.SAND) {

                    event.setCancelled(true);
                    event.getBlock().getLocation().getWorld().setType(blockLocation, Material.BEDROCK);

                    if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                        event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
                    }

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
//                        System.out.println("Replaced");
                        }
                    }, plugin.getConfig().getInt("time-delays.gravel/sand"));
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


        }else {
            event.setCancelled(false);
        }
    }

}
