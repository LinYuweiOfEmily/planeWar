package com.example.aircraftwar2024.supply;


import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.shoot.ShootStrategy;

import java.util.Stack;

/**
 * 火力道具：增加同时发射的子弹数。
 * 自动触发
 *
 * @author hitsz
 */
public class FireSupply extends AbstractFlyingSupply {
    /**
     * 用 Stack 存储旧的 shootNum 值，从而在计时结束后顺序恢复。
     * java.util.Stack 继承自 Vector，线程安全。
     */
    private static Stack<Integer> shootNumStack;

    static {
        // 初始化静态变量，所有线程共享该stack
        shootNumStack = new Stack<>();
    }

    public FireSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate() throws InterruptedException {
        /**
         * 火力道具生效时，子弹数量在原有基础上+1，可叠加
         */
        Runnable fireUpTask = () -> {
            try {

                ShootStrategy shootStrategy = HeroAircraft.getHeroAircraft().getShootStrategy(); // 英雄机当前射击策略
                int oldNum = HeroAircraft.getHeroAircraft().getShootNum();
                shootNumStack.push(oldNum);

                // 同时射出的子弹数 +1
                int newNum = oldNum + 1;
                HeroAircraft.getHeroAircraft().setShootNum(newNum);

                Thread.sleep(10000); // 持续10s，之后恢复原有子弹数量

                HeroAircraft.getHeroAircraft().setShootNum(shootNumStack.pop());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(fireUpTask).start();
        System.out.println("FireSupply active");
    }
}
