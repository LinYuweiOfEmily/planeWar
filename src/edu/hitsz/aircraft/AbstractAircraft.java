package edu.hitsz.aircraft;

import edu.hitsz.Strategy.ShootStrategy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected ShootStrategy shootStrategy;
    protected int shootNum = 1;
    protected int direction = 1;
    protected int power = 10;
    protected double startAngle = 2*Math.PI/shootNum;


    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }


    public int getShootNum() {
        return shootNum;
    }

    public int getDirection() {
        return direction;
    }

    public int getPower() {
        return power;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }


    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public List<BaseBullet> shoot(){
        return shootStrategy.doShoot(this);
    };

}


