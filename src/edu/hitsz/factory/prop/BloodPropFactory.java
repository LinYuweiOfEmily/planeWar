package edu.hitsz.factory.prop;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BloodProp;

/**
 * @author linyu
 */
public class BloodPropFactory implements PropFactory {
    private int speedX = 0;
    private int speedY = 6;
    @Override
    public BaseProp createProp(int locationX, int locationY) {
        return new BloodProp(locationX, locationY, speedX, speedY);
    }
}
