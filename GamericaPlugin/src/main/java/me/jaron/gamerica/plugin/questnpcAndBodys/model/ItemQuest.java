package me.jaron.gamerica.plugin.questnpcAndBodys.model;

import org.bukkit.Material;

public class ItemQuest extends Quest {

    private Material itemType; // The type of item to be collected
    private int itemAmount; // The amount of items to be collected
    //progress
    private int progress; //the amount of targets collected

    public ItemQuest(String name, String description, double reward, Material itemType, int itemAmount) {
        super(name, description, reward);
        this.itemType = itemType;
        this.itemAmount = itemAmount;
    }

    public Material getItemType() {
        return itemType;
    }
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    public void setItemType(Material itemType) {
        this.itemType = itemType;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }
}
