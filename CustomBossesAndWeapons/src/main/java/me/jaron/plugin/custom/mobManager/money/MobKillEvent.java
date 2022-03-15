package me.jaron.plugin.custom.mobManager.money;

import me.jaron.plugin.MainClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.awt.*;
import java.util.Random;

public class MobKillEvent  implements Listener {

    private MainClass plugin;
    public MobKillEvent(MainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (event.getEntity() instanceof Animals) {
            Player player = event.getEntity().getKiller();
            if (player == null) return;
            Random r = new Random();
            int amount = r.nextInt(10);
            plugin.eco.depositPlayer(player, amount);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "" +
                    ChatColor.BOLD +
                    "+ $" + amount + " for killing animals"));
        }else if (event.getEntity() instanceof Monster) {
            Player player = event.getEntity().getKiller();
            if (player == null) return;
            Random r = new Random();
            int amount = r.nextInt(100);
            plugin.eco.depositPlayer(player, amount);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "" +
                    ChatColor.BOLD +
                    "+ $" + amount + " for killing monsters"));
        }
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        Random r = new Random();
        int amount = r.nextInt(1000);
            if (plugin.eco.getBalance(player) == amount) {
                player.sendMessage("You payed!");
                plugin.eco.withdrawPlayer(player, amount);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "" +
                        ChatColor.BOLD +
                        "- $" + amount));
            }
    }
}
