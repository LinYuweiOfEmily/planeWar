package com.example.aircraftwar2024.factory.supply_factory;

import com.example.aircraftwar2024.supply.AbstractFlyingSupply;
import com.example.aircraftwar2024.supply.FireSupply;

/**
 * 火力道具工厂
 *
 * @author hitsz
 */
public class FireSupplyFactory implements SupplyFactory {

    @Override
    public AbstractFlyingSupply createFlyingSupply(int x, int y) {
        return new FireSupply(x,y,0,2);
    }

}
