package edu.hitsz.aircraft.enemy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.prop.BloodPropFactory;
import edu.hitsz.factory.prop.BombPropFactory;
import edu.hitsz.factory.prop.BulletPropFactory;
import edu.hitsz.factory.prop.PropFactory;
import edu.hitsz.prop.BaseProp;

import java.util.LinkedList;
import java.util.List;

/**
 * @author linyu
 */
public class BossAircraft extends AbstractEnemyAircraft{
    private int propNum = 3;
    private PropFactory propFactory;
    private int shootNum = 20;
    private double startAngle = 2*Math.PI/shootNum;
    private int direction = 1;
    private int power = 10;

    public BossAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*3;
        double angle = -startAngle;
        int speed = this.getSpeedY()+direction*10;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            int speedX = (int) (speed*Math.sin(angle));
            int speedY = (int) (speed*Math.cos(angle));
            bullet = new EnemyBullet(x, y, speedX, speedY, power);
            angle += startAngle;
            res.add(bullet);
        }
        return res;
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
            }else{
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
