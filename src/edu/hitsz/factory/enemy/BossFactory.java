package edu.hitsz.factory.enemy;

import edu.hitsz.Strategy.ShootStrategy;
import edu.hitsz.Strategy.shoot.RingShoot;
import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.BossAircraft;
import edu.hitsz.aircraft.enemy.ElitePlus;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * @author linyu
 */
public class BossFactory implements EnemyFactory {

    private int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()));
    private int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
    private int speedX = 5;
    private int speedY = 0;
    private int hp = 500;
    private int score = 300;
    @Override
    public AbstractEnemyAircraft createEnemyAircraft() {
        return new BossAircraft(locationX,locationY,speedX,speedY,hp,score);
    }
}
