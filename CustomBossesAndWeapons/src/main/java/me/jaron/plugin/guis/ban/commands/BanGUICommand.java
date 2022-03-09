package me.jaron.plugin.guis.ban.commands;
import me.jaron.plugin.guis.ban.utils.BanMenuUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class BanGUICommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (sender.hasPermission("op")) {
                BanMenuUtils.openBanMenu(p);
            }
        }

        return true;
    }

}
