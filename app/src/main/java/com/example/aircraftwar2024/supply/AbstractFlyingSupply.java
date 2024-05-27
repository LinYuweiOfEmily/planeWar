package com.example.aircraftwar2024.supply;


import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.basic.AbstractFlyingObject;

/**
 * 所有飞行道具的抽象父类。
 *
 * @author hitsz
 */
public abstract class AbstractFlyingSupply extends AbstractFlyingObject {

    public AbstractFlyingSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /**
     * 道具向下飞行出界后，标记无效
     */
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= GameActivity.screenHeight ) {
            vanish();
        }
    }

    /**
     * 道具生效
     * @param game
     */
    public abstract void activate() throws InterruptedException;

}
