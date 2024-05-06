package edu.hitsz.prop;

import edu.hitsz.aircraft.hero.HeroAircraft;

/**
 * @author linyu
 */
public class BloodProp extends BaseProp{

    /**
     * 加血道具的回血量
     */
    private int blood = 20;


    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void addBlood(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(this.blood);
    }


}
