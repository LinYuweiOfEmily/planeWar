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
public class DirectShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> doShoot(AbstractAircraft abstractAircraft) {
        LinkedList<BaseBullet> res = new LinkedList<>();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + abstractAircraft.getDirection()*2;
        int speedX = 0;
        int speedY = abstractAircraft.getSpeedY() + abstractAircraft.getDirection()*8;
        int shootNum = abstractAircraft.getShootNum();
        int power = abstractAircraft.getPower();
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            BaseBullet bullet;
            if(abstractAircraft instanceof HeroAircraft){
                bullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            }else {
                bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY-3, power);
            }
            res.add(bullet);
        }
        return res;
    }
}
