package me.jaron.plugin.npc;

import me.jaron.plugin.MainClass;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;


public class ClickNPC implements Listener {

    //    private final NpcMain main;
    private final MainClass main;
    public static DataManager data;
//    public CustomInventory inventory;


    public void onInventoryopen(Player player){
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


    public ClickNPC(MainClass main) {
        this.main = main;
    }

    @EventHandler
    public void theClick(rightClickNPC event) {

        data = new DataManager(main);
        FileConfiguration file = data.getConfig();

        Player player = event.getPlayer();
        EntityPlayer npc1 = event.getNpc();

        String npc2 = npc1.getBukkitEntity().getName().substring(2);
        ArrayList<String> messageC = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();

        if (file.getConfigurationSection("data") != null) {

            file.getConfigurationSection("data").getKeys(false).forEach(npc -> {
                String message = file.getString("data." + npc + ".message");
                String Cname = file.getString("data." + npc + ".name");
                for (int i = 0; i < npc.length(); i++) {
                    messageC.add(message);
                    name.add(Cname);
                }
            });

            for (int i = 0; i < name.size(); i++) {
                if (npc2.equals(String.valueOf(name.get(i)))) {
                    player.sendMessage(String.valueOf(messageC.get(i)));
//                    Inventory invs = Bukkit.createInventory(null, 9 * 5, "opp");
//                    player.openInventory(inv);
                    onInventoryopen(player);


                }
            }
        }
    }



}


