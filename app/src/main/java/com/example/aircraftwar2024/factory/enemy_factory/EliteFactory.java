package com.example.aircraftwar2024.factory.enemy_factory;


import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.aircraft.AbstractEnemyAircraft;
import com.example.aircraftwar2024.aircraft.EliteEnemy;
import com.example.aircraftwar2024.shoot.DirectShoot;

public class EliteFactory implements EnemyFactory {

    private final int eliteHp = 60;
    private final int speedY = 5;

    @Override
    public AbstractEnemyAircraft createEnemyAircraft(double level) {
        EliteEnemy elite = new EliteEnemy(
                (int) ( Math.random() * (GameActivity.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * GameActivity.screenHeight * 0.05),
                (int) ((Math.random()-0.5)*20),
                (int) (this.speedY * level),
                (int) (this.eliteHp * level));
        elite.setShootStrategy(new DirectShoot());
        return elite;
    }

}
