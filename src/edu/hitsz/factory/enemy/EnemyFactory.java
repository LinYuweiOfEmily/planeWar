package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;

/**
 * @author linyu
 */
public interface EnemyFactory {
    AbstractEnemyAircraft createEnemyAircraft();
}
