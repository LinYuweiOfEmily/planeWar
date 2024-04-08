package edu.hitsz.prop;

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
}
