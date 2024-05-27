package com.example.aircraftwar2024.game;

import android.content.Context;
import android.os.Handler;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.aircraft.AbstractEnemyAircraft;

import java.util.List;

public class HardGame extends BaseGame{
    public HardGame(Context context) {
        super(context);
        this.backGround = ImageManager.BACKGROUND3_IMAGE;
        this.enemyMaxNumber = 5;
        this.heroShootCycle = 8;
        this.enemyShootCycle = 18;
        this.eliteProb = 0.2;
        this.bossScoreThreshold = 500;
        this.tickCycle = 250;
    }

    /**
     * 困难模式随着时间增加而提高难度
     */
    @Override
    protected void tick() {
        this.tickCounter++;
        if (this.tickCounter >= this.tickCycle) {
            this.tickCounter = 0;

            // 困难模式增加：提高精英敌机出现概率
            if (eliteProb <= 1) {
                eliteProb *= 1.02;
            }

            // 提高敌机产生频率（减小产生周期）
            this.enemyCycle *= 0.98;

            // 提高敌机血量和速度
            gameLevel *= 1.02;

            System.out.format(" 提高难度！精英机概率:%.2f,敌机周期:%.2f, 敌机属性提升倍率:%.2f。\n",
                    eliteProb, enemyCycle, gameLevel);

        }
    }

    /**
     * 困难模式每次召唤BOSS，提高BOSS血量
     * @return
     */
    @Override
    protected List<AbstractEnemyAircraft> produceBoss() {

        List<AbstractEnemyAircraft> res = super.produceBoss();
        if (res.size() > 0)
        {
            bossLevel *= 1.1;
            System.out.format("Boss敌机血量倍率:%.2f。\n", bossLevel);
        }

        return res;
    }
}
