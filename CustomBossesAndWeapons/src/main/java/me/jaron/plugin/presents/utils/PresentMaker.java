//package me.jaron.plugin.presents.utils;
//
//import me.jaron.plugin.MainClass;
//import org.bukkit.Material;
//import org.bukkit.NamespacedKey;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.persistence.PersistentDataContainer;
//import org.bukkit.persistence.PersistentDataType;
//import org.bukkit.util.io.BukkitObjectInputStream;
//import org.bukkit.util.io.BukkitObjectOutputStream;
//
//import javax.naming.Name;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.List;
//
//public class PresentMaker {
//
//    public static ItemStack createPresent(List<ItemStack> items, String from, String to, String message) throws IOException {
//
//        ByteArrayOutputStream bio = new ByteArrayOutputStream();
//        BukkitObjectOutputStream outputStream = new BukkitObjectOutputStream(bio);
//
//        outputStream.writeInt(items.size());
//
//        items.forEach(item -> {
//            try {
//                outputStream.writeObject(item);
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        });
//
//        outputStream.flush();
//
//        byte[] serialized = bio.toByteArray();
//        String encodedString = Base64.getEncoder().encodeToString(serialized);
//
//        ItemStack present = new ItemStack(Material.CHEST, 1);
//        ItemMeta meta = present.getItemMeta();
//        meta.setDisplayName("Present from " + from);
//
//        PersistentDataContainer container = meta.getPersistentDataContainer();
//        ArrayList<String> lore = new ArrayList<>();
//        container.set(new NamespacedKey(MainClass.getInstance(), "presents"), PersistentDataType.STRING, encodedString);
//
//        System.out.println("dumbadumbdumb: " + to);
//
//        if (to != null){
//            container.set(new NamespacedKey(MainClass.getInstance(), "presentRecipient"), PersistentDataType.STRING, to);
//            lore.add(ColorUtils.translateColorCodes("&a&lTO: &c&l" + to));
//            meta.setLore(lore);
//        }
//
//        if (message != null){
//            container.set(new NamespacedKey(MainClass.getInstance(), "presentMessage"), PersistentDataType.STRING, message);
//        }
//
//        present.setItemMeta(meta);
//
//        bio.close();
//        outputStream.close();
//
//        return present;
//    }
//
//    public static ItemStack[] getItemsFromPresent(String encodedString) throws IOException, ClassNotFoundException {
//
//        byte[] rawData = Base64.getDecoder().decode(encodedString);
//        ByteArrayInputStream bio = new ByteArrayInputStream(rawData);
//        BukkitObjectInputStream inputStream = new BukkitObjectInputStream(bio);
//
//        int size = inputStream.readInt();
//
//        ItemStack[] items = new ItemStack[size];
//        for(int i = 0; i < size; i++){
//            items[i] = (ItemStack) inputStream.readObject();
//        }
//
//        inputStream.close();
//
//        return items;
//    }
//
//}
