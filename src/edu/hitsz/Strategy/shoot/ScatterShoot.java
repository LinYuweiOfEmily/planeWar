package edu.hitsz.Strategy.shoot;

import edu.hitsz.Strategy.ShootStrategy;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.hero.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.BaseProp;

import java.util.LinkedList;
import java.util.List;

/**
 * @author linyu
 */
public class ScatterShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> doShoot(AbstractAircraft abstractAircraft) {
        List<BaseBullet> res = new LinkedList<>();
        double startAngle = abstractAircraft.getStartAngle();
        double angle = -startAngle;
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + abstractAircraft.getDirection() * 3;
        int speedY = abstractAircraft.getSpeedY() + abstractAircraft.getDirection() * 5;
        BaseBullet bullet;
        int power = abstractAircraft.getPower();
        int shootNum = abstractAircraft.getShootNum();
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            int speedX = (int) (speedY * Math.tan(angle));
            if(abstractAircraft instanceof HeroAircraft){
                bullet = new HeroBullet(x , y, speedX , speedY-2, power);
            }else {
                bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX + abstractAircraft.getSpeedX(), speedY, power);
            }
            angle += startAngle;
            res.add(bullet);
        }
        return res;
    }
}
