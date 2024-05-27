package com.example.aircraftwar2024.aircraft;


import android.util.Log;

import com.example.aircraftwar2024.factory.supply_factory.BombSupplyFactory;
import com.example.aircraftwar2024.factory.supply_factory.FireSupplyFactory;
import com.example.aircraftwar2024.factory.supply_factory.HpSupplyFactory;
import com.example.aircraftwar2024.factory.supply_factory.SupplyFactory;
import com.example.aircraftwar2024.supply.AbstractFlyingSupply;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 * 可射击
 *
 * @author hitsz
 */
public class EliteEnemy extends AbstractEnemyAircraft {

    /**
     * 道具工厂
     */
    private SupplyFactory flyingSupplyFactory;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp ) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 1;
        this.power = 10;
        this.direction = 1;
        this.rate = 1.2;
    }

    @Override
    public List<AbstractFlyingSupply> generateSupplies() {
        List<AbstractFlyingSupply> res = new LinkedList<>();

        double prob = Math.random();
        if (prob < 0.3) {
            Log.d("EliteEnemy","generate bomb supply");
            flyingSupplyFactory = new BombSupplyFactory();
        } else if (prob < 0.5) {
            Log.d("EliteEnemy","generate fire supply");
            flyingSupplyFactory = new FireSupplyFactory();
        } else if (prob < 0.9) {
            Log.d("EliteEnemy","generate hp supply");
            flyingSupplyFactory = new HpSupplyFactory();
        } else {
            System.out.println("No supply generated!");
        }


        if (flyingSupplyFactory != null){
            res.add(flyingSupplyFactory.createFlyingSupply(locationX,locationY));
        }

        return res;
    }

    @Override
    public int score() {
        return 30;
    }
}
