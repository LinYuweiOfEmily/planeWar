package com.example.aircraftwar2024.aircraft;


import com.example.aircraftwar2024.basic.AbstractFlyingObject;
import com.example.aircraftwar2024.bullet.AbstractBullet;
import com.example.aircraftwar2024.shoot.ShootStrategy;

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

    /** 攻击方式
     * shootNum: 子弹一次发射数量
     * power:子弹伤害
     * direction:子弹射击方向 (向上发射：1，向下发射：-1)
     * rate: 调节子弹移动速度
     * shootStrategy: 攻击策略     * shootStrategy: 攻击策略
     */
    protected int shootNum = 1;
    protected int power = 30;
    protected int direction = -1;
    protected double rate = 3;
    protected ShootStrategy shootStrategy;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    /**
     * 减少HP
     * 一般在飞机被攻击时调用，减少一部分HP
     *
     * @param decrease HP的减少量，应为非负值
     */
    public void decreaseHp(int decrease) {
        hp -= decrease;
        if (hp <= 0) {
            hp = 0;
            vanish();
        }
    }

    /**
     * 增加HP
     * 一般在飞机获得补给时调用，增加部分HP，
     * 但不会使得飞机的HP超过初始值
     *
     * @param increase HP的增加量，应为非负值
     */
    public void increaseHp(int increase) {
        if(increase<=0){
            return;
        }
        hp += increase;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    public int getHp() {
        return hp;
    }


    public void setHp(int hp) {
        this.hp = hp;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }
    public int getMaxHp() {
        return maxHp;
    }


    /**
     * 为可射击飞机指定射击策略
     * 【策略模式】
     *
     * @param shootStrategy ShootStrategy 接口的实现类
     */
    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    /**
     * 获得飞机的射击策略
     *
     * @return ShootStrategy 实现对象
     */
    public ShootStrategy getShootStrategy() {
        return shootStrategy;
    }

    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public List<AbstractBullet> shoot() {
        assert shootStrategy!=null : "Shoot strategy unset!";
        return shootStrategy.shootWithStrategy(this);
    }


    public int getShootNum() {
        return shootNum;
    }

    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}


