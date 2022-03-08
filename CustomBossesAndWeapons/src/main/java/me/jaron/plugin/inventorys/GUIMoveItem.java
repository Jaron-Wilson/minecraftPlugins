package me.jaron.plugin.inventorys;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUIMoveItem implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent e){

        //Check to see if its the GUI menu
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Custom GUI")){
            Player player = (Player) e.getWhoClicked();
            //Determine what they selected and what to do
            switch(e.getCurrentItem().getType()){
                case TNT:
                    player.closeInventory();
                    player.setHealth(0.0);
                    player.sendMessage("You just killed yourself");
                    break;
                case BREAD:
                    player.closeInventory();
                    player.setFoodLevel(20);
                    player.sendMessage("YUM!");
                    break;
                case DIAMOND_SWORD:
                    player.closeInventory();
                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);


                    ItemMeta sword_meta = sword.getItemMeta();
                    sword_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Sword");
                    ArrayList<String> sword_lore = new ArrayList<>();
                    sword_lore.add(ChatColor.GOLD + "Got a sword.");
                    sword_meta.setLore(sword_lore);
                    sword.setItemMeta(sword_meta);

                    player.getInventory().addItem(sword);
                        player.sendMessage("Don't slice yourself");
                    break;
            }


            e.setCancelled(true); //So they can't take the items
        }

    }

}