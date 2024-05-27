package com.example.aircraftwar2024.game;

import android.content.Context;
import com.example.aircraftwar2024.ImageManager;

public class MediumGame extends BaseGame{
    public MediumGame(Context context) {
        super(context);
        this.backGround = ImageManager.BACKGROUND2_IMAGE;
        this.enemyMaxNumber = 3;
        this.heroShootCycle = 9;
        this.enemyShootCycle = 19;
        this.eliteProb = 0.15;
        this.bossScoreThreshold = 300;
        this.tickCycle = 300;
    }

    /**
     * 普通模式随着时间增加而提高难度
     */
    @Override
    protected void tick() {
        this.tickCounter++;
        if (this.tickCounter >= this.tickCycle) {
            this.tickCounter = 0;
            // 提高敌机产生频率（减小产生周期）
            this.enemyCycle *= 0.99;
            // 提高敌机血量
            gameLevel *= 1.01;
            System.out.format(" 提高难度！精英机概率:%.2f,敌机周期:%.2f, 敌机属性提升倍率:%.2f。\n",
                    eliteProb, enemyCycle, gameLevel);

        }
    }

}
