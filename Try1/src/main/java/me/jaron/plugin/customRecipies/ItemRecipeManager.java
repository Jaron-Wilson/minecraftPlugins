package me.jaron.plugin.customRecipies;

import me.jaron.plugin.itemEvents.GrapplingHookFiles.GrapplingHook;
import me.jaron.plugin.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRecipeManager {

    public static ItemStack HardenedDiamond;
    public static ItemStack HardenedDiamondBlock;

    public static void init() {
        createHardenedDiamond();
        createHardenedDiamondBlock();
    }

    private static void createHardenedDiamond() {
        ItemStack item = new ItemStack(Material.DIAMOND, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6Hardened Diamond");
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        HardenedDiamond = item;

        FurnaceRecipe smelt = new FurnaceRecipe(NamespacedKey.minecraft("hardened_diamond_smelt"), item,
                Material.DIAMOND, 0.0f, 10 * 20);
        Bukkit.getServer().addRecipe(smelt);

        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(NamespacedKey.minecraft("hardened_diamond"), item);
        shapelessRecipe.addIngredient(4, Material.DIAMOND);
        Bukkit.getServer().addRecipe(shapelessRecipe);

    }

    private static void createHardenedDiamondBlock() {
        ItemStack item = new ItemStack(Material.DIAMOND_BLOCK, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§4Hardened Diamond Block");
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        HardenedDiamondBlock = item;

        BlastingRecipe HardenedDiamondBlockSmelt = new BlastingRecipe(NamespacedKey.minecraft("hardened_diamond_block_smelt"), item,
                new RecipeChoice.ExactChoice(HardenedDiamond), 0.0f, 10 * 200);
        Bukkit.getServer().addRecipe(HardenedDiamondBlockSmelt);

        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("hardened_diamond_block"), item);
        shapedRecipe.shape("HHH", "HNH", "HHH");
        shapedRecipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        shapedRecipe.setIngredient('N', Material.NETHERITE_BLOCK);
        Bukkit.getServer().addRecipe(shapedRecipe);
    }

}
