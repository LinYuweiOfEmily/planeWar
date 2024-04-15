package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.EliteAircraft;

/**
 * @author linyu
 */
public class EliteFactory implements EnemyFactory {


    @Override
    public AbstractEnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        return new EliteAircraft(locationX,locationY,speedX,speedY,hp,score);
    }
}
