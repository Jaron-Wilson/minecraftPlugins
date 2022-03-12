package me.jaron.plugin.moneyManagers;

import me.jaron.plugin.MainClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class MobKillEvent  implements Listener {

    private MainClass plugin;
    public MobKillEvent(MainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (event.getEntity() instanceof Mob) {
            Player player = event.getEntity().getKiller();
            if (player == null) return;
            Random r = new Random();
            int amount = r.nextInt(1000);
            plugin.eco.depositPlayer(player, amount);
            player.sendMessage(ChatColor.GREEN + "" +
                    ChatColor.BOLD +
                    "+ $" + amount);
        }
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player killerplayer = event.getEntity().getKiller();
        Player player = event.getEntity().getPlayer();
        Random r = new Random();
        int amount = r.nextInt(1000);

        if (plugin.eco.getBalance(player) == amount) {
            player.sendMessage("You Can pay");
            plugin.eco.withdrawPlayer(player, amount);
            player.sendMessage(ChatColor.RED + "" +
                    ChatColor.BOLD +
                    "- $" + amount);
        }else if (plugin.eco.getBalance(player) != amount){
            player.sendMessage("Why Do you not have $" + amount + " ?");
        }

//        if (player.getKiller() == killerplayer) {
//            int worthAmount =
//            plugin.eco.withdrawPlayer(player, worthAmount);
//            plugin.eco.depositPlayer(killerplayer,);
//        }

    }
}
