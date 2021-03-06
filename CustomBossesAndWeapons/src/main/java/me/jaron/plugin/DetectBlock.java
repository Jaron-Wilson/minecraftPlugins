package me.jaron.plugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class DetectBlock implements Listener {
    MainClass plugin;

    public DetectBlock(MainClass plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void startTimer(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("nodes.enable") == true) {

            Material breakmaterial = Material.valueOf(plugin.getConfig().getString("nodes.breakmaterial").toUpperCase());
            if (!event.getPlayer().isSneaking()) {
                Location blockLocation = event.getBlock().getLocation();
                if (event.getBlock().getLocation().getWorld() != null) {
                    if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {

                        List<String> dirtStringList = plugin.getConfig().getStringList("nodes.dirttypes");
                        List<Material> dirtTypes = dirtStringList.stream().map(string -> Material.valueOf(string)).collect(Collectors.toList());
                        List<String> endTypesString = plugin.getConfig().getStringList("nodes.endtypes");
                        List<Material> endTypes = endTypesString.stream().map(string -> Material.valueOf(string)).collect(Collectors.toList());

                        Material beforeClick = event.getBlock().getLocation().getWorld().getType(blockLocation);
//                    boolean isEnd = false;

//                    for (Material type : dirtTypes) {
//                        if (event.getBlock().getType() == type)
//                            isDirt = true;
//
//                        if (isDirt == true) {
//                            setCancelled(event);
//                            sendMessage(event);
//                            event.getBlock().getLocation().getWorld().setType(blockLocation, breakmaterial);
//
//                            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
//                                event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
//                            }
//
//                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//                                public void run() {
//                                    event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
////                        System.out.println("Replaced");
//                                }
//                            }, plugin.getConfig().getInt("nodes.time-delays.dirt/stone/cobblestone"));
//                        }
//                        isDirt = false;
//                    }
//
//                    for (Material type : endTypes) {
//                        if (event.getBlock().getType() == type) isEnd = true;
//
//                        if (isEnd == true) {
//                            setCancelled(event);
//                            sendMessage(event);
//                            event.getBlock().getLocation().getWorld().setType(blockLocation, breakmaterial);
//
//                            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
//                                event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
//                            }
//
//                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//                                public void run() {
//                                    event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
////                        System.out.println("Replaced");
//                                }
//                            }, plugin.getConfig().getInt("nodes.time-delays.enddelay"));
//                            isEnd = false;
//                        }
//                    }
                        boolean isEndType = endTypes.stream().anyMatch(material -> event.getBlock().getType() == material);
                        boolean isDirtType = dirtTypes.stream().anyMatch(material -> event.getBlock().getType() == material);

                        if (!isEndType) {
                            if (isDirtType) {
                                setCancelled(event);
                                event.getBlock().getLocation().getWorld().setType(blockLocation, breakmaterial);

                                if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                                    event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
                                    sendMessage(event);
                                }

                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                    public void run() {
                                        event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
//                        System.out.println("Replaced");
                                    }
                                }, plugin.getConfig().getInt("nodes.time-delays.dirt/stone/cobblestone"));
                            }
                            // handle it not being that type
                            return;
                        }

                        if (!isDirtType) {
                            if (isEndType) {
                                setCancelled(event);
                                event.getBlock().getLocation().getWorld().setType(blockLocation, breakmaterial);

                                if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                                    event.getPlayer().getInventory().addItem(new ItemStack(beforeClick));
                                    sendMessage(event);
                                }

                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                    public void run() {
                                        event.getBlock().getLocation().getWorld().setType(blockLocation, beforeClick);
//                        System.out.println("Replaced");
                                    }
                                }, plugin.getConfig().getInt("nodes.time-delays.enddelay"));
                            }
                            // handle it not being that type
                            return;
                        }

                    }

                } else {
                    event.setCancelled(false);
                }
            }
        }
    }

    private void sendMessage(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("nodes.messages") == true) {
            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(plugin.getConfig().getString("nodes.message")));
        }
    }

    private void setCancelled(BlockBreakEvent event) {

        event.setCancelled(true);
    }


}