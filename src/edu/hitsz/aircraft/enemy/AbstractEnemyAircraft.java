package edu.hitsz.aircraft.enemy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.prop.BaseProp;

/**
 * @author linyu
 */
public abstract class AbstractEnemyAircraft extends AbstractAircraft {
    protected int score;

    public int getScore() {
        return score;
    }

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp);
        this.score = score;
    }
    public abstract BaseProp generateNewProp();
}
