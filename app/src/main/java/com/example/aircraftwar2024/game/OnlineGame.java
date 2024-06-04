package com.example.aircraftwar2024.game;

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.activity.MainActivity;
import com.example.aircraftwar2024.basic.AbstractFlyingObject;

public class OnlineGame extends BaseGame {
    public OnlineGame(Context context) {
        super(context);
        this.backGround = ImageManager.BACKGROUND2_IMAGE;
        this.enemyMaxNumber = 3;
        this.heroShootCycle = 9;
        this.enemyShootCycle = 19;
        this.eliteProb = 0.15;
        this.bossScoreThreshold = 300;
        this.tickCycle = 300;
    }

    @Override
    protected void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        flyingSupplies.removeIf(AbstractFlyingObject::notValid);

        if (heroAircraft.notValid()) {
            MainActivity.isHeroExit = false;
            bgmPlayer.stopMusic();
            stopDraw = true;
            if(existBoss()){
                bossGgmPlayer.stopMusic();
            }
            mySoundPool.playMusic(R.raw.game_over);
            mySoundPool.stopAllMusic();
            if(!MainActivity.isEnemyHeroExit){
                Message message = new Message();
                message.obj = "heroAircraft is not Valid";
                message.arg1 = score;
                gameContext.mHandler.sendMessage(message);
                surfaceDestroyed(mSurfaceHolder);
                gameOverFlag = true;
                mbLoop = false;
                Log.i(TAG, "heroAircraft is not Valid");
            }
        }
    }

    @Override
    public void paintScoreAndLife() {
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(60);
        canvas.drawText("SCORE: "+score+"   ENEMY SCORE:"+ MainActivity.scoreEnemy,10,40,mPaint);
        canvas.drawText("LIFE: "+heroAircraft.getHp(),10,90,mPaint);
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
