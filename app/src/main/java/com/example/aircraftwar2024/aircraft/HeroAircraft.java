package com.example.aircraftwar2024.aircraft;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.shoot.DirectShoot;

/**
 * 英雄飞机，游戏玩家操控，遵循单例模式（singleton)
 * 【单例模式】
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

/*
        volatile 修饰，
        singleton = new Singleton() 可以拆解为3步：
        1、分配对象内存(给singleton分配内存)
        2、调用构造器方法，执行初始化（调用 Singleton 的构造函数来初始化成员变量）。
        3、将对象引用赋值给变量(执行完这步 singleton 就为非 null 了)。
        若发生重排序，假设 A 线程执行了 1 和 3 ，还没有执行 2，B 线程来到判断 NULL，B 线程就会直接返回还没初始化的 instance 了。
        volatile 可以避免重排序。
    */
    /** 英雄机对象单例 */
    private volatile static HeroAircraft heroAircraft;

    /**
     * 单例模式：私有化构造方法
     */
    private HeroAircraft() {
        super(GameActivity.screenWidth / 2, GameActivity.screenHeight - ImageManager.HERO_IMAGE.getHeight(),
                0, 0, 1000);
        this.shootNum = 1;
        this.power = 30;
        this.direction = -1;
        this.rate = 3;
        shootStrategy = new DirectShoot();
    }


    /**
     * 通过单例模式获得初始化英雄机
     * 【单例模式：双重校验锁方法】
     * @return 英雄机单例
     */
    public static HeroAircraft getHeroAircraft(){
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft();
                }
            }
        }
        return heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }
}
