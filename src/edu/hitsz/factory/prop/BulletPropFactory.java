package edu.hitsz.factory.prop;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BulletProp;

/**
 * @author linyu
 */
public class BulletPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BulletProp(locationX,locationY,speedX,speedY);
    }
}
