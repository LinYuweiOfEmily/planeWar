package edu.hitsz.prop;

import edu.hitsz.Strategy.shoot.RingShoot;
import edu.hitsz.aircraft.hero.HeroAircraft;

/**
 * @author linyu
 */
public class BulletPlusProp extends BaseProp{

    public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    public void ringShoot(HeroAircraft heroAircraft){
        heroAircraft.setShootNum(20);
        heroAircraft.setStartAngle(2*Math.PI/20);
        heroAircraft.setShootStrategy(new RingShoot());
    }
}
