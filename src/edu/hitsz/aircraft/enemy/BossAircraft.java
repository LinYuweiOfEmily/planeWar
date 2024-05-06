package edu.hitsz.aircraft.enemy;

import edu.hitsz.Strategy.ShootStrategy;
import edu.hitsz.Strategy.shoot.RingShoot;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.prop.*;
import edu.hitsz.prop.BaseProp;

import java.util.LinkedList;
import java.util.List;

/**
 * @author linyu
 */
public class BossAircraft extends AbstractEnemyAircraft{

    public BossAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
        this.shootNum = 20;
        this.startAngle = 2*Math.PI/shootNum;
        this.direction = 1;
        this.power = 10;
        this.shootStrategy = new RingShoot();
        this.propNum = 3;
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
