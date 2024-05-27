package com.example.aircraftwar2024.aircraft;


import com.example.aircraftwar2024.bullet.AbstractBullet;
import com.example.aircraftwar2024.supply.AbstractFlyingSupply;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemyAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     * 获得敌机分数，击毁敌机时，调用该方法获得分数。
     * @return 敌机的分数
     */
    @Override
    public int score() {
        return 10;
    }

    @Override
    public List<AbstractBullet> shoot() {
        return new LinkedList<>();
    }

    @Override
    public List<AbstractFlyingSupply> generateSupplies() {
        return new LinkedList<>();
    }


}
