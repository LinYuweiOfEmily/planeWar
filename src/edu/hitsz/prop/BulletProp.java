package edu.hitsz.prop;

import edu.hitsz.Strategy.shoot.ScatterShoot;
import edu.hitsz.aircraft.hero.HeroAircraft;

/**
 * @author linyu
 */
public class BulletProp extends BaseProp{

    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    public void scatterShoot(HeroAircraft heroAircraft){
        heroAircraft.setShootNum(3);
        heroAircraft.setStartAngle(Math.PI / 12);
        heroAircraft.setShootStrategy(new ScatterShoot());
    }
}
