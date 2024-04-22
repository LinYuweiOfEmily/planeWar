package edu.hitsz.factory.prop;

import edu.hitsz.prop.BaseProp;

/**
 * @author linyu
 */
public interface PropFactory {
    BaseProp createProp(int locationX, int locationY);
}
