package edu.hitsz.aircraft.enemy;

import edu.hitsz.Strategy.ShootStrategy;
import edu.hitsz.Strategy.shoot.RingShoot;
import edu.hitsz.Strategy.shoot.ScatterShoot;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.prop.*;
import edu.hitsz.prop.BaseProp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author linyu
 */
public class ElitePlus extends AbstractEnemyAircraft {

    public ElitePlus(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
        this.startAngle = Math.PI / 12;
        this.shootNum = 3;
        this.direction = 1;
        this.power = 10;
        this.shootStrategy = new ScatterShoot();
        this.propNum = 1;
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return super.shoot();
    }

    @Override
    public List<BaseProp> generateNewProp() {
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
}