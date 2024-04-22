package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.EliteAircraft;
import edu.hitsz.aircraft.enemy.ElitePlus;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * @author linyu
 */
public class ElitePlusFactory implements EnemyFactory {

    private int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()));
    private int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
    private int speedX = 2;
    private int speedY = 5;
    private int hp = 90;
    private int score = 200;
    @Override
    public AbstractEnemyAircraft createEnemyAircraft() {
        return new ElitePlus(locationX,locationY,speedX,speedY,hp,score);
    }
}
