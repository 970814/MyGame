package controller;

import java.util.function.Consumer;

/**
 * 由于按键映射;
 */
public abstract class ControlMapping implements Controllable {
    protected int[] map;

    public ControlMapping(int... keys) {
        mapping(keys);
    }

    public void mapping(int... keys) {
        map = keys;
    }
}
