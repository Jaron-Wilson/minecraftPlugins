package me.jaron.gamerica.plugin.afk.tasks;

import me.jaron.gamerica.plugin.afk.AFKManager;

public class MovementChecker implements Runnable {

    private final AFKManager afkManager;

    public MovementChecker(AFKManager afkManager) {
        this.afkManager = afkManager;
    }

    @Override
    public void run() {

        System.out.println("AFK Status for each player is being checked");

        afkManager.checkAllPlayersAFKStatus();

    }

}