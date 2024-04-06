package edu.hitsz.prop;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class BaseProp extends AbstractFlyingObject {
    public BaseProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public BaseProp() {
    }

    public void forward(){
        super.forward();
        //判定y轴出界
        if (locationY <= 0 || locationY >= Main.WINDOW_HEIGHT) {
            // 横向超出边界后反向
            speedY = -speedY;
        }
    }
}
