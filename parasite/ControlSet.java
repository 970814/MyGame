package parasite;

import controller.ControlMapping;

/**
 * Created by Administrator on 2016/4/28.
 */
public class ControlSet extends ControlMapping implements Parasites {
    private int direction;

    public ControlSet(int... keys) {
        super(keys);
    }


    @Override
    public int currentDirection() {
        return direction;
    }

    /**
     * This method was never be used,
     * Usually driven by keyboard.
     *
     * @param d new Direction
     */
    @Override
    public void turn(int d) {
        direction = d;
    }

    /**
     * do nothing.
     */
    @Override
    public void control(int keyCode) {
        for (int i = 0; i < map.length; i++) {
            if (keyCode == map[i]) {
                turn(i);
                return;
            }
        }
    }
}
