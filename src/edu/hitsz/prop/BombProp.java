package edu.hitsz.prop;

public class BombProp extends BaseProp{
    private int power = 300;

    public int getPower() {
        return power;
    }

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
}
