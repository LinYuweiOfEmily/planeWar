package edu.hitsz.aircraft.enemy;

import edu.hitsz.application.Main;
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
public class ElitePlus extends AbstractEnemyAircraft {
    private int direction = 1;
    private int shootNum = 3;
    private int power = 10;
    private final double startAngle = Math.PI / 12;
    private PropFactory propFactory;
    private int propNum = 1;

    public ElitePlus(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
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
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 3;
        int speedY = this.getSpeedY() + direction * 5;
        double angle = -startAngle;
        BaseBullet bullet;

        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            int speedX = (int) (speedY * Math.tan(angle));
            bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX + this.getSpeedX(), speedY, power);
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