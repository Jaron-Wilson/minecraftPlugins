package me.jaron.plugin.custom.recipies;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRecipeManager {

    public static ItemStack HardenedDiamond, HardenedDiamondBlock, RefinedBlock, Refined_Iron, Refined_Gold, Refined_Quarts;


    public static void init() {
        createHardenedDiamond();
        createHardenedDiamondBlock();
        createRefinedBlock();
        createRefinedIron();
        createRefinedGold();
        createRefinedQuarts();
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

    private static void createRefinedBlock() {
        ItemStack item = new ItemStack(Material.QUARTZ_BLOCK, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§4Refined Block");
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        RefinedBlock = item;

//        BlastingRecipe RefinedBlockSmelt = new BlastingRecipe(NamespacedKey.minecraft("refined_block_smelt"), item,
//                new RecipeChoice.ExactChoice(Refined_Iron), 0.0f, 10 * 200);
//        Bukkit.getServer().addRecipe(RefinedBlockSmelt);

        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("refined_block"), item);
        shapedRecipe.shape("HHH", "HNH", "HHH");
        shapedRecipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        shapedRecipe.setIngredient('N', new RecipeChoice.ExactChoice(HardenedDiamondBlock));
        Bukkit.getServer().addRecipe(shapedRecipe);
    }

    private static void createRefinedIron() {
        ItemStack item = new ItemStack(Material.IRON_INGOT, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§4Refined Iron");
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        Refined_Iron = item;

        FurnaceRecipe Refined_Iron_smelt = new FurnaceRecipe(NamespacedKey.minecraft("refined_iron_smelt"), item,
                Material.IRON_INGOT, 1.0f, 10 * 2);
        Bukkit.getServer().addRecipe(Refined_Iron_smelt);
    }

    private static void createRefinedGold() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§4Refined Gold");
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        Refined_Gold = item;

        BlastingRecipe RefinedGoldBlockSmelt = new BlastingRecipe(NamespacedKey.minecraft("refined_gold_smelt"), item,
                Material.GOLD_INGOT, 0.0f, 10 * 20);
        Bukkit.getServer().addRecipe(RefinedGoldBlockSmelt);
    }

    private static void createRefinedQuarts() {
        ItemStack item = new ItemStack(Material.QUARTZ, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§4Refined Quartz");
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        Refined_Quarts = item;

        BlastingRecipe RefinedQuartzBlockSmelt = new BlastingRecipe(NamespacedKey.minecraft("refined_quartz_smelt"), item,
                Material.QUARTZ, 0.0f, 10 * 200);
        Bukkit.getServer().addRecipe(RefinedQuartzBlockSmelt);
    }

}
