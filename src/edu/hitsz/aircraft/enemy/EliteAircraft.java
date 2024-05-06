package edu.hitsz.aircraft.enemy;

import edu.hitsz.Strategy.ShootStrategy;
import edu.hitsz.Strategy.shoot.DirectShoot;
import edu.hitsz.Strategy.shoot.RingShoot;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.prop.*;
import edu.hitsz.prop.BaseProp;

import java.util.LinkedList;
import java.util.List;

/**
 * @author linyu
 */
public class EliteAircraft extends AbstractEnemyAircraft {



    /**
     * @param locationX 精英敌机位置x坐标
     * @param locationY 精英敌机位置y坐标
     * @param speedX 精英敌机射出的子弹的基准速度
     * @param speedY 精英敌机射出的子弹的基准速度
     * @param hp    初始生命值
     */
    public EliteAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp,score);
        this.shootNum = 1;
        this.direction = 1;
        this.power = 10;
        this.shootStrategy = new DirectShoot();
        this.propNum = 1;
    }
    @Override
    public void forward(){
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    /**
     * 击败精英敌机随机产生道具
     * @return 产生的道具
     */
    @Override
    public List<BaseProp> generateNewProp(){
        List<BaseProp> props = new LinkedList<>();
        for(int i=0;i<propNum;i++){
            double isGenProp = Math.random();
            if(isGenProp<0.3){
                propFactory = new BloodPropFactory();
            }else if(isGenProp<0.4){
                propFactory = new BombPropFactory();
            }else if(isGenProp<0.6){
                propFactory = new BulletPropFactory();
            }else if(isGenProp<0.8){
                propFactory = new BulletPlusPropFactory();
            }else {
                propFactory = null;
            }
            if(propFactory!=null){
                props.add(propFactory.createProp(
                        this.getLocationX(),
                        this.getLocationY()
                ));
            }
        }

        return props;

    }


    /**
     * 通过射击产生子弹
     * @return 产生的子弹列表
     */
    @Override
    public List<BaseBullet> shoot() {
        return super.shoot();
    }
}
