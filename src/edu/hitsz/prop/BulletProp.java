package edu.hitsz.prop;

import edu.hitsz.aircraft.hero.HeroAircraft;

/**
 * @author linyu
 */
public class BulletProp extends BaseProp{
    public BulletProp() {
    }

    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    public void addShootNum(HeroAircraft heroAircraft){
        heroAircraft.increaseShootNum();
        System.out.println("FireSupply active");
    }
}
