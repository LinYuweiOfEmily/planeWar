package edu.hitsz.factory.prop;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BloodProp;

/**
 * @author linyu
 */
public class BloodPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BloodProp(locationX, locationY, speedX, speedY);
    }
}
