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
public class RingShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> doShoot(AbstractAircraft abstractAircraft) {
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + abstractAircraft.getDirection()*2;
        double startAngle = -abstractAircraft.getStartAngle();
        double angle = -startAngle;
        int speed = abstractAircraft.getSpeedY()+abstractAircraft.getDirection()*10;
        List<BaseBullet> res = new LinkedList<>();
        int power = abstractAircraft.getPower();
        int shootNum = abstractAircraft.getShootNum();
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            int speedX = (int) (speed*Math.sin(angle));
            int speedY = (int) (speed*Math.cos(angle));
            if(abstractAircraft instanceof  HeroAircraft){
                bullet = new HeroBullet(x,y, speedX, speedY, power);
            }else{
                bullet = new EnemyBullet(x,y,speedX,speedY,power);
            }
            angle += startAngle;
            res.add(bullet);
        }
        return res;
    }
}
