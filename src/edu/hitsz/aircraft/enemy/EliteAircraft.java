package edu.hitsz.aircraft.enemy;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.prop.PropFactory;
import edu.hitsz.factory.prop.BloodPropFactory;
import edu.hitsz.factory.prop.BombPropFactory;
import edu.hitsz.factory.prop.BulletPropFactory;
import edu.hitsz.prop.BaseProp;

import java.util.LinkedList;
import java.util.List;

/**
 * @author linyu
 */
public class EliteAircraft extends AbstractEnemyAircraft {
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 击败精英敌机得到的分数
     */
    private PropFactory propFactory;


    /**
     * 子弹伤害
     */
    private int power = 10;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;
    private int propNum = 1;

    /**
     * @param locationX 精英敌机位置x坐标
     * @param locationY 精英敌机位置y坐标
     * @param speedX 精英敌机射出的子弹的基准速度
     * @param speedY 精英敌机射出的子弹的基准速度
     * @param hp    初始生命值
     */
    public EliteAircraft(int locationX, int locationY, int speedX, int speedY, int hp,int score) {
        super(locationX, locationY, speedX, speedY, hp,score);
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

    /**
     * 通过射击产生子弹
     * @return 产生的子弹列表
     */
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*3;
        int speedX = 0;
        int speedY = this.getSpeedY()+direction*5;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
