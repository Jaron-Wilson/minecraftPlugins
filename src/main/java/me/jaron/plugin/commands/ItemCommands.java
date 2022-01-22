package me.jaron.plugin.commands;

import me.jaron.plugin.managers.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (player.hasPermission("op")) {
                if (cmd.getName().equalsIgnoreCase("giveall")) {
                    player.getInventory().addItem(ItemManager.GrapplngHook);
                    player.getInventory().addItem(ItemManager.TeleportSword);
                    player.getInventory().addItem(ItemManager.TheGiftingFish);
                    player.getInventory().addItem(ItemManager.ExplosiveBow);
                    player.getInventory().addItem(ItemManager.InfiniteWaterBucket);
                    player.getInventory().addItem(ItemManager.InfiniteLavaBucket);
                    player.getInventory().addItem(ItemManager.MachineGunBow);
                    player.getInventory().addItem(ItemManager.MultibreakPickaxe);
                    player.getInventory().addItem(ItemManager.MidasPickaxe);
                    player.getInventory().addItem(ItemManager.Boomerang);
                    player.getInventory().addItem(ItemManager.HomingBow);
                    player.getInventory().addItem(ItemManager.RocketLauncher);
                    player.getInventory().addItem(ItemManager.ThrowingAxe);
                    player.getInventory().addItem(ItemManager.UndeadSword);


                    player.getInventory().addItem(ItemManager.ThrowableTNT);
                    player.getInventory().addItem(ItemManager.LightningAxe);
                    player.getInventory().addItem(ItemManager.AutoSmeltPickaxe);
                    player.getInventory().addItem(ItemManager.SmokeBow);
                    player.getInventory().addItem(ItemManager.Fireball);
                    player.getInventory().addItem(ItemManager.TripleShotBow);
                    player.getInventory().addItem(ItemManager.BomberElytra);
                    player.getInventory().addItem(ItemManager.AutoShootChestplate);
                    player.getInventory().addItem(ItemManager.AirStrikeBow);
                    player.getInventory().addItem(ItemManager.ChunkMinerPickaxe);
                    player.getInventory().addItem(ItemManager.OreCompass);
                    player.getInventory().addItem(ItemManager.ZombieKnightSpawnEgg);

                }
                if (cmd.getName().equalsIgnoreCase("givegrapplinghook")) {
                    player.getInventory().addItem(ItemManager.GrapplngHook);
                }
                if (cmd.getName().equalsIgnoreCase("giveteleportsword")){
                    player.getInventory().addItem(ItemManager.TeleportSword);
                }
                if (cmd.getName().equalsIgnoreCase("givethegiftingfish")){
                    player.getInventory().addItem(ItemManager.TheGiftingFish);
                }
                if(cmd.getName().equalsIgnoreCase("giveexplosivebow")){
                    player.getInventory().addItem(ItemManager.ExplosiveBow);
                }
                if(cmd.getName().equalsIgnoreCase("giveinfinitewaterbucket")){
                    player.getInventory().addItem(ItemManager.InfiniteWaterBucket);
                }
                if(cmd.getName().equalsIgnoreCase("giveinfinitelavabucket")){
                    player.getInventory().addItem(ItemManager.InfiniteLavaBucket);
                }
                if(cmd.getName().equalsIgnoreCase("givemachinegunbow")){
                    player.getInventory().addItem(ItemManager.MachineGunBow);
                }
                if(cmd.getName().equalsIgnoreCase("givemultibreakpickaxe")){
                    player.getInventory().addItem(ItemManager.MultibreakPickaxe);
                }
                if(cmd.getName().equalsIgnoreCase("givemidaspickaxe")){
                    player.getInventory().addItem(ItemManager.MidasPickaxe);
                }
                if(cmd.getName().equalsIgnoreCase("giveboomerang")){
                    player.getInventory().addItem(ItemManager.Boomerang);
                }
                if(cmd.getName().equalsIgnoreCase("givehomingbow")){
                    player.getInventory().addItem(ItemManager.HomingBow);
                }
                if(cmd.getName().equalsIgnoreCase("giverocketlauncher")){
                    player.getInventory().addItem(ItemManager.RocketLauncher);
                }
                if(cmd.getName().equalsIgnoreCase("givethrowingaxe")){
                    player.getInventory().addItem(ItemManager.ThrowingAxe);
                }
                if(cmd.getName().equalsIgnoreCase("giveundeadsword")){
                    player.getInventory().addItem(ItemManager.UndeadSword);
                }
                if(cmd.getName().equalsIgnoreCase("givethrowabletnt")){
                    player.getInventory().addItem(ItemManager.ThrowableTNT);
                }
                if(cmd.getName().equalsIgnoreCase("givelightningaxe")){
                    player.getInventory().addItem(ItemManager.LightningAxe);
                }
                if(cmd.getName().equalsIgnoreCase("giveautosmeltpickaxe")){
                    player.getInventory().addItem(ItemManager.AutoSmeltPickaxe);
                }
                if(cmd.getName().equalsIgnoreCase("givesmokebow")){
                    player.getInventory().addItem(ItemManager.SmokeBow);
                }
                if(cmd.getName().equalsIgnoreCase("givefireball")){
                    player.getInventory().addItem(ItemManager.Fireball);
                }
                if(cmd.getName().equalsIgnoreCase("givetripleshotbow")){
                    player.getInventory().addItem(ItemManager.TripleShotBow);
                }
                if(cmd.getName().equalsIgnoreCase("givebomberelytra")){
                    player.getInventory().addItem(ItemManager.BomberElytra);
                }
                if(cmd.getName().equalsIgnoreCase("giveautoshootchestplate")){
                    player.getInventory().addItem(ItemManager.AutoShootChestplate);
                }
                if(cmd.getName().equalsIgnoreCase("giveairstrikebow")){
                    player.getInventory().addItem(ItemManager.AirStrikeBow);
                }
                if(cmd.getName().equalsIgnoreCase("givechunkminerpickaxe")){
                    player.getInventory().addItem(ItemManager.ChunkMinerPickaxe);
                }
                if(cmd.getName().equalsIgnoreCase("giveorecompass")){
                    player.getInventory().addItem(ItemManager.OreCompass);
                }
                if(cmd.getName().equalsIgnoreCase("givezombieknightspawnegg")){
                    player.getInventory().addItem(ItemManager.ZombieKnightSpawnEgg);
                }
            }
            else {
                sender.sendMessage("You are not allowed to use this command");
            }
            return true;
        }
        else {
            sender.sendMessage("Only players can use that command");
            return true;
        }
    }
}
