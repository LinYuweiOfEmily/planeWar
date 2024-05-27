package com.example.aircraftwar2024.aircraft;



import com.example.aircraftwar2024.factory.supply_factory.BombSupplyFactory;
import com.example.aircraftwar2024.factory.supply_factory.FireSupplyFactory;
import com.example.aircraftwar2024.factory.supply_factory.HpSupplyFactory;
import com.example.aircraftwar2024.factory.supply_factory.SupplyFactory;
import com.example.aircraftwar2024.supply.AbstractFlyingSupply;

import java.util.LinkedList;
import java.util.List;

/**
 * BOSS 敌机
 * 可射击
 * 工厂构造
 *
 * @author hitsz
 */
public class BossEnemy extends AbstractEnemyAircraft {

    /**
     * 道具工厂
     */
    private SupplyFactory flyingSupplyFactory;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 3;
        this.power = 10;
        this.direction = 1;
        this.rate = 1.2;
    }

    @Override
    public List<AbstractFlyingSupply> generateSupplies() {
        List<AbstractFlyingSupply> res = new LinkedList<>();

        //boss机坠落随机掉落3个道具
        for (int i = 0; i < 3; i++) {
            flyingSupplyFactory = null;
            double prob = Math.random();
            if (prob < 0.3) {
                flyingSupplyFactory = new BombSupplyFactory();
            } else if (prob < 0.5) {
                flyingSupplyFactory = new FireSupplyFactory();
            } else if (prob < 0.8) {
                flyingSupplyFactory = new HpSupplyFactory();
            } else {
                System.out.println("No supply generated!");
            }

            if (flyingSupplyFactory != null) {
                int dropLocationX = locationX+50*i < 470 ? locationX+50*i : 470-50*i;
                res.add(flyingSupplyFactory.createFlyingSupply(dropLocationX, locationY));
            }
        }
        return res;
    }

    @Override
    public int score() {
        return 100;
    }


}
