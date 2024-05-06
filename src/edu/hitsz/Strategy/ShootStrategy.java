package edu.hitsz.Strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;

import java.util.List;

/**
 * @author linyu
 */
public interface ShootStrategy {
    List<BaseBullet> doShoot(AbstractAircraft abstractAircraft);
}
