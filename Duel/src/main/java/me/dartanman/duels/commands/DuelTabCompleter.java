package me.dartanman.duels.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class DuelTabCompleter implements TabCompleter{

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> arguments = new ArrayList();
		if(args.length == 1) {
//			EX: 1v1 arenas
			arguments.add("help");
			arguments.add("join");
			arguments.add("leaderboard");
			arguments.add("arenas");
			arguments.add("kits");
		}else if(args.length == 2) {
			if(args[1].equalsIgnoreCase("arenas")) {
//			EX: 1v1 arenas create
				arguments.add("create");
				arguments.add("setspawn1");
				arguments.add("setspawn2");
			}else if(args[1].equalsIgnoreCase("kits")) {
//			EX: 1v1 kits create
				arguments.add("create");
				arguments.add("delete");
				arguments.add("list");
				arguments.add("select");
			}
		}else if(args.length == 3) {
			if(args[1].equalsIgnoreCase("arenas")) {
				arguments.add("<arena>");
			}else if(args[1].equalsIgnoreCase("kits")) {
				arguments.add("<kit>");
			}
		}
		return arguments;
	}

}
