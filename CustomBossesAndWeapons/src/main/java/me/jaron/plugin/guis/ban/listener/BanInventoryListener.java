package me.jaron.plugin.guis.ban.listener;

import me.jaron.plugin.MainClass;
import me.jaron.plugin.guis.ban.utils.BanMenuUtils;
import org.bukkit.BanList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.Date;

public class BanInventoryListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        //Check inventory
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLUE + "Player List")){
            //make sure they clicked on a player head
            if (e.getCurrentItem().getType() == Material.PLAYER_HEAD){

                //Get player they clicked on from item name
                Player whoToBan = MainClass.getInstance().getServer().getPlayerExact(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));

                BanMenuUtils.openPlayerMenu(player, whoToBan);
                e.setCancelled(true);
            }

        }else if(e.getView().getTitle().equalsIgnoreCase("Ban This Noob")){
            switch(e.getCurrentItem().getType()){
                case BARRIER:
                    BanMenuUtils.openBanMenu(player);
                    break;
                case WOODEN_AXE:
                    //Get name
                    String name = e.getClickedInventory().getItem(4).getItemMeta().getDisplayName();
                    player.getServer().getBanList(BanList.Type.NAME).addBan(ChatColor.stripColor(name), "Op did that", null, "The code god");
                    player.sendMessage(ChatColor.GREEN + "Banned Player");
                    break;
            }
            //make it so they cant move items
            e.setCancelled(true);
        }

    }

}
