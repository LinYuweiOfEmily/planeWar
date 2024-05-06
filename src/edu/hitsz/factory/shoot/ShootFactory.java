package edu.hitsz.factory.shoot;

import edu.hitsz.bullet.BaseBullet;

/**
 * @author linyu
 */
public interface ShootFactory {
    BaseBullet createBullet(int locationX, int locationY, int speedX, int speedY, int power);

}
