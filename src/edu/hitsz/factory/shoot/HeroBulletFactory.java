package edu.hitsz.factory.shoot;

import edu.hitsz.Strategy.ShootStrategy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

/**
 * @author linyu
 */
public class HeroBulletFactory implements ShootFactory {
    @Override
    public BaseBullet createBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        return new HeroBullet(locationX, locationY, speedX, speedY, power);
    }
}
