package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.aircraft.enemy.EliteAircraft;
import edu.hitsz.aircraft.enemy.MobEnemy;

import java.util.List;
import java.util.ListIterator;

/**
 * @author linyu
 */
public class BombProp extends BaseProp{
    /**
     * 爆炸道具的威力
     */
    private int power = 300;

    public int getPower() {
        return power;
    }

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    public int bomb(List<AbstractEnemyAircraft> enemyAircrafts, ListIterator<BaseProp> iterator){
        int score = 0;
        for (AbstractAircraft aircraft : enemyAircrafts) {
            if (aircraft.notValid()) {
                continue;
            }
            aircraft.decreaseHp(this.power);
            if (aircraft instanceof MobEnemy mobEnemy) {
                score += mobEnemy.getScore();
            } else if(aircraft instanceof EliteAircraft eliteAircraft){
                score += eliteAircraft.getScore();
                BaseProp prop1 = eliteAircraft.generateNewProp();
                if(prop1!=null){
                    iterator.add(prop1);
                }
            }
            System.out.println("BombSupply active");
        }
        return score;
    }
}
