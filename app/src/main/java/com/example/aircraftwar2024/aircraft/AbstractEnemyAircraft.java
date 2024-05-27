package com.example.aircraftwar2024.aircraft;



import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.supply.AbstractFlyingSupply;

import java.util.List;


/**
 * 敌机父类
 * 敌机：BOSS, ELITE, MOB
 *
 * @author hitsz
 */
public abstract class AbstractEnemyAircraft extends AbstractAircraft {

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);

    }

    /**
     * 获得敌机分数，击毁敌机时，调用该方法获得分数。
     * @return 敌机的分数
     */
    public abstract int score();

    /**
     * 敌机坠毁时以一定概率掉落道具
     * @return 道具
     */
    public abstract List<AbstractFlyingSupply> generateSupplies();

    /**
     * 敌机向下飞行出界后，标记无效
     */
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= GameActivity.screenHeight) {
            vanish();
        }
    }


}
