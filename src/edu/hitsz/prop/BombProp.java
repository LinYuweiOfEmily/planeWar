package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.enemy.*;

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


    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    public int[] bomb(List<AbstractEnemyAircraft> enemyAircrafts, ListIterator<BaseProp> iterator){
        int score = 0;
        int isBoss = 0;
        for (AbstractEnemyAircraft aircraft : enemyAircrafts) {
            if (aircraft.notValid()) {
                continue;
            }
            aircraft.decreaseHp(this.power);
            score += aircraft.getScore();
            for(BaseProp a:aircraft.generateNewProp()){
                iterator.add(a);
            }
            if(aircraft instanceof BossAircraft bossAircraft){
                isBoss = 1;
            }
            System.out.println("BombSupply active");
        }
        return new int[]{score,isBoss};
    }
}
