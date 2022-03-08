package me.jaron.plugin.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUICommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            Inventory gui = Bukkit.createInventory(player, 9, ChatColor.AQUA + "Custom GUI");

            //Menu Options(Items)
            ItemStack suicide = new ItemStack(Material.TNT); //Kills the player
            ItemStack feed =  new ItemStack(Material.BREAD); //Fills the hunger bar
            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD); //Gives the player a weapon

            //Edit the items
            ItemMeta suicide_meta = suicide.getItemMeta();
            suicide_meta.setDisplayName(ChatColor.RED + "Suicide");
            ArrayList<String> suicide_lore = new ArrayList<>();
            suicide_lore.add(ChatColor.GOLD + "Kill yourself. ;)");
            suicide_meta.setLore(suicide_lore);
            suicide.setItemMeta(suicide_meta);

            ItemMeta feed_meta = feed.getItemMeta();
            feed_meta.setDisplayName(ChatColor.DARK_GREEN + "Feed");
            ArrayList<String> feed_lore = new ArrayList<>();
            feed_lore.add(ChatColor.GOLD + "Hunger no more.");
            feed_meta.setLore(feed_lore);
            feed.setItemMeta(feed_meta);

            ItemMeta sword_meta = sword.getItemMeta();
            sword_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Sword");
            ArrayList<String> sword_lore = new ArrayList<>();
            sword_lore.add(ChatColor.GOLD + "Get a sword.");
            sword_meta.setLore(sword_lore);
            sword.setItemMeta(sword_meta);

            //Put the items in the inventory
            ItemStack[] menu_items = {suicide, feed, sword};
            gui.setContents(menu_items);
            player.openInventory(gui);


        }

        return true;
    }
}