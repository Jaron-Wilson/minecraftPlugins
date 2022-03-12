package me.jaron.plugin.tabManagers;

import me.jaron.plugin.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class TabManager {

    private List<String> headers = new ArrayList<>();
    private List<String> footers = new ArrayList<>();
    private HashMap<String, Function<Player, String>> placeHolders = new HashMap<>();

    private MainClass plugin;
    public TabManager(MainClass plugin){
        this.plugin = plugin;
    }

    public void showTab(){

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int headerCount = 0;
            int footerCount = 0;

            @Override
            public void run() {
                try{
                    if(headers.isEmpty() && footers.isEmpty()) return;
                    if(headerCount >= headers.size()) headerCount = 0;
                    if(footerCount >= footers.size()) footerCount = 0;

                    for(Player player : Bukkit.getOnlinePlayers()) {
                        String header = headers.size() == 0 ? "" : headers.get(headerCount);
                        String footer = footers.size() == 0 ? "" : footers.get(footerCount);
                        for(Map.Entry<String, Function<Player, String>> entry : placeHolders.entrySet()) {
                            header = header.replace(entry.getKey(),entry.getValue().apply(player));
                            footer = footer.replace(entry.getKey(),entry.getValue().apply(player));
                        }
                        player.setPlayerListHeaderFooter(header, footer);
                    }
                    headerCount++;
                    footerCount++;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, 5, 10);
    }

    public void addPlaceHolder(String replace, Function<Player, String> replacement) {
        placeHolders.put(replace, replacement);
    }
    public void addPlaceHolder(String replace, Supplier<String> replacement) {
        placeHolders.put(replace, (player) -> replacement.get());
    }


    public void addHeader(String header){
        headers.add((format(header)));
    }

    public void addFooter(String footer){
        footers.add((format(footer)));
    }

    private String format(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}




















//package me.jaron.plugin.tabManagers;
//
//import me.jaron.plugin.MainClass;
//import net.minecraft.network.chat.ChatComponentText;
//import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
//import org.bukkit.entity.Player;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TabManager {
//
//    private List<ChatComponentText> headers = new ArrayList<>();
//    private List<ChatComponentText> footers = new ArrayList<>();
//
//    private MainClass plugin;
//    public TabManager(MainClass plugin){
//        this.plugin = plugin;
//    }
//
//    public void showTab(){
//
//
//        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
//            int count1 = 0;
//            int count2 = 0;
//
//            @Override
//            public void run() {
//                try{
//                    if(headers.isEmpty() && footers.isEmpty())
//                        return;
//                    if(count1 >= headers.size())
//                        count1 = 0;
//                    if(count2 >= footers.size())
//                        count2 = 0;
//
//                        for(Player player : Bukkit.getOnlinePlayers())
//                            ((CraftPlayer)player).getHandle().b.a(new PacketPlayOutPlayerListHeaderFooter(headers.get(count1), footers.get(count2)));
//
//                    count1++;
//                    count2++;
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }, 5, 10);
//    }
//
//
//
//
//    public void addHeader(String header){
//        headers.add(new ChatComponentText(format(header)));
//    }
//
//    public void addFooter(String footer){
//        footers.add(new ChatComponentText(format(footer)));
//    }
//
//    private String format(String msg){
//        return ChatColor.translateAlternateColorCodes('&', msg);
//    }
//
//}