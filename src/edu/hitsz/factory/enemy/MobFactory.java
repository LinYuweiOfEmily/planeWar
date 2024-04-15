package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.MobEnemy;

/**
 * @author linyu
 */
public class MobFactory implements EnemyFactory {
    @Override
    public AbstractEnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        return new MobEnemy(locationX,locationY,speedX,speedY,hp,score);
    }
}
