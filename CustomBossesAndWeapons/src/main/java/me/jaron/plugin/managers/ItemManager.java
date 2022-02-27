package me.jaron.plugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.jaron.plugin.customRecipies.ItemRecipeManager.*;

public class ItemManager {

    public static ItemStack GrapplngHook, TeleportSword, TheGiftingFish, ExplosiveBow, InfiniteWaterBucket,
            InfiniteLavaBucket, MachineGunBow, MultibreakPickaxe, MidasPickaxe, Boomerang, HomingBow, RocketLauncher,
            ThrowingAxe, UndeadSword, ThrowableTNT, LightningAxe, AutoSmeltPickaxe, SmokeBow, Fireball, TripleShotBow,
            BomberElytra, AutoShootChestplate, AirStrikeBow, ChunkMinerPickaxe, OreCompass, ZombieKnightSpawnEgg,
            NecromancerSpawnEgg, CoolBeamSpawnEgg, NecromancerApprenticeSpawnEgg, ReverentSpawnEgg, MiniZombieBoss, MiniSkeletonBoss
            ;

    public static void init() {
        createGrapplingHook();
        createTeleportSword();
        createTheGiftingFish();
        createExplosiveBow();
        createInfiniteWaterBucket();
        createInfiniteLavaBucket();
        createMachineGunBow();
        createMultibreakPickaxe();
        createMidasPickaxe();
        createBoomerang();
        createHomingBow();
        createRocketLauncher();
        createThrowingAxe();
        createUndeadSword();
        createThrowableTNT();
        createLightningAxe();
        createAutoSmeltPickaxe();
        createSmokeBow();
        createFireball();
        createTripleShotBow();
        createBomberElytra();
        createAutoShootChestplate();
        createAirStrikeBow();
        createChunkMinerPickaxe();
        createOreCompass();
        createZombieKnightSpawnEgg();

        createNecromancerSpawnEgg();
        createCoolBeamSpawnEgg();
        createNecromancerApprenticeSpawnEgg();
        createReverentSpawnEgg();
        createSkeletonMobSpawnEgg();
        createZombieMobSpawnEgg();
    }

    private static void createGrapplingHook() {
        ItemStack item = new ItemStack(Material.FISHING_ROD, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§9Grappling Hook");}
        List<String> lore = new ArrayList<>();
        lore.add("§9RARE");
        lore.add("§7Travel in style with this tool...");
        lore.add("Item has a 5 second cooldown.");
        assert meta != null;
        meta.setLore(lore);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        GrapplngHook = item;

        ShapedRecipe grappling_hook_recipe = new ShapedRecipe(NamespacedKey.minecraft("grappling_hook"), item);
        grappling_hook_recipe.shape("  N", " NH", "N H");
        grappling_hook_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        grappling_hook_recipe.setIngredient('N', Material.STICK);
        Bukkit.getServer().addRecipe(grappling_hook_recipe);
    }

    private static void createTeleportSword() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§5Teleport Sword");}
        List<String> lore = new ArrayList<>();
        lore.add("§5EPIC");
        lore.add("§7This sword has teleport powers.");
        lore.add("§6Item Ability (Right Click):");
        lore.add("§fTeleport 8 blocks ahead");
        assert meta != null;
        meta.setLore(lore);
        meta.setUnbreakable(true);
        AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage",
                25.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);
        AttributeModifier speed = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed",
                0.05, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, speed);
        item.setItemMeta(meta);
        TeleportSword = item;

        ShapedRecipe teleport_sword_Recipe = new ShapedRecipe(NamespacedKey.minecraft("teleport_sword"), item);
        teleport_sword_Recipe.shape("EHE", "EHE", "ENE");
        teleport_sword_Recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        teleport_sword_Recipe.setIngredient('N', Material.STICK);
        teleport_sword_Recipe.setIngredient('E', Material.ENDER_PEARL);
        Bukkit.getServer().addRecipe(teleport_sword_Recipe);
    }

    private static void createTheGiftingFish() {
        ItemStack item = new ItemStack(Material.PUFFERFISH, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§cThe Gifting Fish");}
        List<String> lore = new ArrayList<>();
        lore.add("§cSPECIAL");
        lore.add("§7Right click for an item!");
        assert meta != null;
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 3, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        TheGiftingFish = item;
    }

    private static void createExplosiveBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§5Explosive Bow");}
        List<String> lore = new ArrayList<>();
        lore.add("§5EPIC");
        lore.add("§7Creates an explosive...");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        ExplosiveBow = item;

        ShapedRecipe explosive_bow_recipe = new ShapedRecipe(NamespacedKey.minecraft("explosive_bow"), item);
        explosive_bow_recipe.shape("NHT", "NTH", "NHT");
        explosive_bow_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        explosive_bow_recipe.setIngredient('N', Material.STICK);
        explosive_bow_recipe.setIngredient('T', Material.TNT);
        Bukkit.getServer().addRecipe(explosive_bow_recipe);
    }

    private static void createInfiniteWaterBucket() {
        ItemStack item = new ItemStack(Material.WATER_BUCKET, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§5Infinite Water Bucket");}
        List<String> lore = new ArrayList<>();
        lore.add("§5EPIC");
        lore.add("§7Infinite flow of water...");
        assert meta != null;
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 3, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        InfiniteWaterBucket = item;

        ShapedRecipe infinite_water_bucket_recipe = new ShapedRecipe(NamespacedKey.minecraft("infinite_water_bucket"), item);
        infinite_water_bucket_recipe.shape("WH");
        infinite_water_bucket_recipe.setIngredient('H',new RecipeChoice.ExactChoice(HardenedDiamond));
        infinite_water_bucket_recipe.setIngredient('W',Material.WATER_BUCKET);
        Bukkit.getServer().addRecipe(infinite_water_bucket_recipe);
    }

    private static void createInfiniteLavaBucket() {
        ItemStack item = new ItemStack(Material.LAVA_BUCKET, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§5Infinite Lava Bucket");}
        List<String> lore = new ArrayList<>();
        lore.add("§5EPIC");
        lore.add("§7Infinite flow of lava...");
        assert meta != null;
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 3, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        InfiniteLavaBucket = item;

        ShapedRecipe infinite_lava_bucket_recipe = new ShapedRecipe(NamespacedKey.minecraft("infinite_lava_bucket"), item);
        infinite_lava_bucket_recipe.shape("WH");
        infinite_lava_bucket_recipe.setIngredient('W', new RecipeChoice.ExactChoice(HardenedDiamond));
        infinite_lava_bucket_recipe.setIngredient('H', Material.LAVA_BUCKET);
        Bukkit.getServer().addRecipe(infinite_lava_bucket_recipe);
    }

    private static void createMachineGunBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§9Machine Gun Bow");}
        List<String> lore = new ArrayList<>();
        lore.add("§9RARE");
        lore.add("§6Left Click:");
        lore.add("§7Shoots 5 arrows a second for 5 seconds");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        MachineGunBow = item;

        ShapedRecipe machine_gun_bow_recipe = new ShapedRecipe(NamespacedKey.minecraft("machine_gun_bow"), item);
        machine_gun_bow_recipe.shape("NHA", "NAH", "NHA");
        machine_gun_bow_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        machine_gun_bow_recipe.setIngredient('N', Material.STICK);
        machine_gun_bow_recipe.setIngredient('A', Material.ARROW);
        Bukkit.getServer().addRecipe(machine_gun_bow_recipe);
    }

    private static void createMultibreakPickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Multibreak Pickaxe");}
        List<String> lore = new ArrayList<>();
        lore.add("§6LEGENDARY");
        lore.add("§6Multibreak:");
        lore.add("§7Breaks stone in a 3x3 area");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        MultibreakPickaxe = item;

        ShapedRecipe multibreak_pickaxe_recipe = new ShapedRecipe(NamespacedKey.minecraft("multibreak_pickaxe"), item);
        multibreak_pickaxe_recipe.shape("HHH", "HNH", "HNH");
        multibreak_pickaxe_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        multibreak_pickaxe_recipe.setIngredient('N', Material.STICK);
        Bukkit.getServer().addRecipe(multibreak_pickaxe_recipe);
    }

    private static void createMidasPickaxe() {
        ItemStack item = new ItemStack(Material.GOLDEN_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Midas Pickaxe");}
        List<String> lore = new ArrayList<>();
        lore.add("§6LEGENDARY");
        lore.add("§6Golden Touch:");
        lore.add("§7Every drop will turn to gold.");
        assert meta != null;
        meta.setLore(lore);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        MidasPickaxe = item;

        ShapedRecipe midas_pickaxe_recipe = new ShapedRecipe(NamespacedKey.minecraft("midas_pickaxe"), item);
        midas_pickaxe_recipe.shape("HHH", "HNH", "HNH");
        midas_pickaxe_recipe.setIngredient('H', new RecipeChoice.ExactChoice(Refined_Gold));
        midas_pickaxe_recipe.setIngredient('N', Material.STICK);
        Bukkit.getServer().addRecipe(midas_pickaxe_recipe);
    }

    private static void createBoomerang() {
        ItemStack item = new ItemStack(Material.BONE, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Boomerang");}
        List<String> lore = new ArrayList<>();
        lore.add("§6LEGENDARY");
        lore.add("§6Boomerang Powers:");
        lore.add("§7Shoots out a boomerang");
        lore.add("§7that deals damage.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        Boomerang = item;

        ShapedRecipe boomerang_recipe = new ShapedRecipe(NamespacedKey.minecraft("boomerang"), item);
        boomerang_recipe.shape(" H ", "H H", "NNN");
        boomerang_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        boomerang_recipe.setIngredient('N', Material.STICK);
        Bukkit.getServer().addRecipe(boomerang_recipe);
    }

    private static void createHomingBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Homing Bow");}
        List<String> lore = new ArrayList<>();
        lore.add("§6LEGENDARY");
        lore.add("§6Homing Arrows:");
        lore.add("§7All arrows shot from this bow");
        lore.add("§7will turn to the target within 5 blocks.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        HomingBow = item;

        ShapedRecipe homing_bow_recipe = new ShapedRecipe(NamespacedKey.minecraft("homing_bow"), item);
        homing_bow_recipe.shape("NHI", "NIH", "NHI");
        homing_bow_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        homing_bow_recipe.setIngredient('N', Material.STICK);
        homing_bow_recipe.setIngredient('I', new RecipeChoice.ExactChoice(Refined_Iron));
        Bukkit.getServer().addRecipe(homing_bow_recipe);
    }

    private static void createRocketLauncher() {
        ItemStack item = new ItemStack(Material.GOLDEN_SHOVEL, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Rocket Launcher");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Damage: §c+70");
        lore.add("");
        lore.add("§6Item Ability: Ricochet Rocket §eRIGHT CLICK");
        lore.add("§7Fires a rocket that explodes for");
        lore.add("§c0 §7damage and when ricocheting");
        lore.add("§7off the ground creates explosions for");
        lore.add("§7half the damage.");
        lore.add("");
        assert meta != null;
        meta.setLore(lore);
        AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 70.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        RocketLauncher = item;

        ShapedRecipe rocket_launcher_recipe = new ShapedRecipe(NamespacedKey.minecraft("rocket_launcher"), item);
        rocket_launcher_recipe.shape("GHG", " N ", " N ");
        rocket_launcher_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        rocket_launcher_recipe.setIngredient('G', new RecipeChoice.ExactChoice(Refined_Gold));
        rocket_launcher_recipe.setIngredient('N', Material.STICK);
        Bukkit.getServer().addRecipe(rocket_launcher_recipe);
    }

    private static void createThrowingAxe() {
        ItemStack item = new ItemStack(Material.NETHERITE_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Throwing Axe");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Damage: §c+70");
        lore.add("");
        lore.add("§6Item Ability: Throw §eRIGHT CLICK");
        lore.add("§7Throw your axe and deal");
        lore.add("§c1,000 §7damage.");
        assert meta != null;
        meta.setLore(lore);
        AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 70.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);
        item.setItemMeta(meta);
        ThrowingAxe = item;

        ShapedRecipe throwing_axe_recipe = new ShapedRecipe(NamespacedKey.minecraft("throwing_axe"), item);
        throwing_axe_recipe.shape(" II", " SI", " S ");
        throwing_axe_recipe.setIngredient('I', new RecipeChoice.ExactChoice(Refined_Iron));
        throwing_axe_recipe.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(throwing_axe_recipe);
    }

    private static void createUndeadSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Undead Sword");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Damage: §c+70");
        lore.add("");
        lore.add("§6Item Ability: Damage Multiplier");
        lore.add("§7Damage dealt to some mobs will be multiplied.");
        lore.add("§7Zombies: ");
        lore.add("§7Skeletons: ");
        lore.add("");
        assert meta != null;
        meta.setLore(lore);
        AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 70.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        UndeadSword = item;

        ShapedRecipe undead_sword_recipe = new ShapedRecipe(NamespacedKey.minecraft("undead_sword"), item);
        undead_sword_recipe.shape("KHK", "KHK", "KNK");
        undead_sword_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        undead_sword_recipe.setIngredient('N', Material.STICK);
        undead_sword_recipe.setIngredient('K', Material.SKELETON_SKULL);
            Bukkit.getServer().addRecipe(undead_sword_recipe);
    }

    private static void createThrowableTNT() {
        ItemStack item = new ItemStack(Material.TNT, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Throwable TNT");}
        List<String> lore = new ArrayList<>();
        lore.add("§6Item Ability: Throwable TNT");
        lore.add("§7Throws TNT......");
        assert meta != null;
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        ThrowableTNT = item;

        ShapedRecipe throw_tnt_recipe = new ShapedRecipe(NamespacedKey.minecraft("throw_tnt"), item);
        throw_tnt_recipe.shape("NNN", "NHN", "NNN");
        throw_tnt_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        throw_tnt_recipe.setIngredient('N', Material.TNT);
        Bukkit.getServer().addRecipe(throw_tnt_recipe);
    }

    private static void createLightningAxe() {
        ItemStack item = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Lightning Axe");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Strike your opponent with");
        lore.add("§7lightning everytime you hit them,");
        lore.add("§7dealing §c1000 §7damage.");
        lore.add("");
        lore.add("§6Item Ability: Stormbreaker");
        lore.add("§7When right clicked, strike all");
        lore.add("§7mobs around you in a 12 block radius");
        lore.add("§7dealing §c1000 §7damage.");
        assert meta != null;
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        LightningAxe = item;

        ShapedRecipe lightning_axe_recipe = new ShapedRecipe(NamespacedKey.minecraft("lightning_axe"), item);
        lightning_axe_recipe.shape("RII", "RSI", "RSR");
        lightning_axe_recipe.setIngredient('I', new RecipeChoice.ExactChoice(Refined_Iron));
        lightning_axe_recipe.setIngredient('R', new RecipeChoice.ExactChoice(RefinedBlock));
        lightning_axe_recipe.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(lightning_axe_recipe);
    }

    private static void createAutoSmeltPickaxe() {
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6AutoSmelt Pickaxe");}
        List<String> lore = new ArrayList<>();
        lore.add("§6Item Ability: AutoSmelt");
        lore.add("§7Automatically smelt block drops.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        AutoSmeltPickaxe = item;

        ShapedRecipe autosmelt_pickaxe_recipe = new ShapedRecipe(NamespacedKey.minecraft("autosmelt_pickaxe"), item);
        autosmelt_pickaxe_recipe.shape("HHH", "FNF", "BNB");
        autosmelt_pickaxe_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        autosmelt_pickaxe_recipe.setIngredient('N', Material.STICK);
        autosmelt_pickaxe_recipe.setIngredient('B', Material.BLAST_FURNACE);
        autosmelt_pickaxe_recipe.setIngredient('F', Material.FURNACE);
        Bukkit.getServer().addRecipe(autosmelt_pickaxe_recipe);
    }

    private static void createSmokeBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Smoke Bow");}
        List<String> lore = new ArrayList<>();
        lore.add("§6Item Ability: Wither Smoke");
        lore.add("§7Creates a smoke bomb at the");
        lore.add("§7location where the arrow landed,");
        lore.add("§7and gives the wither effect to");
        lore.add("§7all mobs in a 4 block radius.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        SmokeBow = item;

        ShapedRecipe smoke_bow_recipe = new ShapedRecipe(NamespacedKey.minecraft("smoke_bow"), item);
        smoke_bow_recipe.shape("NHA", "NAH", "NHA");
        smoke_bow_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamond));
        smoke_bow_recipe.setIngredient('N', Material.STICK);
        smoke_bow_recipe.setIngredient('A', new RecipeChoice.ExactChoice(Refined_Quarts));
        Bukkit.getServer().addRecipe(smoke_bow_recipe);
    }

    private static void createFireball() {
        ItemStack item = new ItemStack(Material.FIRE_CHARGE, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Fireball");}
        List<String> lore = new ArrayList<>();
        lore.add("§6Item Ability: Fireball");
        lore.add("§7Launches a fireball.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        Fireball = item;

        ShapedRecipe fly_fireball_recipe = new ShapedRecipe(NamespacedKey.minecraft("fly_fireball"), item);
        fly_fireball_recipe.shape("WH");
        fly_fireball_recipe.setIngredient('W',Material.FIRE_CHARGE);
        fly_fireball_recipe.setIngredient('H',Material.ARROW);
        Bukkit.getServer().addRecipe(fly_fireball_recipe);
    }

    private static void createTripleShotBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Triple Shot Bow");}
        List<String> lore = new ArrayList<>();
        lore.add("§6Item Ability: Three Shot");
        lore.add("§7Shoots three arrows instead of 1.");
        lore.add("§7The other 2 arrows deal half the damage");
        lore.add("§7but a lot of knockback.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        TripleShotBow = item;

        ShapedRecipe triple_shot_bow_recipe = new ShapedRecipe(NamespacedKey.minecraft("triple_shot_bow"), item);
        triple_shot_bow_recipe.shape("NHA", "NAH", "NHA");
        triple_shot_bow_recipe.setIngredient('H', new RecipeChoice.ExactChoice(HardenedDiamondBlock));
        triple_shot_bow_recipe.setIngredient('N', Material.STICK);
        triple_shot_bow_recipe.setIngredient('A', Material.ARROW);
        Bukkit.getServer().addRecipe(triple_shot_bow_recipe);
    }

    private static void createBomberElytra() {
        ItemStack item = new ItemStack(Material.ELYTRA, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Bomber Elytra");}
        List<String> lore = new ArrayList<>();
        lore.add("§6Item Ability: Bomber");
        lore.add("§7Bomb everything below you when");
        lore.add("§7you are gliding using this elytra.");
        lore.add("");
        lore.add("§eBomber mode: On");
        lore.add("§eSneak to toggle mode.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        BomberElytra = item;

        ShapelessRecipe elytra_bomber_recipe = new ShapelessRecipe(NamespacedKey.minecraft("elytra_bomber"), item);
        elytra_bomber_recipe.addIngredient(Material.ELYTRA);
        elytra_bomber_recipe.addIngredient(Material.TNT);
        Bukkit.getServer().addRecipe(elytra_bomber_recipe);
    }

    private static void createAutoShootChestplate() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Auto Shoot Chestplate");}
        List<String> lore = new ArrayList<>();
        lore.add("§6Item Ability: ArroNT");
        lore.add("§7Every 2 seconds shoots either an");
        lore.add("§7arrow or TNT to all the mobs");
        lore.add("§7within a 10 block radius.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        AutoShootChestplate = item;

        ShapelessRecipe auto_shoot_chestplate_recipe = new ShapelessRecipe(NamespacedKey.minecraft("auto_shoot_chestplate"), item);
        auto_shoot_chestplate_recipe.addIngredient(Material.IRON_CHESTPLATE);
        auto_shoot_chestplate_recipe.addIngredient(Material.ARROW);
        auto_shoot_chestplate_recipe.addIngredient(Material.TNT);
        Bukkit.getServer().addRecipe(auto_shoot_chestplate_recipe);
    }

    private static void createAirStrikeBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Air Strike Bow");}
        List<String> lore = new ArrayList<>();
        lore.add("§6Item Ability: Air Strike");
        lore.add("§7Calls a decent air strike");
        lore.add("§7where the arrow lands.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        AirStrikeBow = item;

        ShapedRecipe air_strike_bow_recipe = new ShapedRecipe(NamespacedKey.minecraft("air_strike_bow"), item);
        air_strike_bow_recipe.shape("TTT", "TbT", "TTT");
        air_strike_bow_recipe.setIngredient('b', new RecipeChoice.ExactChoice(ExplosiveBow));
        air_strike_bow_recipe.setIngredient('T', Material.TNT);
        Bukkit.getServer().addRecipe(air_strike_bow_recipe);
    }

    private static void createChunkMinerPickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Chunk Miner Pickaxe");}
        List<String> lore = new ArrayList<>();
        lore.add("§6Item Ability: Chunk Mine");
        lore.add("§7Mine an entire chunk up, teleporting");
        lore.add("§7the resources into your inventory.");
        lore.add("");
        lore.add("§eDiamonds only mode: On");
        lore.add("§eRight click to toggle mode.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        ChunkMinerPickaxe = item;

        ShapedRecipe chunk_miner_recipe = new ShapedRecipe(NamespacedKey.minecraft("chunk_miner"), item);
        chunk_miner_recipe.shape("DTP");
        chunk_miner_recipe.setIngredient('D',Material.DIAMOND);
        chunk_miner_recipe.setIngredient('T',Material.TNT);
        chunk_miner_recipe.setIngredient('P',new RecipeChoice.ExactChoice(MultibreakPickaxe));
        Bukkit.getServer().addRecipe(chunk_miner_recipe);
    }

    private static void createOreCompass() {
        ItemStack item = new ItemStack(Material.COMPASS, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Ore Compass");}
        List<String> lore = new ArrayList<>();
        lore.add("§6Item Ability: Oreker");
        lore.add("§7Points to the nearest ore");
        lore.add("§7until the ore is broken.");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        OreCompass = item;

        ShapedRecipe ore_compass_recipe = new ShapedRecipe(NamespacedKey.minecraft("ore_compass"), item);
        ore_compass_recipe.shape("CD");
        ore_compass_recipe.setIngredient('C',Material.COMPASS);
        ore_compass_recipe.setIngredient('D', new RecipeChoice.ExactChoice(HardenedDiamondBlock));
        Bukkit.getServer().addRecipe(ore_compass_recipe);
    }

    private static void createZombieKnightSpawnEgg() {
        ItemStack item = new ItemStack(Material.ZOMBIE_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Zombie Knight Spawn Egg");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Spawns in a Zombie Knight");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        ZombieKnightSpawnEgg = item;

        ShapelessRecipe zombie_knight_recipe = new ShapelessRecipe(NamespacedKey.minecraft("zombie_knight"), item);
        zombie_knight_recipe.addIngredient(Material.ZOMBIE_HEAD);
        zombie_knight_recipe.addIngredient(Material.EGG);
        Bukkit.getServer().addRecipe(zombie_knight_recipe);
    }


    private static void createNecromancerSpawnEgg() {
        ItemStack item = new ItemStack(Material.ZOMBIE_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Necromancer Spawn Egg");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Spawns in a Necromancer");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        NecromancerSpawnEgg = item;

        ShapedRecipe necromancer_recipe = new ShapedRecipe(NamespacedKey.minecraft("necromancer"), item);
        necromancer_recipe.shape("HE ");
        necromancer_recipe.setIngredient('H', Material.WITHER_SKELETON_SKULL);
        necromancer_recipe.setIngredient('E',Material.EGG);
        Bukkit.getServer().addRecipe(necromancer_recipe);
    }

    private static void createNecromancerApprenticeSpawnEgg() {
        ItemStack item = new ItemStack(Material.ZOMBIE_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Necromancer's Apprentice Spawn Egg");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Spawns in a Necromancer's Apprentice");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        NecromancerApprenticeSpawnEgg = item;

        ShapedRecipe necromancer_recipe = new ShapedRecipe(NamespacedKey.minecraft("necromancer_apprentice"), item);
        necromancer_recipe.shape("HEH");
        necromancer_recipe.setIngredient('H', new RecipeChoice.ExactChoice(NecromancerSpawnEgg));
        necromancer_recipe.setIngredient('E',Material.EGG);
        Bukkit.getServer().addRecipe(necromancer_recipe);
    }

    private static void createCoolBeamSpawnEgg() {
        ItemStack item = new ItemStack(Material.HUSK_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6CoolBeam Spawn Egg");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Spawns in a CoolBeam Boss");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        CoolBeamSpawnEgg = item;

        ShapedRecipe necromancer_recipe = new ShapedRecipe(NamespacedKey.minecraft("coolbeam"), item);
        necromancer_recipe.shape("HEH");
        necromancer_recipe.setIngredient('H', new RecipeChoice.ExactChoice(NecromancerSpawnEgg));
        necromancer_recipe.setIngredient('E',Material.EGG);
        Bukkit.getServer().addRecipe(necromancer_recipe);
    }

    private static void createReverentSpawnEgg() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Reverent Spawn Stick");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Spawns in a Reverent");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        ReverentSpawnEgg = item;

        ShapedRecipe necromancer_recipe = new ShapedRecipe(NamespacedKey.minecraft("reverent"), item);
        necromancer_recipe.shape("HER");
        necromancer_recipe.setIngredient('H', Material.ZOMBIE_HEAD);
        necromancer_recipe.setIngredient('E',Material.STICK);
        necromancer_recipe.setIngredient('R',Material.ROTTEN_FLESH);
        Bukkit.getServer().addRecipe(necromancer_recipe);
    }

    private static void createSkeletonMobSpawnEgg() {
        ItemStack item = new ItemStack(Material.BEE_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§6Skeleton Spawn Egg");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Spawns in a Skeleton");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        MiniSkeletonBoss = item;

        ShapedRecipe necromancer_recipe = new ShapedRecipe(NamespacedKey.minecraft("skeleton_boss"), item);
        necromancer_recipe.shape("HER");
        necromancer_recipe.setIngredient('H', Material.SKELETON_SKULL);
        necromancer_recipe.setIngredient('E',Material.EGG);
        necromancer_recipe.setIngredient('R',Material.BONE);
        Bukkit.getServer().addRecipe(necromancer_recipe);
    }

    private static void createZombieMobSpawnEgg() {
        ItemStack item = new ItemStack(Material.HUSK_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){meta.setDisplayName("§9Zombie Boss Spawn Egg");}
        List<String> lore = new ArrayList<>();
        lore.add("§7Spawns in a Zombie Boss");
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        MiniZombieBoss = item;

        ShapedRecipe necromancer_recipe = new ShapedRecipe(NamespacedKey.minecraft("zombie_boss"), item);
        necromancer_recipe.shape("ER");
        necromancer_recipe.setIngredient('E',Material.EGG);
        necromancer_recipe.setIngredient('R',Material.ROTTEN_FLESH);
        Bukkit.getServer().addRecipe(necromancer_recipe);
    }
}
