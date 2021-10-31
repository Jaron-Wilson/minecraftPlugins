package me.jaron.plugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class Menu implements Listener {

//    Try1 pluginz = new Try1();
//    Menu menuz = new Menu(pluginz);
    private final Inventory inventory;

    public Menu(Plugin p) {
//        inve = Bukkit.getServer().createInventory(null, InventoryType.ENDER_CHEST);
        inventory = Bukkit.getServer().createInventory(null, 9, "Gamemode Chooser");

        ItemStack c = createItem(DyeColor.GREEN, ChatColor.GREEN + "Creative");
        ItemStack su = createItem(DyeColor.YELLOW, ChatColor.YELLOW + "Survival");
        ItemStack a = createItem(DyeColor.RED, ChatColor.RED + "Adventure");

        inventory.setItem(2, c);
        inventory.setItem(4, su);
        inventory.setItem(6, a);

//        Bukkit.getServer().getPluginManager().registerEvents(menuz, p);

    }
    private ItemStack createItem(DyeColor dc, String name) {
        ItemStack i = new ItemStack(new Wool(dc).toItemStack(1));
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList("Set your gamemode", "to " + name.toUpperCase() + " mode"));
        i.setItemMeta(im);
        return i;

    }

    public void show(Player p) {
        p.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(inventory)) return;
        if (e.getCurrentItem()== null || e.getCurrentItem().getItemMeta() == null) return;

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Creative")) {
            e.setCancelled(true);
            e.getWhoClicked().setGameMode(GameMode.CREATIVE);
            e.getWhoClicked().closeInventory();
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Survival")) {
            e.setCancelled(true);
            e.getWhoClicked().setGameMode(GameMode.SURVIVAL);
            e.getWhoClicked().closeInventory();
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Adventure")) {
            e.setCancelled(true);
            e.getWhoClicked().setGameMode(GameMode.ADVENTURE);
            e.getWhoClicked().closeInventory();
        }

    }
}
