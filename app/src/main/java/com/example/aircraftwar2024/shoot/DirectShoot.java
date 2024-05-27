package com.example.aircraftwar2024.shoot;

import com.example.aircraftwar2024.aircraft.AbstractAircraft;
import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.bullet.AbstractBullet;
import com.example.aircraftwar2024.bullet.EnemyBullet;
import com.example.aircraftwar2024.bullet.HeroBullet;
import java.util.LinkedList;
import java.util.List;

/**
 * 直线射击策略
 * 子弹与飞机同向沿直线飞行
 *
 * @author hitsz
 */
public class DirectShoot implements ShootStrategy {

    @Override
    public List<AbstractBullet> shootWithStrategy(AbstractAircraft abstractAircraft) {
        List<AbstractBullet> res = new LinkedList<>();

        int num = abstractAircraft.getShootNum();
        int power = abstractAircraft.getPower();
        int direction = abstractAircraft.getDirection();
        double rate = abstractAircraft.getRate();

        boolean isHero = abstractAircraft instanceof HeroAircraft;

        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + abstractAircraft.getHeight() * direction / 2;
        int speedX = 0;
        int speedY = (int) (abstractAircraft.getSpeedY() * rate + direction * 4);

        AbstractBullet bullet;
        for (int i = 0; i < num; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if (isHero) {
                bullet = new HeroBullet(x + (i * 2 - num + 1) * 25, y, speedX, speedY, power);
            } else {
                bullet = new EnemyBullet(x + (i * 2 - num + 1) * 25, y, speedX, speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}
