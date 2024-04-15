package edu.hitsz.factory.prop;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;

/**
 * @author linyu
 */
public class BombPropFactory implements PropFactory {

    @Override
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BombProp(locationX, locationY, speedX, speedY);
    }
}
