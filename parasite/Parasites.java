package parasite;

import controller.Controllable;
import movable.Movable;

/**
 * Created by Administrator on 2016/4/28.
 */

/**
 * A parasite.
 * It will parasitic to animals and controls its actions
 */
public interface Parasites extends Controllable {
    int currentDirection();
    void turn(int d);
}
