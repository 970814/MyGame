package parasite;

import controller.Controllable;
import movable.Movable;

/**
 * A parasite.
 * It will parasitic to animals and controls its actions
 */
public interface Parasites extends Controllable {
    int currentDirection();
    void turn(int d);
}
/**
 * 对于动物类都可以寄生一个实现了Parasites接口的对象;通常用匿名类使用;
 * 作用:
 * 控制动物的方向.
 */
