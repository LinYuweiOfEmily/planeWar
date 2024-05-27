package com.example.aircraftwar2024.factory.enemy_factory;


import com.example.aircraftwar2024.aircraft.AbstractEnemyAircraft;

import java.util.LinkedList;
import java.util.List;

/**
 * 工厂抽象类
 * 【工厂模式】
 *
 * @author hitsz
 */
public interface EnemyFactory{

    /**
     * 创建敌机抽象方，厂实现类根据具体工厂创建指定敌机。
     * @param level： 游戏难度增加等级（速度和血量提升的倍率）
     * @return 敌机
     */
    public abstract AbstractEnemyAircraft createEnemyAircraft(double level);

}
