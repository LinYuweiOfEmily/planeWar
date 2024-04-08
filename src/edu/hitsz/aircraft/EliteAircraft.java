package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.prop.BulletProp;

import java.util.LinkedList;
import java.util.List;

public class EliteAircraft extends AbstractAircraft{
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 击败精英敌机得到的分数
     */
    private int score = 100;

    public int getScore() {
        return score;
    }

    /**
     * 子弹伤害
     */
    private int power = 10;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;

    /**
     * @param locationX 精英敌机位置x坐标
     * @param locationY 精英敌机位置y坐标
     * @param speedX 精英敌机射出的子弹的基准速度
     * @param speedY 精英敌机射出的子弹的基准速度
     * @param hp    初始生命值
     */
    public EliteAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
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
    public BaseProp generateNewProp(){
        double isGenProp = Math.random();
        if(isGenProp<0.3){
            return new BloodProp(
                    this.getLocationX(),
                    this.getLocationY(),
                    0,
                    6
            );
        }else if(isGenProp<0.4){
            return new BombProp(
                    this.getLocationX(),
                    this.getLocationY(),
                    0,
                    6
            );
        }else if(isGenProp<0.6){
            return new BulletProp(
                    this.getLocationX(),
                    this.getLocationY(),
                    0,
                    6
            );
        }
        return new BulletProp() ;
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
