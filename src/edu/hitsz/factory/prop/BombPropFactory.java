package edu.hitsz.factory.prop;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;

/**
 * @author linyu
 */
public class BombPropFactory implements PropFactory {

    private int speedX = 0;
    private int speedY = 6;
    @Override
    public BaseProp createProp(int locationX, int locationY) {
        return new BombProp(locationX, locationY, speedX, speedY);
    }
}
