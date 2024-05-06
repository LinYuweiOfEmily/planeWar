package edu.hitsz.aircraft.enemy;

import edu.hitsz.Strategy.ShootStrategy;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemyAircraft {


    /**
     * @param locationX 普通敌机位置x坐标
     * @param locationY 普通敌机位置y坐标
     * @param speedX 普通敌机射出的子弹的基准速度
     * @param speedY 普通敌机射出的子弹的基准速度
     * @param hp    初始生命值
     */
    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp,score);
    }

    @Override
    public List<BaseProp> generateNewProp() {
        return new LinkedList<>();
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

}
