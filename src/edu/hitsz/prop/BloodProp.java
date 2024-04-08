package edu.hitsz.prop;

/**
 * @author linyu
 */
public class BloodProp extends BaseProp{

    /**
     * 加血道具的回血量
     */
    private int blood = 20;

    public int getBlood() {
        return blood;
    }

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
}
