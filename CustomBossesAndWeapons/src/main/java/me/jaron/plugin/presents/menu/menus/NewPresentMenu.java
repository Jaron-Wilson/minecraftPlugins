//package me.jaron.plugin.presents.menu.menus;
//
//import me.jaron.plugin.presents.menu.Menu;
//import me.jaron.plugin.presents.menu.PlayerMenuUtility;
//import me.jaron.plugin.presents.utils.ColorUtils;
//import me.jaron.plugin.presents.utils.PresentMaker;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class NewPresentMenu extends Menu {
//
//    public NewPresentMenu(PlayerMenuUtility playerMenuUtility) {
//        super(playerMenuUtility);
//    }
//
//    @Override
//    public String getMenuName() {
//        return "New Present";
//    }
//
//    @Override
//    public int getSlots() {
//        return 54;
//    }
//
//    @Override
//    public void handleMenu(InventoryClickEvent e) {
//
//        Player p = playerMenuUtility.getOwner();
//
//        if (e.getSlot() == 0){
//            e.setCancelled(true);
//        }else if (e.getSlot() == 40){
//
//            //get the items in the top 3 rows
//            ArrayList<ItemStack> items = new ArrayList<>();
//            ItemStack present;
//
//            try {
//
//                if (e.getInventory().getItem(0) != null){
//                    for (int i = 1; i <= 26; i++){
//                        if (e.getInventory().getItem(i) != null){
//                            items.add(e.getInventory().getItem(i));
//                        }
//                    }
//
//                    present = PresentMaker.createPresent(items, p.getDisplayName(), playerMenuUtility.getRecipient(), playerMenuUtility.getPresentMessage());
//                }else{
//                    for (int i = 0; i <= 26; i++){
//                        if (e.getInventory().getItem(i) != null){
//                            items.add(e.getInventory().getItem(i));
//                        }
//                    }
//                    present = PresentMaker.createPresent(items, p.getDisplayName(), null, null);
//                }
//
//                p.closeInventory();
//                p.getInventory().addItem(present);
//                p.sendMessage("You have been given the present.");
//            } catch (IOException ioException) {
//                p.sendMessage("Unable to create present. Sowwy.");
//            }
//
//        }else if (e.getSlot() >= 27 && e.getSlot() < 54){
////            e.setCancelled(true);
////        }
//
//    }
//
//    @Override
//    public void setMenuItems() {
//
//        for (int i = 27; i < 54; i++){
//            inventory.setItem(i, FILLER_GLASS);
//        }
//
//        ItemStack info = makeItem(Material.PAPER, ColorUtils.translateColorCodes("&e&lInfo"), ColorUtils.translateColorCodes("&aPut the items you"),
//                ColorUtils.translateColorCodes("&awant in the present"), ColorUtils.translateColorCodes("&ain the box above."));
//
//        ItemStack create = makeItem(Material.BELL, ColorUtils.translateColorCodes("&c&lC&a&lr&c&le&a&la&c&lt&a&le &c&lP&f&lr&c&le&f&ls&c&le&f&ln&c&lt"), ColorUtils.translateColorCodes("&aClick this item and the &c&lElves&a..."),
//                ColorUtils.translateColorCodes("&awill package your &f&l&nPRESENT"));
//
//        inventory.setItem(39, info);
//        inventory.setItem(40, create);
//
//        if (playerMenuUtility.getPresentMessage() != null || playerMenuUtility.getRecipient() != null){
//            ItemStack messageItem = new ItemStack(Material.NAME_TAG, 1);
//            ItemMeta messageMeta = messageItem.getItemMeta();
//
//            if (playerMenuUtility.getRecipient() != null){
//
//                System.out.println("recipient: " + playerMenuUtility.getRecipient());
//                messageMeta.setDisplayName(ColorUtils.translateColorCodes("&e&lTo: &f" + playerMenuUtility.getRecipient()));
//
//            }else{
//                messageMeta.setDisplayName(ColorUtils.translateColorCodes("&e&lMessage &#ff00ef&l<3:"));
//            }
//
//            ArrayList<String> messageLore = new ArrayList<>();
//            if (playerMenuUtility.getPresentMessage() != null){
//                messageLore.add(ColorUtils.translateColorCodes("&#08d30e" + playerMenuUtility.getPresentMessage()));
//                messageMeta.setLore(messageLore);
//            }else{
//                messageLore.add("And a happy new year!!!");
//            }
//
//            messageItem.setItemMeta(messageMeta);
//
//            inventory.addItem(messageItem);
//        }
//
//    }
//
//}
