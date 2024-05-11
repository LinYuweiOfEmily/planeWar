package edu.hitsz.prop;

import edu.hitsz.Strategy.shoot.DirectShoot;
import edu.hitsz.Strategy.shoot.RingShoot;
import edu.hitsz.Strategy.shoot.ScatterShoot;
import edu.hitsz.aircraft.hero.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

/**
 * @author linyu
 */
public abstract class BaseProp extends AbstractFlyingObject {
    /**
     * @param locationX 道具的x坐标
     * @param locationY 道具的y坐标
     * @param speedX 道具的x速度
     * @param speedY 道具的y速度
     */
    public BaseProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public BaseProp() {
    }

    /**
     *
     * 可以根据速度移动
     * 碰到y边界会销毁
     */
    @Override
    public void forward(){
        super.forward();
        //向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
        //判定y轴出界
//        if (locationY <= 0 || locationY >= Main.WINDOW_HEIGHT) {
//            // 横向超出边界后反向
//            speedY = -speedY;
//        }
    }

    public void directShoot(HeroAircraft heroAircraft){
        heroAircraft.setShootNum(1);
        heroAircraft.setShootStrategy(new DirectShoot());
    }
}
