package me.jaron.plugin.minigames.tag.tagcommand;

import me.jaron.plugin.MainClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommand implements CommandExecutor {

    private MainClass plugin;

    public TagCommand(MainClass plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch(args.length) {
                case 1:
                    //  /tag start/end
                    if (args[0].equalsIgnoreCase("start")) {
                        if (!plugin.tagGame.isPlaying()) {
                            plugin.tagGame.tagged(plugin.tagGame.pickFirstIt());
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "TagGame Started"));
                            return true;
                        }else {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Game is running"));
                        }
                    }

                    if (sender.isOp()) {
                        if (args[0].equalsIgnoreCase("end")) {
                            if (plugin.tagGame.isPlaying()) {
                                plugin.tagGame.end();
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "TagGame Ended"));
                                return true;
                            } else {
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Tag is not running"));
                            }
                        }
                    }
                default:
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Try /tag <start/end>"));
            }

            return true;
        }
        sender.sendMessage("no");
        return true;
    }
}