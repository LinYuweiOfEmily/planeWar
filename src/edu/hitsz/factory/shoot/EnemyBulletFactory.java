package edu.hitsz.factory.shoot;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

/**
 * @author linyu
 */
public class EnemyBulletFactory implements ShootFactory{
    @Override
    public BaseBullet createBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        return new EnemyBullet(locationX, locationY, speedX, speedY, power);
    }
}
